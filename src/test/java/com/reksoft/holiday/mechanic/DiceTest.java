package com.reksoft.holiday.mechanic;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class DiceTest {
    private DiceInterface diceInterface = new Dice();
    private static final Logger log = Logger.getLogger(DiceTest.class);
    private final String eventMiss= "eventMiss";
    @Test
    public void testMultiEvent(){

        LinkedHashMap<String,Integer> map = new LinkedHashMap<>();
        map.put("name1",0);
        map.put("name2",0);
        map.put("name3",5);
        testDiceFullRange(map, Set.of("name3"));

        map.put("name1",0);
        map.put("name2",5);
        map.put("name3",0);
        testDiceFullRange(map,Set.of("name2"));

        map.put("name1",5);
        map.put("name2",0);
        map.put("name3",0);
        testDiceFullRange(map, Set.of("name1"));

        map.put("name1",70);
        testDiceFullRange(map, Set.of("name1"));

        map.put("name1",100);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");

        map.put("name1",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),eventMiss);

        map.put("name1",70);
        map.put("name2",60);
        testDiceFullRange(map, Set.of("name1","name2"));

        map.put("name1",30);
        map.put("name2",70);
        testDiceFullRange(map, Set.of("name1","name2"));

        map.put("name1",10);
        map.put("name2",60);
        testDiceFullRange(map, Set.of("name1","name2"));

        map.put("name1",0);
        map.put("name2",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),eventMiss);

        map.put("name1",100);
        map.put("name2",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");

        map.put("name1",70);
        map.put("name2",60);
        map.put("name3",60);
        testDiceFullRange(map, Set.of("name1","name2","name3"));

        map.put("name1",10);
        map.put("name2",20);
        map.put("name3",30);
        testDiceFullRange(map, Set.of("name1","name2","name3"));

        map.put("name1",20);
        map.put("name2",10);
        map.put("name3",70);
        testDiceFullRange(map, Set.of("name1","name2","name3"));

        map.put("name1",100);
        map.put("name2",0);
        map.put("name3",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");
        map.put("name1",0);
        map.put("name2",0);
        map.put("name3",100);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name3");
        map.put("name1",0);
        map.put("name2",100);
        map.put("name3",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name2");
        map.put("name1",0);
        map.put("name2",0);
        map.put("name3",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),eventMiss);

    }
    @Test
    public void testRange(){
        Assertions.assertEquals(diceInterface.getRandFromRange(0,0),0);
        Assertions.assertEquals(diceInterface.getRandFromRange(1,1),1);
    }
    private void testDice(LinkedHashMap <String,Integer>map){
        diceInterface.getMultiEventResult(map);
        map.clear();
        log.info(diceInterface.getEventName());
    }
    private void testDiceFullRange(LinkedHashMap<String,Integer> map, Set<String> expectedEventsList){
        diceInterface.setDebug(false);
        String event="";
        List<String> eventsStringList = new ArrayList<>(expectedEventsList);
        for (int i = 0; i <= 100 ; i++) {
            diceInterface.setRand(i);
            event = diceInterface.getMultiEventResult(map);
            switch (expectedEventsList.size()){
                case 1:{
                    Assertions.assertTrue(event.equals(eventMiss)
                            || event.equals(eventsStringList.get(0)));
                    break;
                }
                case 2:{
                    Assertions.assertTrue(event.equals(eventMiss)
                            || event.equals(eventsStringList.get(0))
                            || event.equals(eventsStringList.get(1)));
                    break;
                }
                case 3:{
                    Assertions.assertTrue(event.equals(eventMiss)
                            || event.equals(eventsStringList.get(0))
                            || event.equals(eventsStringList.get(1))
                            || event.equals(eventsStringList.get(2)));
                    break;
                }
                default:{
                    break;
                }
            }

        }
        map.clear();
        diceInterface.setDebug(false);
    }

}
