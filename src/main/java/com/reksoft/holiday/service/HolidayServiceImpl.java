package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Holiday;
import com.reksoft.holiday.repository.HolidayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class HolidayServiceImpl {

    private HolidayRepository holidayRepository;

    public Set<Holiday> getAllSet (){
        List<Holiday> holidayArray = holidayRepository.findAll();
        Set<Holiday> holidaySet = new HashSet<>();
        for (Holiday item: holidayArray) {
            if (!holidaySet.contains(item)){
                holidaySet.add(item);
            }
        }
        return holidaySet;
    }
    public Map<String,Holiday> getAllMapNameKey(){
        Map<String,Holiday> map = new HashMap<>();
        List<Holiday> holidayArray = holidayRepository.findAll();
        for (Holiday item: holidayArray) {
            if (!map.containsKey(item.getName())){
                map.put(item.getName(),item);
            }
        }
        return map;
    }
    public Holiday findByName (String name){
        return getAllMapNameKey().get(name);
    }
}
