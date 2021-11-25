package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import com.reksoft.holiday.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final boolean debug = false;

    @Override
    public void deleteAll() {
        memberRepository.deleteAll();
    }
    @Override
    public List<Member> getMemberListByCalculate (Calculate calculate){
        List<Member> memberList=memberRepository.findByCalculate(calculate);
        if(debug){
            System.out.println("member list of calc_id:"+calculate.getId());
            for (Member item: memberList
            ) {
                System.out.println(item.getId());
            }
        }

        return memberList;
    }
}
