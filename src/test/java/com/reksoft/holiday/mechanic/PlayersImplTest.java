package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayersImplTest {
    private static final Logger log = LogManager.getLogger(PlayersImplTest.class);
    @Autowired
    private DiceInterface diceInterface;
    private SessionParameters parameters;
    PlayersImpl players;

    private final Integer minPlayers = 3;
    private final Integer maxPlayers = 500;
    @BeforeEach
    void setUpParameters(){
        log.info("Start " + this.getClass().getName());
        parameters = new SessionParameters(
                new User(),
                100,
                5,
                70,
                5,
                10,
                8,
                10,
                50,
                50,
                30,
                30,
                30
                );
        log.info(parameters);
        players = new PlayersImpl(parameters);
        players.createNewPlayersSet();
    }

    @Test
    void createNewPlayersSet(){
        Assertions.assertEquals(parameters.getSessionPlayers(),players.getPlayersSet().size());
        Assertions.assertTrue(players.getAllFreePlayer().size() >= minPlayers);
        Assertions.assertTrue(players.getAllFreePlayer().size() <= maxPlayers);
        Assertions.assertNotNull(players.getFreePlayer());
        Assertions.assertEquals(players.getPoints(),0);
        Assertions.assertTrue(players.getAllFreePlayerWithShots().size() == players.getPlayersSet().size());
        Assertions.assertTrue(players.getAllBusyPlayer().size()==0);


    }
    @Test
    void playerFreeOrBusy(){
        Player player = players.getPlayersSet().iterator().next();
        players.setPlayerIsBusy(player);
        Assertions.assertTrue(players.getAllBusyPlayer().size()==1);
        players.setPlayerIsFree(player);
        Assertions.assertTrue(players.getAllBusyPlayer().size()==0);
    }
}
