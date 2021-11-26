package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.exception.CalculateException;
import com.reksoft.holiday.model.*;
import com.reksoft.holiday.service.HolidayService;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.*;

@Getter
public class CalculatesPool {
    private DiceInterface diceInterface;
    private List<Calculate> currentCalculateList;
    private List<Calculate> completedCalculateList;
    private final SessionGame sessionGame;
    private final HolidayService holidayService;
    private final PlayersInterface playersPool;
    private final int org_points = 1500;
    private static final Logger log = Logger.getLogger(CalculatesPool.class);
    private final boolean debug = true;

    public CalculatesPool(SessionGame sessionGame, PlayersInterface playersPool, HolidayService holidayService) {
        this.sessionGame = sessionGame;
        this.playersPool = playersPool;
        this.holidayService = holidayService;
        this.completedCalculateList = new ArrayList<>();
        this.currentCalculateList = new ArrayList<>();
        this.diceInterface = new Dice();

    }

    /*
    Method creates new Calculate and adding to current list
     */
    public void createCalculate (Instant currentTime) throws CalculateException {
        log.info("create Calculate");

        Set<Holiday> holidaySet = holidayService.getAllSet();

        HashMap<String,Integer> holidayFullDiceMap = getHolidayDiceMap(holidaySet);
        HashMap<String,Integer> holidayWithoutDinnerDiceMap = (HashMap<String, Integer>) holidayFullDiceMap.clone();
        holidayWithoutDinnerDiceMap.remove("dinner");

        Integer freePlayers = playersPool.getNumberFreePlayers();
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = diceInterface.getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = diceInterface.getMultiEventResult(holidayWithoutDinnerDiceMap);
        } else throw new CalculateException ("No free players for holiday begin");

