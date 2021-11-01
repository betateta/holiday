package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerServiceImpl {

    private final PlayerRepository playerRepository;

    public boolean savePlayer (Player player){
        if (playerRepository.findByName(player.getName())!=null) {
             return false;
        }
        playerRepository.saveAndFlush(player);
        return true;
    }
    public void deleteAll (){
        playerRepository.deleteAll();
    }
    public void saveAll (Set<Player> playerSet){
        playerRepository.saveAll(playerSet);
    }

}
