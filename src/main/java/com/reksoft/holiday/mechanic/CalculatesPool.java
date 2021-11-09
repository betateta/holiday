package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.*;
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
    private final SessionGame sessionGame;
    private final HolidayService holidayService;


    private final PlayersInterface playersImpl;
    private final int org_points = 1500;

    public CalculatesPool(SessionGame sessionGame, PlayersInterface playersImpl, HolidayService holidayService) {
        this.sessionGame = sessionGame;
        this.playersImpl = playersImpl;
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

        Integer freePlayers = playersImpl.getNumberFreePlayers();
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = new Dice().getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = new Dice().getMultiEventResult(holidayWithoutDinnerDiceMap);
        }

        if (!holidayName.isBlank() && !holidayName.isEmpty() && !holidayName.equals("eventMiss")) {

            Calculate calculate = new Calculate();
            calculate.setMemberList(new ArrayList<>());
            MembersInterface membersImpl = new MembersImpl(calculate);

            if (holidayName.equals("dinner")) {
                membersImpl.addMemberAsOrganizator(playersImpl.getFreePlayer(),currentTime);
                membersImpl.addMemberAsOrganizator(playersImpl.getFreePlayer(),currentTime);
            } else {
                membersImpl.addMemberAsOrganizator(playersImpl.getFreePlayer(),currentTime);
            }
            calculate.setNumberOfPlayers(0);
            calculate.setHoliday(holidayService.findByName(holidayName));
            calculate.setSession(sessionGame);
            calculate.setCapacity(getHolidayCapacity(holidayService.findByName(holidayName)));
            calculate.setStartTime(currentTime);
            calculate.setPoints(0);
            calculate.setUniqPlayersNumber(0);
            calculate.setMemberList(membersImpl.getMemberList());
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
        addPlayersToHolidays(currentTime);
       // kickPlayerFromHoliday(currentTime);
    }
    private void kickPlayerFromHoliday(Instant currentTime){
        /*
            Kick chance
         */
        Player player = playersImpl.getFreePlayerWithShots();
        List<Calculate> calculatesWithoutPlaces = new ArrayList<>();
        HashMap<String,Integer> kick_map = new HashMap<>();
        kick_map.put("enter",sessionGame.getHolidayFillChance());

        for (Calculate calc:currentCalculateList
        ) {
            if((calc.getCapacity()-calc.getNumberOfPlayers()) == 0){
                calculatesWithoutPlaces.add(calc);
            }
        }
        for (Calculate calc: calculatesWithoutPlaces
        ) {
            player = playersImpl.getFreePlayerWithShots();
            if(player != null){
                player.setShots(player.getShots()-1);
                if(new Dice().getMultiEventResult(kick_map).equals("enter")){
                        /*
                        kick player from holiday and calc his points
                         */
                    List<Member> memberList = calc.getMemberList();
                    MembersImpl membersImpl = new MembersImpl(calc);
                    Member member = memberList.iterator().next();
                    Player kick_player= member.getPlayer();

                    member.setOutputTime(currentTime);
                    playersImpl.setPlayerIsFree(kick_player);
                    member.setHolidayPoints(member.getHolidayPoints()+ membersImpl.getPointsForPeriod(member));
                        /*
                        add new player
                         */
                    if (!membersImpl.findMember(member)) {
                        calc.setUniqPlayersNumber(calc.getUniqPlayersNumber()+1);
                    }
                    playersImpl.setPlayerIsBusy(member.getPlayer());
                    membersImpl.addMember(player,currentTime);

                }
            }
        }
    }
    /*
        Input data:
       - list of holidays(calculates) with free places
       - list of free players with shots >0
    */
    private void addPlayersToHolidays(Instant currentTime){
        Player player = playersImpl.getFreePlayerWithShots();
        if(!currentCalculateList.isEmpty() && (player != null)) {
            List<Calculate> calculatesWithPlaces = new ArrayList<>();

            for (Calculate calc:currentCalculateList
            ) {
                if((calc.getCapacity()-calc.getNumberOfPlayers()) > 0){
                    calculatesWithPlaces.add(calc);
                }
            }
            /*
            Fill chance
             */
            HashMap<String,Integer> fill_map = new HashMap<>();
            fill_map.put("enter",sessionGame.getHolidayFillChance());
            for (Calculate calc: calculatesWithPlaces
                 ) {
                player = playersImpl.getFreePlayerWithShots();
                if(player != null){
                    player.setShots(player.getShots()-1);
                    if(new Dice().getMultiEventResult(fill_map).equals("enter")){
                        player.setIsBusy(true);
                        MembersInterface membersImpl = new MembersImpl(calc);
                        membersImpl.addMember(player,currentTime);
                        calc.setNumberOfPlayers(calc.getNumberOfPlayers()+1);
                        if (!membersImpl.findPlayer(player)) {
                            calc.setUniqPlayersNumber(calc.getUniqPlayersNumber()+1);
                        }
                    }
                }
            }
        }
    }
    private void checkHolidaysExpiration (Instant currentTime){
        for (Calculate calc : currentCalculateList
        ) {
            // complete  holiday by time expiration
            if (currentTime.isAfter(calc.getStartTime().plusSeconds(calc.getHoliday().getDuration() * 3600))) {
                calc.setStopTime(currentTime);
                MembersInterface membersImpl = new MembersImpl(calc);

                List<Member> organizators = membersImpl.getOrganizators();
                List<Member> players = membersImpl.getAllWithoutOrganizators();
                int total_players_points = 0;
                if (players!= null) {
                    calc.setUniqPlayersNumber(players.size());
                    // freeing players and calc points
                    for (Member member: players
                    ) {
                        /* if member is active*/
                        if (member.getOutputTime() == null){
                            member.setOutputTime(currentTime);

                            /*duration of last period*/
                            long duration = currentTime.getEpochSecond() - member.getInputTime().getEpochSecond();
                            member.setDuration(member.getDuration() + duration);

                            /* points of last period*/
                            int points = membersImpl.getPointsForPeriod(member);
                            member.setHolidayPoints(member.getHolidayPoints() + points);
                            total_players_points += member.getHolidayPoints();

                            playersImpl.setPlayerIsFree(member.getPlayer());
                        }
                    }
                }
                int points=0;
                for (Member org: organizators
                     ) {
                    playersImpl.setPlayerIsFree(org.getPlayer());
                    org.setOutputTime(currentTime);
                    points+=org_points;
                    org.setHolidayPoints(org_points + total_players_points);
                }
                /* total holiday points */
                calc.setPoints(total_players_points+points);
                completedCalculateList.add(calc);
            }
        }
        excludeCompletedFromCurrent();
    }
   
    private void excludeCompletedFromCurrent(){
        for (Calculate item: completedCalculateList
             ) {
            if (currentCalculateList.contains(item)){
                currentCalculateList.remove(item);
            }
        }
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

