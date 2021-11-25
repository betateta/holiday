package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;

import java.util.List;

public interface MemberService {
    void deleteAll();
    public List<Member> getMemberListByCalculate (Calculate calculate);
}
