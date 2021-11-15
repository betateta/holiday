package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.User;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class PlayersImplTest {
    private static final Logger log = Logger.getLogger(PlayersImplTest.class);
    @Autowired
    private DiceInterface diceInterface;
    private SessionParameters parameters;
    PlayersImpl players;

    private final Integer minPlayers = 3;
    private final Integer maxPlayers = 500;
    @BeforeEach
    void setUpParameters(){
        parameters= new SessionParameters();
        parameters.setUser(new User());
        parameters.setSessionPlayers(diceInterface.getRandFromRange(minPlayers,maxPlayers));
        parameters.setSessionDuration(diceInterface.getRandFromRange(1,20));
        parameters.setPlayersAddshotChance(diceInterface.getRandFromRange(0,100));
        parameters.setPlayersAddshotMin(diceInterface.getRandFromRange(0,10));
        parameters.setPlayersAddshotMax(diceInterface.getRandFromRange(parameters.getPlayersAddshotMin(),
                parameters.getPlayersAddshotMin()+10));
        parameters.setPlayersNumberAddshot(diceInterface.getRandFromRange(0,
                parameters.getSessionPlayers()));
        parameters.setHolidaySampleFreq(diceInterface.getRandFromRange(1,100));
        parameters.setHolidayFillChance(diceInterface.getRandFromRange(0,100));
        parameters.setHolidayPushChance(diceInterface.getRandFromRange(0,100));
        parameters.setHolidayBanquetChance(diceInterface.getRandFromRange(0,100));
        parameters.setHolidayDinnerChance(diceInterface.getRandFromRange(0,100));
        parameters.setHolidaySimpleChance(diceInterface.getRandFromRange(0,100));
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
