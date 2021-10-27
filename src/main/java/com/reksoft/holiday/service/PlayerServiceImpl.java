package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.repository.PlayerRepository;
import com.reksoft.holiday.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl {
    @Autowired
    private PlayerRepository playerRepository;

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
}
