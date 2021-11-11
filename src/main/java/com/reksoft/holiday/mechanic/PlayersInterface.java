package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Player;

import java.util.List;
import java.util.Set;

public interface PlayersInterface {
    Set<Player> createNewPlayersSet();

    void setPlayerIsFree(Player player);

    void setPlayerIsBusy(Player player);

    Integer getNumberFreePlayers();

    Player getFreePlayer();

    Player getFreePlayerWithShots();

    List<Player> getAllFreePlayer();

    List<Player> getAllBusyPlayer();

    List<Player> getAllFreePlayerWithShots();

    Set<Player> getPlayersSet();

    Integer getPoints();

    com.reksoft.holiday.dto.SessionParameters getSessionParameters();
}
