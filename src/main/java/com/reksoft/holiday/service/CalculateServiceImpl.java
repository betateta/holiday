package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.repository.CalculateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculateServiceImpl implements CalculateService {
    private final CalculateRepository calculateRepository;

    @Override
    public void deleteAll(){
        calculateRepository.deleteAll();
    }
    @Override
    public void saveAll(List<Calculate> calculateList) {calculateRepository.saveAllAndFlush(calculateList);}
}
