package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


public class CalculateSession {

    private PlayerServiceImpl playerService;
    private Player player;
    private SessionGame sessionGame;
    private Instant currentTime;

    public CalculateSession(SessionGame sessionGame, PlayerServiceImpl playerService) {
        this.sessionGame = sessionGame;
        this.playerService=playerService;
    }

    public SessionGame getSessionGame(){

        sessionGame.setStartTime(Instant.now());
        sessionGame.setStopTime(sessionGame.getStartTime().plusSeconds(sessionGame.getSessionDuration()*24*3600));
        currentTime = sessionGame.getStartTime();
        createPlayers(sessionGame.getSessionPlayers());

        //Session duration in min
        Integer timeTick = sessionGame.getSessionDuration()*60;
        while (currentTime.isBefore(sessionGame.getStopTime())){

            currentTime.plusSeconds(timeTick);
        }
        return sessionGame;
    }
    private Set<Player> createPlayers(Integer numberOfPlayers){
        playerService.deleteAll();
        Set<Player> playersSet = new HashSet<Player>();

        for (int i = 0;i<numberOfPlayers;i++){
            playersSet.add(new Player(i,"player_"+i));
        }
        for (Player player:playersSet
             ) {
            playerService.savePlayer(player);
        }
        return playersSet;
    }

}

