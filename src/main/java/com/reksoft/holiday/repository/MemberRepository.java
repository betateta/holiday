package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository <Member, Integer> {
    @Query(value= "SELECT s FROM Member s WHERE s.calculate = :calculate")
    List<Member> findByCalculate (@Param("calculate") Calculate calculate);
}
