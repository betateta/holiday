package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.repository.CalculateRepository;
import com.reksoft.holiday.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculateServiceImpl implements CalculateService {
    @Autowired
    private CalculateRepository calculateRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void deleteAll(){

        calculateRepository.deleteAll();
    }
    @Override
    public void saveAndFlushAll(List<Calculate> calculateList) {
        calculateRepository.saveAllAndFlush(calculateList);}

    @Override
    public void saveAll(List<Calculate> calculateList) {
        calculateRepository.saveAll(calculateList);
    }

}
