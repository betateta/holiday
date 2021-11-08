package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Calculate;
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
        for (int i = 1;i <= sessionParameters.getSessionPlayers(); i++){
            playersSet.add(new Player("player_"+i,
                    0,0,
                    5,0,
                    false,false,false));
        }
        /*
        Fill players profile
         */

        Integer playersNumberAddshot = sessionParameters.getPlayersNumberAddshot();

        HashMap<String,Integer> addShotMap =new HashMap<>();
        addShotMap.put("addshots",sessionParameters.getPlayersAddshotChance());

        Iterator<Player> iterator = playersSet.iterator();
        Integer bonus_addshots =0;
        if (sessionParameters.getSessionPlayers()>= playersNumberAddshot){
            for (int i = 0; i < playersNumberAddshot; i++){
                if (new Dice().getMultiEventResult(addShotMap).equals("addshots")) {
                    if (iterator.hasNext()){
                        bonus_addshots = new Dice().getRandFromRange(sessionParameters.getPlayersAddshotMin(),
                                sessionParameters.getPlayersAddshotMax());
                        iterator.next().setBonusShots(bonus_addshots);
                    }
                }
            }
        }
        return playersSet;
    }
    public Player setPlayerAsSponsor(Calculate calculate){
        if (getFreePlayers() != 0){
            for (Player item: playersSet) {
                if (!item.getIsOrganizator()){
                    item.setIsOrganizator(true);
                    item.setIsBusy(true);
                    item.setSponsoredHoliday(calculate);
                    return item;
                }
            }
        }
        return null;
    }
    public Player setPlayerAsMember (Set<Player> players, Calculate calculate){
        if (getFreePlayers() != 0){
            for (Player item: playersSet) {
                if (!item.getIsAtParty()){
                    item.setIsAtParty(true);
                    item.setIsBusy(true);
                    item.setSponsoredHoliday(calculate);
                    return item;
                }
            }
        }
        return null;
    }
    public void freePlayer (Player player){
          for (Player item: playersSet) {
                if (item.equals(player)){
                    item.setIsAtParty(false);
                    item.setIsBusy(false);
                    item.setIsOrganizator(false);
                    item.setSponsoredHoliday(null);
                }
            }
    }

    public Integer getFreePlayers (){
        int count = 0;
        for (Player entity : playersSet) {
            if (!entity.getIsBusy()){
                count++;
            }
        }
        return count;
    }
}
