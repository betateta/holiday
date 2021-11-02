package com.reksoft.holiday.service;

import com.reksoft.holiday.repository.CalculateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CalculateServiceImpl {
    private final CalculateRepository calculateRepository;

    public void deleteAll(){
        calculateRepository.deleteAll();
    }
}
