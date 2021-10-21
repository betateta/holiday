package com.reksoft.holiday.service;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private SessionRepository sessionRepository;

   @Override
    public SessionGame findByUser(User user) {
        return sessionRepository.findByUser(user);
    }

    @Override
    public void save(SessionGame session) {
        sessionRepository.saveAndFlush(session);
    }


}
