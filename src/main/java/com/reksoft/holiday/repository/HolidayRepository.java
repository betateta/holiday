package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {
}