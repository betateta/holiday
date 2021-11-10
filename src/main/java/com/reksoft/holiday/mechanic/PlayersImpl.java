package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Player;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.util.*;

@Getter
public class PlayersImpl implements PlayersInterface {
    private Set<Player> playersSet;
    private SessionParameters sessionParameters;
    private static final Logger log = Logger.getLogger(PlayersImpl .class);

    public PlayersImpl(SessionParameters sessionParameters) {
        this.sessionParameters = sessionParameters;
        this.playersSet = new HashSet<>();
    }

    @Override
    public Set<Player> createNewPlayersSet(){
        log.info("createNewPlayersSet");
        final int std_shots = 5;
        if (sessionParameters!=null) {
            for (int i = 1; i <= sessionParameters.getSessionPlayers(); i++) {
                playersSet.add(new Player("player_" + i,
                        0,
                        std_shots,0,5,
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
            for (Player player:playersSet
                 ) {
                player.setShots(std_shots+player.getBonusShots());
            }

            return playersSet;
        }
        return null;
    }
    @Override
    public void setPlayerIsFree(Player player){
          for (Player item: playersSet) {
                if (item.equals(player)){
                    item.setIsBusy(false);
                }
            }
    }
    @Override
    public void setPlayerIsBusy(Player player){
        for (Player item: playersSet) {
            if (item.equals(player)){
                item.setIsBusy(true);
            }
        }
    }

    @Override
    public Integer getNumberFreePlayers(){
        int count = 0;
        for (Player entity : playersSet) {
            if (!entity.getIsBusy()){
                count++;
            }
        }
        return count;
    }
    @Override
    public Player getFreePlayer(){
        for (Player item:playersSet
             ) {
            if (!item.getIsBusy()){
                return item;
            }
        }
        return null;
    }
    @Override
    public Player getFreePlayerWithShots(){
        for (Player item:playersSet
        ) {
            if (!item.getIsBusy() && (item.getShots() > 0)){
                return item;
            }
        }
        return null;
    }
    @Override
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
    @Override
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
    @Override
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
