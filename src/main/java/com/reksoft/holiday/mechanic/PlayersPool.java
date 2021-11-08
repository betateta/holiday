package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Getter
public class PlayersPool {
    private Set<Player> playersSet;
    private SessionParameters sessionParameters;

    public PlayersPool(SessionParameters sessionParameters) {
        this.sessionParameters = sessionParameters;
        this.playersSet = new HashSet<>();
    }

    public Set<Player> createNewPlayersSet(){
        if (sessionParameters!=null) {
            for (int i = 1; i <= sessionParameters.getSessionPlayers(); i++) {
                playersSet.add(new Player("player_" + i,
                        0,
                        5, 0,
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
                            iterator.next().setBonusShots(bonus_addshots);
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

}
