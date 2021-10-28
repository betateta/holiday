package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class CalculateSession {

    private PlayerServiceImpl playerService;
    private Set <Player> playerSet;
    private SessionGame sessionGame;
    private Instant currentTime;

    public CalculateSession(SessionGame sessionGame, PlayerServiceImpl playerService) {
        this.sessionGame = sessionGame;
        this.playerService=playerService;
    }

    public SessionGame getSessionGame(){
        initSession();
        runSession();
        saveResults();
        HashMap <String,Integer> map = new HashMap<String,Integer>();
        map.put("name1",40);
        map.put("name2",60);

        new Dice().getMultiEventResult(map);
        new Dice().getMultiEventResult(map);
        new Dice().getMultiEventResult(map);
        new Dice().getMultiEventResult(map);
        new Dice().getMultiEventResult(map);

        return sessionGame;
    }

    private Set<Player> createPlayers(Integer numberOfPlayers){
        playerService.deleteAll();
        Set<Player> playersSet = new HashSet<Player>();
        for (int i = 0;i<numberOfPlayers;i++){
            playersSet.add(new Player(i,"player_"+i,0,false));
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

        while (currentTime.isBefore(sessionGame.getStopTime())){
            currentTime = currentTime.plusSeconds(timeTick);

        }
    }
    private void saveResults(){
        playerService.saveAll(playerSet);
    }

}

