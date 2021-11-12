package com.reksoft.holiday.repository;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SessionRepository extends JpaRepository <SessionGame,Integer>{
    @Query(value= "SELECT s FROM SessionGame s WHERE s.user = :user")
     List<SessionGame> findByUser (@Param("user") User user);
}
