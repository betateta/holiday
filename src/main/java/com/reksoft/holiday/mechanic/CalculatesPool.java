package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Holiday;
import com.reksoft.holiday.model.Player;
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

        Integer freePlayers = playersPool.getFreePlayers();
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = new Dice().getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = new Dice().getMultiEventResult(holidayWithoutDinnerDiceMap);
        }

        if (!holidayName.isBlank() && !holidayName.isEmpty() && !holidayName.equals("eventMiss")) {

            Calculate calculate = new Calculate();
            List<Player> sponsorList = new ArrayList<>();

            if (holidayName.equals("dinner")) {
                sponsorList.add(playersPool.setPlayerAsSponsor(calculate));
                sponsorList.add(playersPool.setPlayerAsSponsor(calculate));
            } else {
                sponsorList.add(playersPool.setPlayerAsSponsor(calculate));
            }

            calculate.setHoliday(holidayService.findByName(holidayName));
            calculate.setSession(sessionGame);
            calculate.setCapacity(getHolidayCapacity(holidayService.findByName(holidayName)));
            calculate.setStartTime(currentTime);
            calculate.setPoints(0);
            calculate.setUniqPlayersNumber(0);
            calculate.setSponsorPlayerList(sponsorList);
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

    }
    private boolean kickPlayerFromHoliday(){
        return false;
    }
    private boolean addPlayerToHoliday(){
        return false;
    }
    private void checkHolidaysExpiration (Instant currentTime){
        for (Calculate item : currentCalculateList
        ) {
            // complete  holiday by time expiration
            if (currentTime.isAfter(item.getStartTime().plusSeconds(item.getHoliday().getDuration() * 3600))) {
                item.setStopTime(currentTime);

                List<Player> players = item.getPlayers();
                List<Player> sponsorPlayerList = item.getSponsorPlayerList();
                if (players != null){
                    item.setUniqPlayersNumber(item.getUniqPlayersNumber() + players.size());
                    for (Player player: players
                         ) {
                        playersPool.freePlayer(player);
                    }
                }
                // freeing player-sponsor
                for (Player player : sponsorPlayerList
                ) {
                    playersPool.freePlayer(player);
                    item.setPoints(item.getPoints() + 1500);
                }
                completedCalculateList.add(item);
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

