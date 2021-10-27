package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Integer> {
    Player findByName (String name);
}