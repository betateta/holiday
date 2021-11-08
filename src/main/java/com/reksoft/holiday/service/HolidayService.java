package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Holiday;

import java.util.Map;
import java.util.Set;

public interface HolidayService {
    Set<Holiday> getAllSet();

    Map<String, Holiday> getAllMapNameKey();

    Holiday findByName(String name);
}
