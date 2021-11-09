package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.*;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@NoArgsConstructor
@Component
@Validated
public class CalculateSession {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private CalculateService calculateService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private MemberService memberService;

    private SessionGame sessionGame;
    private Instant currentTime;
    private PlayersInterface playersImpl;
    private CalculatesPool calculatesPool;
    private static final Logger log = Logger.getLogger(CalculateSession.class);

    public SessionGame getSessionGame(){
        initSession();
        runSession();
        saveResults();
        return sessionGame;
    }

    public void setSessionGame(SessionGame sessionGame) {
        this.sessionGame = sessionGame;
    }

    private void initSession(){
        log.info("initSession");
        sessionGame.setStartTime(Instant.now());
        sessionGame.setStopTime(sessionGame.getStartTime().plusSeconds(sessionGame.getSessionDuration()*24*3600));
        currentTime = sessionGame.getStartTime();
        calculateService.deleteAll();
        playerService.deleteAll();
        memberService.deleteAll();

        playersImpl = new PlayersImpl(sessionService.getSessionParameters(sessionGame));
        playersImpl.createNewPlayersSet();
        calculatesPool =new CalculatesPool(sessionGame, playersImpl, holidayService);
    }
    private void runSession(){
        log.info("runSession");
        //Session duration in min
        Integer timeTick = sessionGame.getHolidaySampleFreq()*60;

        /*  Session cycle      */
        while (currentTime.isBefore(sessionGame.getStopTime())){
            calculatesPool.createCalculate(currentTime);
            calculatesPool.updateCalculates(currentTime);
            currentTime = currentTime.plusSeconds(timeTick);
        }
    }

    private void saveResults(){
        log.info("saveResults");
        playerService.saveAll(playersImpl.getPlayersSet());
        calculateService.saveAll(calculatesPool.getCompletedCalculateList());

    }
}

