package com.reksoft.holiday.mechanic;

import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Component
public class Dice implements DiceInterface {
    private String eventName="";
    private final String eventMiss= "eventMiss";
    private static final Logger log = Logger.getLogger(Dice.class);

    @Override
    public String getMultiEventResult(HashMap<String, Integer> map){
        log.debug("call getMultiEventResult" );

        eventName="";
        boolean chanceHit = false;
        Integer index=0;
        Integer sum=0;
        final Integer mapSize = map.size();
        final Integer arraySize = mapSize+3;
        /*
        Arrays for keys and values
         */
        Integer[] values = new Integer[arraySize];
        String[] keys = new String[arraySize];
        /*
        Array [0:n] for numerical segments.
        [0] - always 0;
        [n-1] - for  case, when amount of probabilites (values) < 100 %.
                If sum(values) < 100 then [n-1] = 100, else, [n-1] = 0;
        [n] - always 100;
         */
        Integer[] a = new Integer[arraySize];

        a[0] = 0;
        a[mapSize+1] = 0;
        a[mapSize+2] = 100;
        values[0] = 0;
        keys[0] = "start";
        values[mapSize+1] = 0;
        keys[mapSize+1] = eventMiss;
        values[mapSize+2] = 100;
        keys[mapSize+2] = "end";

        index = 1;
        for (Map.Entry<String,Integer> entry: map.entrySet()) {
            values[index] = entry.getValue();
            keys[index] = entry.getKey();
            index++;
        }
        /*
        Check sum of probabilites
         */
        sum = 0;
        for (int i = 1; i < (arraySize-1); i++){
            sum+= values[i];
        }
        /*
        If sum > 100 %, normalize values and a[n-1] = 0; else a[n-1] = 100 - sum
         */

        if (sum > 100) {
            Float coef = new Float(sum/100.0);
            for (int i = 1; i < (arraySize-1); i++) {
                values[i] = Math.round(values[i]/coef);
            }
        }
        if (sum < 100){
            //Miss chance probability
            values[mapSize+1] = (100 - sum);
        }
        /*
        Filling array
         */
        for (int i = 1; i <= mapSize+1; i++){
            a[i] = a[i-1]+values[i];
        }
        /*
        rand = [0:100]
         */
        Integer rand = (int) (Math.random()*100);
        index = 1;
        while (a[index]<rand) {
            index++;
        }
        chanceHit = true;
        if (index == (arraySize-2)) {
            chanceHit = false;
        }
        eventName=keys[index];
        log.debug("map size = "+mapSize);
        log.debug("rand="+rand+" index="+index+" eventName="+keys[index]+" dice: "+chanceHit);

        return eventName;
    }
    @Override
    public Integer getRandFromRange(Integer min, Integer max) {
        log.debug("call getRandFromRange");
        log.debug("range["+min+":"+max+"]");
        max -= min;
        int res= (int) ((Math.random()* (++max)) + min);
        log.debug("res = "+res);
        return res;
    }

    @Override
    public String getEventName() {
        return eventName;
    }
}
