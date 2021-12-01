package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private static final Logger log = Logger.getLogger(PlayerServiceImpl.class);

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
        log.debug("save all players");
        playerRepository.saveAll(playerSet);
    }

    @Override
    public void saveAndFlushAll(Set<Player> playerSet) {
        playerRepository.saveAllAndFlush(playerSet);
    }

    @Override
    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    @Override
    public void flush() {
        playerRepository.flush();
    }
}
