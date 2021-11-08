package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Holiday;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.HolidayService;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
public class CalculatesPool {
    private List<Calculate> currentCalculateList;
    private List<Calculate> completedCalculateList;
    private SessionGame sessionGame;
    private HolidayService holidayService;

    private PlayersPool playersPool;
    private final int org_points=1500;

    public CalculatesPool(SessionGame sessionGame, PlayersPool playersPool, HolidayService holidayService) {
        this.sessionGame = sessionGame;
        this.playersPool = playersPool;
        this.holidayService = holidayService;
        this.completedCalculateList = new ArrayList<>();
        this.currentCalculateList = new ArrayList<>();
    }

    /*
    Method creates new Calculate and adding to current list
     */
    public void createCalculate (Instant currentTime) {
        Set<Holiday> holidaySet = holidayService.getAllSet();
        HashMap<String,Integer> holidayFullDiceMap = getHolidayDiceMap(holidaySet);
        HashMap<String,Integer> holidayWithoutDinnerDiceMap = (HashMap<String, Integer>) holidayFullDiceMap.clone();
        holidayWithoutDinnerDiceMap.remove("dinner");

        Integer freePlayers = playersPool.getNumberFreePlayers();
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = new Dice().getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = new Dice().getMultiEventResult(holidayWithoutDinnerDiceMap);
        }

        if (!holidayName.isBlank() && !holidayName.isEmpty() && !holidayName.equals("eventMiss")) {

            Calculate calculate = new Calculate();
            MembersPool membersPool = new MembersPool(calculate, new ArrayList<>());

            if (holidayName.equals("dinner")) {
                membersPool.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
                membersPool.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
            } else {
                membersPool.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
            }

            calculate.setHoliday(holidayService.findByName(holidayName));
            calculate.setSession(sessionGame);
            calculate.setCapacity(getHolidayCapacity(holidayService.findByName(holidayName)));
            calculate.setStartTime(currentTime);
            calculate.setPoints(0);
            calculate.setUniqPlayersNumber(0);
            calculate.setMemberList(membersPool.getMemberList());
            currentCalculateList.add(calculate);
        }
    }
    /*
    method include checks:
        - expiration by time of holiday
        - expiration by day (24 hours)
        - add new players to holiday (calculate)
        - kick players from holiday (calculate)
     */
    public void updateCalculates(Instant currentTime){
        checkHolidaysExpiration(currentTime);
        addPlayerToHoliday();
    }
    private boolean kickPlayerFromHoliday(){
        return false;
    }
    private boolean addPlayerToHoliday(){

        return false;
    }
    private void checkHolidaysExpiration (Instant currentTime){
        for (Calculate calc : currentCalculateList
        ) {
            // complete  holiday by time expiration
            if (currentTime.isAfter(calc.getStartTime().plusSeconds(calc.getHoliday().getDuration() * 3600))) {
                calc.setStopTime(currentTime);
                MembersPool membersPool = new MembersPool(calc,calc.getMemberList());

                List<Member> organizators = membersPool.getOrganizators();
                List<Member> players = membersPool.getAllWithoutOrganizators();
                int total_players_points = 0;
                if (players!= null) {
                    calc.setUniqPlayersNumber(players.size());
                    // freeing players
                    for (Member player: players
                    ) {
                        /* if member is active*/
                        if (player.getOutputTime() == null){
                            player.setOutputTime(currentTime);

                            /*duration of last period*/
                            long duration = currentTime.getEpochSecond() - player.getInputTime().getEpochSecond();
                            player.setDuration(player.getDuration() + duration);

                            /* points of last period*/
                            int points = ((int) duration) * calc.getHoliday().getPointsRate().intValue();
                            player.setHolidayPoints(player.getHolidayPoints() + points);
                            total_players_points += player.getHolidayPoints();

                            playersPool.setPlayerIsFree(player.getPlayer());
                        }
                    }
                }
                for (Member org: organizators
                     ) {
                    playersPool.setPlayerIsFree(org.getPlayer());
                    org.setOutputTime(currentTime);
                    org.setHolidayPoints(org_points+total_players_points);
                }
                completedCalculateList.add(calc);
            }
        }
        excludeCompletedFromCurrent();
    }
    private void checkHolidayExpirationByDay (){

    }
    private void excludeCompletedFromCurrent(){
        for (Calculate item: completedCalculateList
             ) {
            if (currentCalculateList.contains(item)){
                currentCalculateList.remove(item);
            }
        }
        System.out.println();
    }
    private Integer getHolidayCapacity(Holiday holiday){
        return new Dice().getRandFromRange(holiday.getMinCapacity(),holiday.getMaxCapacity());
    }
    private HashMap<String,Integer> getHolidayDiceMap(Set<Holiday> holidaySet){
        HashMap<String,Integer> holidayFullDiceMap = new HashMap<>();
        for (Holiday item:holidaySet
        ) {
            switch (item.getName()) {

                case ("simple"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidaySimpleChance());
                    break;
                case ("banquet"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidayBanquetChance());
                    break;
                case ("dinner"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidayDinnerChance());
                    break;
                default:
                    break;
            }
        }
        return holidayFullDiceMap;
    }

}

