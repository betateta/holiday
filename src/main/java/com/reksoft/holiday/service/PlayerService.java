package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Player;

import java.util.List;
import java.util.Set;

public interface PlayerService {
    boolean savePlayer(Player player);

    void deleteAll();
    void saveAll(Set<Player> playerSet);
    List<Player> getAll();
}
