package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Holiday;
import com.reksoft.holiday.repository.HolidayRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private HolidayRepository holidayRepository;

    @Override
    public Set<Holiday> getAllSet(){
        List<Holiday> holidayArray = holidayRepository.findAll();
        Set<Holiday> holidaySet = new HashSet<>();
        for (Holiday item: holidayArray) {
            if (!holidaySet.contains(item)){
                holidaySet.add(item);
            }
        }
        return holidaySet;
    }
    @Override
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
    @Override
    public Holiday findByName(String name){
        return getAllMapNameKey().get(name);
    }
}
