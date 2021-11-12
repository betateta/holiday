package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.*;
import com.reksoft.holiday.service.HolidayService;
import lombok.Getter;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.*;

@Getter
public class CalculatesPool {
    private List<Calculate> currentCalculateList;
    private List<Calculate> completedCalculateList;
    private final SessionGame sessionGame;
    private final HolidayService holidayService;
    private final PlayersInterface playersPool;

    private final int org_points = 1500;
    private static final Logger log = Logger.getLogger(CalculatesPool.class);

    public CalculatesPool(SessionGame sessionGame, PlayersInterface playersPool, HolidayService holidayService) {
        this.sessionGame = sessionGame;
        this.playersPool = playersPool;
        this.holidayService = holidayService;
        this.completedCalculateList = new ArrayList<>();
        this.currentCalculateList = new ArrayList<>();
    }

    /*
    Method creates new Calculate and adding to current list
     */
    public void createCalculate (Instant currentTime) {
        log.info("create Calculate");

        Set<Holiday> holidaySet = holidayService.getAllSet();
        HashMap<String,Integer> holidayFullDiceMap = getHolidayDiceMap(holidaySet);
        HashMap<String,Integer> holidayWithoutDinnerDiceMap = (HashMap<String, Integer>) holidayFullDiceMap.clone();
        holidayWithoutDinnerDiceMap.remove("dinner");

        Integer freePlayers = playersPool.getNumberFreePlayers();
        String holidayName = "";
        if (freePlayers > 1) {
            holidayName = new Dice().getMultiEventResult(holidayFullDiceMap);
        } else if (freePlayers == 1) {
            holidayName = new Dice().getMultiEventResult(holidayWithoutDinnerDiceMap);
        }

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
        }
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
        kickPlayerFromHoliday(currentTime);
    }
    public Integer getPoints(){
        Integer count=0;
        for (Calculate calc: completedCalculateList
             ) {
            count+=calc.getPoints();
        }
        return count;
    }
    private void kickPlayerFromHoliday(Instant currentTime){
        /*
            Kick chance
         */
        log.debug("call kickPlayerFromHoliday");
        Player player = playersPool.getFreePlayerWithShots();
        List<Calculate> calculatesWithoutPlaces = new ArrayList<>();
        HashMap<String,Integer> kick_map = new HashMap<>();
        kick_map.put("enter",sessionGame.getHolidayFillChance());

        for (Calculate calc:currentCalculateList
        ) {
            if((calc.getCapacity()-calc.getNumberOfPlayers()) == 0){
                calculatesWithoutPlaces.add(calc);
            }
        }
        for (Calculate calc: calculatesWithoutPlaces
        ) {
            player = playersPool.getFreePlayerWithShots();
            if(player != null){
                if(new Dice().getMultiEventResult(kick_map).equals("enter")){
                    player.setShots(player.getShots()-1);
                        /*
                        kick player from holiday and calc his points
                         */
                    Set<Member> memberList = calc.getMemberSet();
                    MembersImpl membersImpl = new MembersImpl(calc, playersPool);
                    Member member = memberList.iterator().next();
                    Player kick_player= member.getPlayer();

                    member.setOutputTime(currentTime);
                    playersPool.setPlayerIsFree(kick_player);
                    member.setHolidayPoints(member.getHolidayPoints()+ membersImpl.getPointsForPeriod(member));
                        /*
                        add new player
                         */
                    if (!membersImpl.findMember(member)) {
                        calc.setUniqPlayersNumber(calc.getUniqPlayersNumber()+1);
                    }
                    playersPool.setPlayerIsBusy(member.getPlayer());
                    membersImpl.addMember(player,currentTime);

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
                    if(new Dice().getMultiEventResult(fill_map).equals("enter")){
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
                completedCalculateList.add(calc);
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
        return new Dice().getRandFromRange(holiday.getMinCapacity(),holiday.getMaxCapacity());
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
    private Integer calculatePointsForCurrentHoliday(Calculate calculate){

    return 0;
    }

}

