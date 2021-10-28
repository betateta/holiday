package com.reksoft.holiday.mechanic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Setter
@Getter
public class Dice {
    private String eventName="";

    public String getMultiEventResult (HashMap<String,Integer> map){

        boolean result=false;
        Integer size = map.size();
        Integer[] values = new Integer[size];
        String[] keys = new String[size];
        Integer[] a = new Integer[size+2];

        Integer index=0;
        for (Map.Entry<String,Integer> entry: map.entrySet()) {
            values[index] = entry.getValue();
            keys[index] = entry.getKey();
            index++;
        }

        a[0] = 0;
        a[size] = 100;

        /*
        Check, when p1+p2+...pn = 100
         */
        Integer sum=0;
        for (int i = 0;i<values.length;i++){
            sum+=values[i];
        }
        /*
        If p1+p2+...pn > 100, normalize values
         */
        if (sum > 100) {
            Float coef = new Float(sum/100.0);
            for (int i =0;i<values.length;i++) {
                values[i] = Math.round(values[i]/coef);
            }
        }

        for (int i = 1;i<=size;i++){
            a[i] = a[i-1]+values[i-1];
        }
        Integer rand = (int) (Math.random()*100);
        index = 1;
        while (a[index]<rand) {
            index++;
        }
        System.out.println("rand= "+rand+" index = "+index+" value= "+values[index-1]);

        return eventName;
    }

}
