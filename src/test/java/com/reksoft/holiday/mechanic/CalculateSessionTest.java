package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.HolidayService;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@SpringBootTest
@Component
public class CalculateSessionTest {
    @Autowired
    private SessionGameMapper sessionGameMapper;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private CalculateSession calculateSession;

    private SessionGame sessionGame;
    private SessionParameters sessionParameters;
    private final Integer sampleFreq = 60;

    private static final Logger log = Logger.getLogger(CalculateSessionTest.class);

    @BeforeEach
    void createCalculatePool(){
        log.info("Start " + this.getClass().getName());
        sessionGame = new SessionGame();
        calculateSession.setTestMode(true);
    }

    @Test
    void checkExcludeHolidaySimple() {
        sessionParameters = new SessionParameters(
                new User(),
                20,
                1,
                70,
                5,
                10,
                8,
                sampleFreq,
                50,
                50,
                0,
                30,
                30
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.run();
        sessionGame = calculateSession.getSessionGame();
        log.debug("Holidays number : " + sessionGame.getCalculateList().size());

        for (Calculate item : sessionGame.getCalculateList()
        ) {
            log.debug(item.getHoliday().getName());
            Assertions.assertFalse(item.getHoliday().getName().equals("simple"));
        }
    }
    @Test
    void checkExcludeHolidayBanquet() {
            sessionParameters = new SessionParameters(
                    new User(),
                    20,
                    1,
                    70,
                    5,
                    10,
                    8,
                    sampleFreq,
                    50,
                    50,
                    30,
                    0,
                    10
            );

            log.info(sessionParameters);
            sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
            calculateSession.buildSessionGame(sessionGame);
            calculateSession.run();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateList()
            ) {
                Assertions.assertFalse(item.getHoliday().getName().equals("banquet"));
            }
    }
    @Test
    void checkExcludeHolidayDinner() {
            sessionParameters = new SessionParameters(
                    new User(),
                    20,
                    1,
                    70,
                    5,
                    10,
                    8,
                    sampleFreq,
                    50,
                    50,
                    20,
                    10,
                    0
            );

            log.info(sessionParameters);
            sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
            calculateSession.buildSessionGame(sessionGame);
            calculateSession.run();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateList()
            ) {
                Assertions.assertFalse(item.getHoliday().getName().equals("dinner"));
            }
    }

    @Test
    void checkIncludeHolidaySimple() {
         sessionParameters = new SessionParameters(
                new User(),
                50,
                1,
                70,
                5,
                10,
                8,
                sampleFreq,
                50,
                50,
                100,
                0,
                0
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.run();
        sessionGame = calculateSession.getSessionGame();
        for (Calculate item : sessionGame.getCalculateList()
        ) {
            Assertions.assertTrue(item.getHoliday().getName().equals("simple"));
            Assertions.assertTrue(item.getMemberSet()
                    .stream().filter(member -> member.getIsOrganizator()).count() == 1);
        }
    }
        @Test
        void checkIncludeHolidayBanquet() {
            sessionParameters = new SessionParameters(
                    new User(),
                    50,
                    1,
                    70,
                    5,
                    10,
                    8,
                    sampleFreq,
                    50,
                    50,
                    0,
                    100,
                    0
            );

            log.info(sessionParameters);
            sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
            calculateSession.buildSessionGame(sessionGame);
            calculateSession.run();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateList()
            ) {
                Assertions.assertTrue(item.getHoliday().getName().equals("banquet"));
                Assertions.assertTrue(item.getMemberSet()
                        .stream().filter(member -> member.getIsOrganizator()).count() == 1);
            }
        }
        @Test
        void checkIncludeHolidayDinner(){
            sessionParameters = new SessionParameters(
                new User(),
                50,
                1,
                70,
                5,
                10,
                8,
                sampleFreq,
                50,
                50,
                0,
                0,
                100
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.run();
        sessionGame = calculateSession.getSessionGame();
        for (Calculate item:sessionGame.getCalculateList()
        ) {
            Assertions.assertTrue(item.getHoliday().getName().equals("dinner"));
            Assertions.assertTrue(item.getMemberSet()
                    .stream().filter(Member::getIsOrganizator).count() == 2);
        }
    }
}
