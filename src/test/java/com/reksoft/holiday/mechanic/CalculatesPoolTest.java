package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.CalculateException;
import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.HolidayService;
import com.reksoft.holiday.service.SessionService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.Instant;

@SpringBootTest
@Component
public class CalculatesPoolTest {
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionGameMapper sessionGameMapper;

    private PlayersInterface playersInterface;
    private SessionGame sessionGame;
    private CalculatesPool calculatesPool;
    private static final Logger log = Logger.getLogger(CalculatesPoolTest.class);

    @BeforeEach
    void createCalculatePool(){
        sessionGame= new SessionGame();
        SessionParameters sessionParameters = new SessionParameters();
        sessionParameters.setUpRandomParameters();

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        PlayersInterface playersInterface = new PlayersImpl(sessionParameters);
        playersInterface.createNewPlayersSet();
        calculatesPool = new CalculatesPool(sessionGame,playersInterface,holidayService);
    }
    @Test
    void createCalculateTest(){
        Assertions.assertNotNull(calculatesPool);
        Assertions.assertNotNull(calculatesPool.getCurrentCalculateList());
        Assertions.assertNotNull(calculatesPool.getCompletedCalculateList());
        for (int i = 0 ; i < 10;i++){
            try{
                calculatesPool.createCalculate(Instant.now());
            } catch (CalculateException ex){
                log.info(ex.getMessage());
            }
            if (!calculatesPool.getCurrentCalculateList().isEmpty()){
                Assertions.assertTrue(calculatesPool.getCurrentCalculateList().size() == 1);
                Assertions.assertTrue(calculatesPool.getCompletedCalculateList().size() == 0);
                Calculate calculate = calculatesPool.getCurrentCalculateList().get(0);
                Assertions.assertNotNull(calculate.getHoliday());
                Assertions.assertNotNull(calculate.getSession());
            }
        }
    }
}
