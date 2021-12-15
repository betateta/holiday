package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.*;
import com.reksoft.holiday.service.HolidayService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private static final Logger log = LogManager.getLogger(CalculateSessionTest.class);

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
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();
        log.debug("Holidays number : " + sessionGame.getCalculateSet().size());

        for (Calculate item : sessionGame.getCalculateSet()
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
            calculateSession.call();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateSet()
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
            calculateSession.call();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateSet()
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
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();
        for (Calculate item : sessionGame.getCalculateSet()
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
            calculateSession.call();
            sessionGame = calculateSession.getSessionGame();
            for (Calculate item : sessionGame.getCalculateSet()
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
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();
        for (Calculate item:sessionGame.getCalculateSet()
        ) {
            Assertions.assertTrue(item.getHoliday().getName().equals("dinner"));
            Assertions.assertTrue(item.getMemberSet()
                    .stream().filter(Member::getIsOrganizator).count() == 2);
        }
    }

    @Test
    void checkCalcPoints(){
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
                34,
                12,
                20
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();

        Optional<Integer> calculatesPoints = sessionGame.getCalculateSet().stream()
                .map(Calculate::getPoints)
                .reduce((calculate, calculate2) -> calculate+calculate2);
        Optional<Integer> playersPoints = calculateSession.getPlayersPool().getPlayersSet().stream()
                .map(Player::getSessionPoints)
                .reduce((calculate, calculate2) -> calculate+calculate2);


        log.info("Calculates points = "+ calculatesPoints.get());
        log.info("Players points = "+ playersPoints.get());

        Assertions.assertTrue(calculatesPoints.get().equals(playersPoints.get()));
    }
    @Test
    void checkMemberPoints(){
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
                34,
                12,
                20
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();

        for (Calculate calc: sessionGame.getCalculateSet()
             ) {
            Optional<Integer> membersPoints = calc.getMemberSet().stream()
                    .map(Member::getHolidayPoints)
                    .reduce((member1,member2)->member1+member2);
            log.debug("Members points = "+ membersPoints.get());
            log.debug("Calculate points = "+ calc.getPoints());
            Assertions.assertTrue(calc.getPoints().equals(membersPoints.get()));
        }
    }
    @Test
    void checkCalculateConsist(){
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
                34,
                12,
                20
        );

        log.info(sessionParameters);
        sessionGame = sessionGameMapper.parametersToSession(sessionParameters);
        calculateSession.buildSessionGame(sessionGame);
        calculateSession.call();
        sessionGame = calculateSession.getSessionGame();

        for (Calculate calc: sessionGame.getCalculateSet()
        ) {
            List<Player> currentOrganizers = calc.getMemberSet()
                    .stream()
                    .filter(member -> member.getIsOrganizator())
                    .map(member -> member.getPlayer())
                    .collect(Collectors.toList());
            List<Player> currentPlayersNotOrganizer = calc.getMemberSet()
                    .stream()
                    .map(member -> member.getPlayer())
                    .filter(player -> !player.getIsOrganizer())
                    .collect(Collectors.toList());
            for (Player player: currentPlayersNotOrganizer
                 ) {
                if(currentOrganizers.contains(player)) {
                    new AssertionError();
                }
            }
        }
    }
}
