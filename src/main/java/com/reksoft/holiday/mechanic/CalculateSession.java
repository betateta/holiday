package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Holiday;
import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.HolidayServiceImpl;
import com.reksoft.holiday.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
public class CalculateSession {

    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private HolidayServiceImpl holidayService;

    private SessionGame sessionGame;
    private Set<Player> playerSet;

    private Set<Calculate> calculateSet;
    private Instant currentTime;

    private HashMap<String,Integer> holidayFullDiceMap;
    private HashMap<String,Integer> holidayWithoutDinnerDiceMap;

    public SessionGame getSessionGame(){
        initSession();
        runSession();
        saveResults();
        return sessionGame;
    }

    public CalculateSession() {
    }

    public void setSessionGame(SessionGame sessionGame) {
        this.sessionGame = sessionGame;
    }

    private Set<Player> createPlayers(Integer numberOfPlayers){
        playerService.deleteAll();
        Set<Player> playersSet = new HashSet<Player>();
        for (int i = 0;i < numberOfPlayers; i++){
            playersSet.add(new Player(i,"player_"+i,0,5,0,false));
        }
        /*
        Fill players profile
         */

        Integer playersNumberAddshot = sessionGame.getPlayersNumberAddshot();

        HashMap<String,Integer> addShotMap =new HashMap<>();
        addShotMap.put("addshots",sessionGame.getPlayersAddshotChance());

        Iterator<Player> iterator = playersSet.iterator();
        Integer bonus_addshots =0;
        if (numberOfPlayers >= playersNumberAddshot){
            for (int i = 0; i < playersNumberAddshot; i++){
                if (new Dice().getMultiEventResult(addShotMap).equals("addshots")) {
                    if (iterator.hasNext()){
                        bonus_addshots = new Dice().getRandFromRange(sessionGame.getPlayersAddshotMin(),
                                sessionGame.getPlayersAddshotMax());
                        iterator.next().setBonusShots(bonus_addshots);
                    }
                }
            }
        }

        return playersSet;
    }

    private void initSession(){
        sessionGame.setStartTime(Instant.now());
        sessionGame.setStopTime(sessionGame.getStartTime().plusSeconds(sessionGame.getSessionDuration()*24*3600));
        currentTime = sessionGame.getStartTime();
        playerSet = createPlayers(sessionGame.getSessionPlayers());

    }

    private void runSession(){
        //Session duration in min
        Integer timeTick = sessionGame.getHolidaySampleFreq()*60;
        /*
        Map for Dice
         */
        Set<Holiday> holidaySet = holidayService.getAllSet();

        calculateSet =new HashSet<>();
        holidayFullDiceMap = getHolidayDiceMap(holidaySet);
        holidayWithoutDinnerDiceMap = new HashMap<>();
        holidayWithoutDinnerDiceMap = (HashMap<String, Integer>) holidayFullDiceMap.clone();
        holidayWithoutDinnerDiceMap.remove("dinner");

        /*  Session cycle      */
        while (currentTime.isBefore(sessionGame.getStopTime())){
            Calculate calculate = startNewCalculate();
            if( calculate != null) {
                calculateSet.add(calculate);
            }


            currentTime = currentTime.plusSeconds(timeTick);
        }
    }
    private Integer getFreePlayers (Set<Player> players){
        int count=0;
        for (Player entity : players) {
            if (!entity.getIsOrganizator()){
                count++;
            }
        }
        return count;
    }
    private Player setPlayerAsOrganizator(Set<Player> players){
        if (getFreePlayers(players) != 0){
            for (Player item: players) {
                if (!item.getIsOrganizator()){
                    item.setIsOrganizator(true);
                    return item;
                }
            }
        }
        return null;
    }

    private void saveResults(){
        playerService.saveAll(playerSet);
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

    private Integer getHolidayCapacity(Holiday holiday){
        return new Dice().getRandFromRange(holiday.getMinCapacity(),holiday.getMaxCapacity());
    }
    private Integer getPlayerAddshot(SessionGame sessionGame){
        return new Dice().getRandFromRange(sessionGame.getPlayersAddshotMin(),sessionGame.getPlayersAddshotMax());
    }

    private Calculate startNewCalculate () {

        /*  Check available resources     */
        Integer freePlayers = getFreePlayers(playerSet);
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = new Dice().getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = new Dice().getMultiEventResult(holidayWithoutDinnerDiceMap);
        }

        if (!holidayName.isBlank() && !holidayName.isEmpty() && !holidayName.equals("eventMiss")) {

            Calculate calculate = new Calculate();
            Set<Player> playersOrg = new HashSet<>();

            /* set player(s) as organizator(s) */
            if (holidayName.equals("dinner")) {
                playersOrg.add(setPlayerAsOrganizator(playerSet));
                playersOrg.add(setPlayerAsOrganizator(playerSet));
            } else {
                playersOrg.add(setPlayerAsOrganizator(playerSet));
            }

            calculate.setPlayers(playersOrg);
            calculate.setHoliday(holidayService.findByName(holidayName));
            calculate.setSession(sessionGame);
            calculate.setCapacity(getHolidayCapacity(holidayService.findByName(holidayName)));
            calculate.setStartTime(currentTime);
            calculate.setPoints(0);
            calculate.setUniqPlayersNumber(0);

            return calculate;
        }
        return null;
    }
    private void checkCurrentCalculates (){

    }
}

