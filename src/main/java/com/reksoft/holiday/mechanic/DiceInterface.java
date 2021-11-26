package com.reksoft.holiday.mechanic;

import java.util.HashMap;

public interface DiceInterface {

    String getMultiEventResult(HashMap<String, Integer> map);
    Integer getRandFromRange(Integer min, Integer max);
    String getEventName();
    void setDebug (boolean debug);
    void setRand(Integer rand);
}
