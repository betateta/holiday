package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository <SessionGame,Integer>{
    @Query(value= "SELECT s FROM SessionGame s WHERE s.user = :user")
     SessionGame findByUser (@Param("user") User user);
}
