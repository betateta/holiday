package com.reksoft.holiday.mechanic;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DiceTest {
    private DiceInterface diceInterface = new Dice();
    private static final Logger log = Logger.getLogger(DiceTest.class);
    private final String eventMiss= "eventMiss";
    @Test
    public void testMultiEvent(){
        HashMap<String,Integer> map = new HashMap<>();
        map.put("name1",70);
        testDice(map);
        map.put("name1",100);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");
        Assertions.assertEquals(diceInterface.getEventName(),"name1");
        map.put("name1",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),eventMiss);
        Assertions.assertEquals(diceInterface.getEventName(),eventMiss);
        map.put("name1",70);
        map.put("name2",60);
        testDice(map);
        map.put("name1",30);
        map.put("name2",70);
        testDice(map);
        map.put("name1",10);
        map.put("name2",60);
        testDice(map);
        map.put("name1",0);
        map.put("name2",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),eventMiss);
        map.put("name1",100);
        map.put("name2",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");
        map.put("name1",70);
        map.put("name2",60);
        map.put("name3",60);
        testDice(map);
        map.put("name1",10);
        map.put("name2",20);
        map.put("name3",30);
        testDice(map);
        map.put("name1",20);
        map.put("name2",10);
        map.put("name3",70);
        testDice(map);
        map.put("name1",100);
        map.put("name2",0);
        map.put("name3",0);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name1");
        map.put("name1",0);
        map.put("name2",0);
        map.put("name3",100);
        Assertions.assertEquals(diceInterface.getMultiEventResult(map),"name3");
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
    private void testDice(HashMap <String,Integer>map){
        diceInterface.getMultiEventResult(map);
        map.clear();
        log.info(diceInterface.getEventName());
    }

}
