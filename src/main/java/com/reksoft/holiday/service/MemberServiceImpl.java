package com.reksoft.holiday.service;

import com.reksoft.holiday.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
