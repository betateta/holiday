package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;

import java.util.List;

public interface SessionService {
    List<SessionGame> findByUser (User user);
    void save(SessionGame session);
    void delete(SessionGame session);
    void validateParameters (SessionParameters sessionParameters) throws ValidationException;
    SessionGame findLast(User user);

}
