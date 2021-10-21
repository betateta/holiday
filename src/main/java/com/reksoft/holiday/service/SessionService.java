package com.reksoft.holiday.service;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;

public interface SessionService {
    SessionGame findByUser (User user);
    void save(SessionGame session);


}
