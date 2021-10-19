package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.InputParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InputParameterRepo extends JpaRepository <InputParameter, Integer> {
}
