package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Calculate;

import java.util.List;

public interface CalculateService {
    void deleteAll();

    void saveAll(List<Calculate> calculateList);
}
