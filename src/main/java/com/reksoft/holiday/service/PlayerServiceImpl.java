package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public boolean savePlayer(Player player){
        if (playerRepository.findByName(player.getName())!=null) {
             return false;
        }
        playerRepository.saveAndFlush(player);
        return true;
    }
    @Override
    public void deleteAll(){
        playerRepository.deleteAll();
    }

    @Override
    public void saveAll(Set<Player> playerSet){
        playerRepository.saveAllAndFlush(playerSet);
    }

}
