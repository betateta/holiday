package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.exception.CalculateException;
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
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@NoArgsConstructor
@Component
@Validated
//@Transactional
public class CalculateSession implements Callable {

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
    @Autowired
    private ProgressBar progressBar;
    @Autowired
    private DiceInterface diceInterface;

    private SessionGame sessionGame;
    private Instant currentTime;
    private PlayersInterface playersPool;
    private CalculatesPool calculatesPool;
    private Integer percentCounter = 0;
    private boolean debug = false;
    private boolean testMode = false;

    private static final Logger log = Logger.getLogger(CalculateSession.class);

    @Override
    public Integer call() {
        System.out.println("Calculate session..."
                +" days:"+sessionGame.getSessionDuration()
                +"; frequency(min):"+sessionGame.getHolidaySampleFreq());
        log.info("Calculate session..."
                +" days:"+sessionGame.getSessionDuration()
                +"; frequency(min):"+sessionGame.getHolidaySampleFreq());
        initSession();
        calc();
        if(!testMode){
            saveResults();
        }
        return percentCounter;
    }

    public void buildSessionGame(SessionGame sessionGame){
        this.sessionGame = sessionGame;
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
        playerService.saveAndFlushAll(playersPool.getPlayersSet());
        calculatesPool = new CalculatesPool(sessionGame, playersPool, holidayService);

    }

    private void calc(){
        log.info("runSession");
        //Used for calculate machine time
        Instant time = Instant.now();

        //Time tick in sec
        Integer timeTick = sessionGame.getHolidaySampleFreq()*60;
        Integer dayCount = 1;

        double coefficient = 100.0/((sessionGame.getSessionDuration()*24*60*60) / timeTick);
        Integer tickCount = 0;
        percentCounter = 0;

        progressBar.setProgress(percentCounter);
        log.info("duration = "+sessionGame.getSessionDuration()*24*60*60
                +" timetick = "+timeTick
                +" coef : "+coefficient);

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
            /*
                For more effective, change algorithm create-update of calculates with random.
                What be first: create or update.
             */
            if (diceInterface.getRandFromRange(0,100) < 50){
                try {
                    calculatesPool.createCalculate(currentTime);
                }
                catch (CalculateException ex) {
                    log.debug(ex.getMessage());
                }
                calculatesPool.updateCalculates(currentTime);
            }
            else {
                calculatesPool.updateCalculates(currentTime);
                try {
                    calculatesPool.createCalculate(currentTime);
                }
                catch (CalculateException ex) {
                    log.debug(ex.getMessage());
                }
            }
            currentTime = currentTime.plusSeconds(timeTick);

            tickCount++;
            int count =(Math.toIntExact(Math.round(tickCount * coefficient)));

            if ((count-percentCounter) >= 1) {
                percentCounter = count;
                if (percentCounter>=99) {
                    percentCounter=99;
                }
                progressBar.setProgress(percentCounter);
               // System.out.println("Thread calculate:"+Thread.currentThread().getName());
                System.out.println("progress:"+percentCounter);
            }

            log.debug("percents of calculates = "+percentCounter);
            try {
                Thread.sleep(1);
            } catch (Exception ex) {System.out.println(ex);}

        }

        log.debug("mark uncompleted calculates");
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

        Set<Calculate> calculateSet= new HashSet<>(calculatesPool.getCompletedCalculateList());
        sessionGame.setCalculateSet(calculateSet);
        System.out.println("Time for calculate,sec:"
                + Instant.now().minusSeconds(time.getEpochSecond()).getEpochSecond());
    }

    private void saveResults(){
        log.info("saveResults");
        System.out.println("Start saving and flush...");
        Instant time = Instant.now();
        sessionService.saveAndFlush(sessionGame);
        percentCounter = 100;
        progressBar.setProgress(percentCounter);
        System.out.println("Time for saving,sec:"
                + Instant.now().minusSeconds(time.getEpochSecond()).getEpochSecond());
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public PlayersInterface getPlayersPool() {
        return playersPool;
    }
}