        if (!holidayName.isBlank() && !holidayName.isEmpty() && !holidayName.equals("eventMiss")) {

            Calculate calculate = new Calculate();
            calculate.setMemberSet(new HashSet<>());
            MembersInterface membersImpl = new MembersImpl(calculate, playersPool);

            if (holidayName.equals("dinner")) {
                membersImpl.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
                membersImpl.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
            } else {
                membersImpl.addMemberAsOrganizator(playersPool.getFreePlayer(),currentTime);
            }
            calculate.setNumberOfPlayers(0);
            calculate.setHoliday(holidayService.findByName(holidayName));
            calculate.setSession(sessionGame);
            calculate.setCapacity(getHolidayCapacity(holidayService.findByName(holidayName)));
            calculate.setStartTime(currentTime);
            calculate.setPoints(0);
            calculate.setUniqPlayersNumber(0);
            calculate.setMemberSet(membersImpl.getMemberSet());
            log.debug("add Calculate to current list");
            currentCalculateList.add(calculate);
        } else throw new CalculateException("Holiday name : "+holidayName);

    }
    /*
    method include checks:
        - expiration by time of holiday
        - expiration by day (24 hours)
        - add new players to holiday (calculate)
        - kick players from holiday (calculate)
     */
    public void updateCalculates(Instant currentTime){
        log.info("call updateCalculates");

        checkHolidaysExpiration(currentTime);
        addPlayersToHolidays(currentTime);
        try {
            kickPlayerFromHoliday(currentTime);
        } catch (CalculateException ce) {log.error(ce);}

    }

    private void kickPlayerFromHoliday(Instant currentTime) throws CalculateException {
        /*
            Kick chance
         */
        log.debug("call kickPlayerFromHoliday");
        List<Calculate> calculatesWithoutPlaces = new ArrayList<>();
        HashMap<String,Integer> kick_map = new HashMap<>();
        kick_map.put("kick",sessionGame.getHolidayPushChance());

        for (Calculate calc:currentCalculateList
        ) {
            log.debug("calc_capacity = "+calc.getCapacity()+" players = "+calc.getNumberOfPlayers());
            if((calc.getCapacity()-calc.getNumberOfPlayers()) == 0){
                calculatesWithoutPlaces.add(calc);
                log.debug("calc was not free places");
            }
        }
        for (Calculate calc: calculatesWithoutPlaces
        ) {
            Player addingPlayer = playersPool.getFreePlayerWithShots();
            if(addingPlayer != null){
                if(diceInterface.getMultiEventResult(kick_map).equals("kick")){
                    //minus 1 shots
                    addingPlayer.setShots(addingPlayer.getShots()-1);
                        /*
                        kick player from holiday and calc his points
                         */
                    MembersImpl membersImpl = new MembersImpl(calc, playersPool);
                    /*
                       We need find active member (without output time, not organisation)
                     */
                    Set<Member> memberSet= calc.getMemberSet();
                    Member activeMember = new Member();
                    
                    /* Check output time*/
                    for (Member item: memberSet
                         ) {
                        if ((item.getOutputTime() == null) && !item.getIsOrganizator()){
                            activeMember = item;
                            break;
                        }
                    }
                    if (activeMember != null){
                        Player kick_player= activeMember.getPlayer();

                        activeMember.setOutputTime(currentTime);
                        playersPool.setPlayerIsFree(kick_player);
                        activeMember.setHolidayPoints(activeMember.getHolidayPoints() +
                                membersImpl.getPointsForPeriod(activeMember));
                          /*
                        add new player
                         */
                        playersPool.setPlayerIsBusy(addingPlayer);
                        membersImpl.addMember(addingPlayer,currentTime);
                        if (!membersImpl.findPlayer(addingPlayer)) {
                            calc.setUniqPlayersNumber(calc.getUniqPlayersNumber()+1);
                        }
                    } else {
                        throw new CalculateException("Error: was no active members for holiday without free places");
                    }
                }
            }
        }
    }
    /*
        Input data:
       - list of holidays(calculates) with free places
       - list of free players with shots >0
    */
    private void addPlayersToHolidays(Instant currentTime){
        log.debug("call addPlayersToHolidays");
        Player player = playersPool.getFreePlayerWithShots();
        if(!currentCalculateList.isEmpty() && (player != null)) {
            List<Calculate> calculatesWithPlaces = new ArrayList<>();
            for (Calculate calc:currentCalculateList
            ) {
                if((calc.getCapacity()-calc.getNumberOfPlayers()) > 0){
                    calculatesWithPlaces.add(calc);
                }
            }
            /*
            Fill chance
             */
            HashMap<String,Integer> fill_map = new HashMap<>();
            fill_map.put("enter",sessionGame.getHolidayFillChance());
            for (Calculate calc: calculatesWithPlaces
                 ) {
                player = playersPool.getFreePlayerWithShots();
                if(player != null){
                    if(diceInterface.getMultiEventResult(fill_map).equals("enter")){
                        player.setShots(player.getShots()-1);
                        playersPool.setPlayerIsBusy(player);
                        MembersInterface membersImpl = new MembersImpl(calc, playersPool);
                        membersImpl.addMember(player,currentTime);
                        calc.setNumberOfPlayers(calc.getNumberOfPlayers()+1);
                        if (!membersImpl.findPlayer(player)) {
                            calc.setUniqPlayersNumber(calc.getUniqPlayersNumber()+1);
                        }
                    }
                }
            }
        }
    }
    private void checkHolidaysExpiration (Instant currentTime){
        log.debug("call checkHolidaysExpiration");
        for (Calculate calc : currentCalculateList
        ) {
            // complete  holiday by time expiration
            if (currentTime.isAfter(calc.getStartTime().plusSeconds(calc.getHoliday().getDuration() * 3600))) {
                calc.setStopTime(currentTime);
                int total_players_points = 0;

                MembersInterface membersImpl = new MembersImpl(calc, playersPool);
                Set<Member> organizators = membersImpl.getOrganizators();
                Set<Member> players = membersImpl.getAllWithoutOrganizators();
                if (players!= null) {
                    calc.setUniqPlayersNumber(players.size());
                    // freeing players and calc points
                    for (Member member: players
                    ) {
                        /* if member is active*/
                        if (member.getOutputTime() == null){
                            member.setOutputTime(currentTime);

                            /*duration of last period*/
                            long duration = currentTime.getEpochSecond() - member.getInputTime().getEpochSecond();
                            member.setDuration(member.getDuration() + duration);

                            /* points of last period*/
                            int points = membersImpl.getPointsForPeriod(member);
                            member.setHolidayPoints(member.getHolidayPoints() + points);
                            total_players_points += member.getHolidayPoints();

                            playersPool.setPlayerIsFree(member.getPlayer());
                        }
                    }
                }
                int points=0;
                for (Member org: organizators
                     ) {
                    playersPool.setPlayerIsFree(org.getPlayer());
                    org.setOutputTime(currentTime);
                    points+= org_points;
                    org.setHolidayPoints(org_points + total_players_points);
                }
                /* total holiday points */
                Set<Member> allMembers = membersImpl.getAll();
                for (Member member: allMembers
                     ) {
                    if(member.getCalculate().equals(calc)){
                        calc.setPoints(calc.getPoints()+member.getHolidayPoints());
                    }
                }
                //calc.setPoints(total_players_points+points);
                /*
                points for each player
                 */

                for (Player player: playersPool.getPlayersSet()
                     ) {
                    points=0;
                    for (Member member: calc.getMemberSet()
                         ) {
                        if(member.getPlayer().equals(player)){
                            points+=member.getHolidayPoints();
                        }
                    }
                    player.setSessionPoints(player.getSessionPoints()+points);
                }
                calc.setCorrectStop(true);
                addToCompleteList(calc);
            }
        }
        excludeCompletedFromCurrent();
    }
    private void excludeCompletedFromCurrent(){
        log.debug("call excludeCompletedFromCurrent");
        for (Calculate item: completedCalculateList
             ) {
            if (currentCalculateList.contains(item)){
                currentCalculateList.remove(item);
            }
        }
    }
    private Integer getHolidayCapacity(Holiday holiday){
        log.debug("call getHolidayCapacity");
        return diceInterface.getRandFromRange(holiday.getMinCapacity(),holiday.getMaxCapacity());
    }
    private HashMap<String,Integer> getHolidayDiceMap(Set<Holiday> holidaySet){
        log.debug("call getHolidayDiceMap");
        HashMap<String,Integer> holidayFullDiceMap = new HashMap<>();
        for (Holiday item:holidaySet
        ) {
            switch (item.getName()) {

                case ("simple"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidaySimpleChance());
                    break;
                case ("banquet"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidayBanquetChance());
                    break;
                case ("dinner"):
                    holidayFullDiceMap.put(item.getName(), sessionGame.getHolidayDinnerChance());
                    break;
                default:
                    break;
            }
        }
        return holidayFullDiceMap;
    }
    private void addToCompleteList(Calculate calculate){
        if(debug){
           // System.out.println("Calculate pool: adding to complete list");
        }
        completedCalculateList.add(calculate);
    }

}

