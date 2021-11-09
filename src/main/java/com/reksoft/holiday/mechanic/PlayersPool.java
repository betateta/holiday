package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Player;
import lombok.Getter;

import java.util.*;

@Getter
public class PlayersPool {
    private Set<Player> playersSet;
    private SessionParameters sessionParameters;

    public PlayersPool(SessionParameters sessionParameters) {
        this.sessionParameters = sessionParameters;
        this.playersSet = new HashSet<>();
    }

    public Set<Player> createNewPlayersSet(){
        final int std_shots = 5;

        if (sessionParameters!=null) {
            for (int i = 1; i <= sessionParameters.getSessionPlayers(); i++) {
                playersSet.add(new Player("player_" + i,
                        0,
                        0,
                        false));
            }
        /*
        Fill players profile
         */

            Integer playersNumberAddshot = sessionParameters.getPlayersNumberAddshot();

            HashMap<String, Integer> addShotMap = new HashMap<>();
            addShotMap.put("addshots", sessionParameters.getPlayersAddshotChance());

            Iterator<Player> iterator = playersSet.iterator();
            Integer bonus_addshots = 0;
            if (sessionParameters.getSessionPlayers() >= playersNumberAddshot) {
                for (int i = 0; i < playersNumberAddshot; i++) {
                    if (new Dice().getMultiEventResult(addShotMap).equals("addshots")) {
                        if (iterator.hasNext()) {
                            bonus_addshots = new Dice().getRandFromRange(sessionParameters.getPlayersAddshotMin(),
                                    sessionParameters.getPlayersAddshotMax());
                            iterator.next().setShots(bonus_addshots + std_shots);
                        }
                    }
                }
            }
            return playersSet;
        }
        return null;
    }
    public void setPlayerIsFree (Player player){
          for (Player item: playersSet) {
                if (item.equals(player)){
                    item.setIsBusy(true);
                }
            }
    }
    public void setPlayerIsBusy (Player player){
        for (Player item: playersSet) {
            if (item.equals(player)){
                item.setIsBusy(false);
            }
        }
    }

    public Integer getNumberFreePlayers (){
        int count = 0;
        for (Player entity : playersSet) {
            if (!entity.getIsBusy()){
                count++;
            }
        }
        return count;
    }
    public Player getFreePlayer(){
        for (Player item:playersSet
             ) {
            if (!item.getIsBusy()){
                return item;
            }
        }
        return null;
    }
    public Player getFreePlayerWithShots(){
        for (Player item:playersSet
        ) {
            if (!item.getIsBusy() && (item.getShots() > 0)){
                return item;
            }
        }
        return null;
    }
    public List<Player> getAllFreePlayer(){
        List<Player> players = new ArrayList<>();
        for (Player item:playersSet
        ) {
            if (!item.getIsBusy()){
                players.add(item);
            }
        }
        return players;
    }
    public List<Player> getAllBusyPlayer(){
        List<Player> players = new ArrayList<>();
        for (Player item:playersSet
        ) {
            if (item.getIsBusy()){
                players.add(item);
            }
        }
        return players;
    }
    public List<Player> getAllFreePlayerWithShots(){
        List<Player> players = new ArrayList<>();
        for (Player item:playersSet
        ) {
            if (!item.getIsBusy() && (item.getShots()>0)){
                players.add(item);
            }
        }
        return players;
    }
}
