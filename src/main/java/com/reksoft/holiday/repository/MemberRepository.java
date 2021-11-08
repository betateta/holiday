package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository <Member, Integer> {
}
