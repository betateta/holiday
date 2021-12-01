package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.Player;

import java.util.List;
import java.util.Set;

public interface PlayersInterface {
    Set<Player> createNewPlayersSet();

    void setPlayerIsFree(Player player);

    void setPlayerIsBusy(Player player);
    void setPlayerIsOrganizer(Player player,boolean value);

    Integer getNumberFreePlayers();
    List<Player> getAllOrganizers();
    Player getNotOrganizerPlayer();

    Player getFreePlayer();

    Player getFreePlayerWithShots();

    List<Player> getAllFreePlayer();

    List<Player> getAllBusyPlayer();

    List<Player> getAllFreePlayerWithShots();

    Set<Player> getPlayersSet();

    Integer getPoints();

    List<Player> getAllFreePlayerExcludeOrganizers();

    com.reksoft.holiday.dto.SessionParameters getSessionParameters();
}
