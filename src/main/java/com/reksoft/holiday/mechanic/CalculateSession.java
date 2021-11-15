package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Player;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.service.*;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
    @Autowired
    private SessionGameMapper sessionGameMapper;

    private SessionGame sessionGame;
    private Instant currentTime;
    private PlayersInterface playersPool;
    private CalculatesPool calculatesPool;
    private static final Logger log = Logger.getLogger(CalculateSession.class);

    public SessionGame buildSessionGame(SessionGame sessionGame){
        this.sessionGame = sessionGame;
        initSession();
        runSession();
        saveResults();
        return sessionGame;
    }
    public SessionGame getSessionGame(){
        return sessionGame;
    }

    private void initSession(){
        log.info("initSession");

        Instant time = Instant.now();
        time = time.plusSeconds(3*3600);
        log.info("init time: "+time);

        sessionGame.setStartTime(time.truncatedTo(ChronoUnit.SECONDS));
        sessionGame.setStopTime(sessionGame.getStartTime().plusSeconds(sessionGame.getSessionDuration()*24*3600).
                truncatedTo(ChronoUnit.SECONDS));
        currentTime = sessionGame.getStartTime();

        calculateService.deleteAll();
        playerService.deleteAll();

        playersPool = new PlayersImpl(sessionGameMapper.sessionToParameters(sessionGame));
        playersPool.createNewPlayersSet();
        calculatesPool = new CalculatesPool(sessionGame, playersPool, holidayService);
    }
    private void runSession(){
        log.info("runSession");
        //Session duration in min
        Integer timeTick = sessionGame.getHolidaySampleFreq()*60;
        Integer dayCount = 1;
        Instant dayStamp = sessionGame.getStartTime().plus(dayCount, ChronoUnit.DAYS);
        log.info("new day of gaming session : "+dayCount);
        /*  Session cycle      */
        while (currentTime.isBefore(sessionGame.getStopTime())){
            if (currentTime.isAfter(dayStamp)){
                dayCount++;
                dayStamp = sessionGame.getStartTime().plus(dayCount, ChronoUnit.DAYS);
                log.info("new day of gaming session : "+dayCount);

                for (Player player: playersPool.getPlayersSet()
                     ) {
                    player.setStdShots(5);
                    player.setShots(player.getBonusShots()+player.getStdShots());
                }
            }
            calculatesPool.createCalculate(currentTime);
            calculatesPool.updateCalculates(currentTime);
            currentTime = currentTime.plusSeconds(timeTick);
        }
        log.debug("mark incompleted calculates");
        for (Calculate calc : calculatesPool.getCurrentCalculateList()
                ) {
            calc.setCorrectStop(false);
        }
        sessionGame.setNumberOfIncompleteHolidays(calculatesPool.getCurrentCalculateList().size());
        sessionGame.setNumberOfHolidays(
                calculatesPool.getCompletedCalculateList().size()+
                calculatesPool.getCurrentCalculateList().size());
        log.debug("adding session points");
        sessionGame.setPoints(playersPool.getPoints());
        sessionGame.setCalculateList(calculatesPool.getCompletedCalculateList());
    }

    private void saveResults(){
        log.info("saveResults");
        playerService.saveAll(playersPool.getPlayersSet());
        sessionService.save(sessionGame);
    }
}

