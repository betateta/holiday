package com.reksoft.holiday.mechanic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DiceTest {
    @Test
    public void testGetSession (){
        HashMap<String,Integer> map = new HashMap<>();

        map.put("name1",70);
        testDice(map);
        map.clear();
        map.put("name1",100);
        Assertions.assertEquals(new Dice().getMultiEventResult(map),"name1");
        map.clear();
        map.put("name1",0);
        Assertions.assertEquals(new Dice().getMultiEventResult(map),"eventMiss");
        map.clear();
        map.put("name1",70);
        map.put("name2",60);
        testDice(map);
        map.clear();
        map.put("name1",30);
        map.put("name2",70);
        testDice(map);
        map.clear();
        map.put("name1",10);
        map.put("name2",60);
        testDice(map);
        map.clear();
        map.put("name1",0);
        map.put("name2",0);
        Assertions.assertEquals(new Dice().getMultiEventResult(map),"eventMiss");
        map.clear();
        map.put("name1",100);
        map.put("name2",0);
        Assertions.assertEquals(new Dice().getMultiEventResult(map),"name1");
        map.clear();
        map.put("name1",70);
        map.put("name2",60);
        map.put("name3",60);
        testDice(map);
        map.clear();
        map.put("name1",10);
        map.put("name2",20);
        map.put("name3",30);
        testDice(map);
        map.clear();
        map.put("name1",20);
        map.put("name2",10);
        map.put("name3",70);
        testDice(map);
        map.clear();
        map.put("name1",100);
        map.put("name2",0);
        map.put("name3",0);
        Assertions.assertEquals(new Dice().getMultiEventResult(map),"name1");
    }
    private void testDice(HashMap <String,Integer>map){
        for(int i = 0; i < 2; i++){
            new Dice().getMultiEventResult(map);
        }
    }
}
