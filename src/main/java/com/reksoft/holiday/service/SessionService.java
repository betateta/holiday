package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;

public interface SessionService {
    SessionGame findByUser (User user);
    void save(SessionGame session);
    void delete(SessionGame session);
    SessionGame setSessionParameters (SessionGame sessionGame, SessionParameters sessionParameters);
    SessionParameters getSessionParameters (SessionGame sessionGame);
    void validateParameters (SessionParameters sessionParameters) throws ValidationException;

}
