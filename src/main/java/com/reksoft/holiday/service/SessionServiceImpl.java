package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;
    private final SessionConfig sessionConfig;

    @Override
    public SessionGame findByUser(User user) {
        return sessionRepository.findByUser(user);
    }

    @Override
    public void save(SessionGame session) {
        sessionRepository.saveAndFlush(session);
    }

    @Override
    public SessionGame setSessionParameters(SessionGame sessionGame, SessionParameters sessionParameters) {
        return sessionConfig.setSessionParameters(sessionGame,sessionParameters);
    }

    @Override
    public SessionParameters getSessionParameters(SessionGame sessionGame) {
        return sessionConfig.getSessionParameters(sessionGame);
    }

    @Override
    public void validateParameters(SessionParameters sessionParameters) throws ValidationException {
        if (isNull(sessionParameters)) {
            throw new ValidationException("sessionParameters object is null");
        } else if (sessionParameters.getSessionPlayers() < 3) {
            throw new ValidationException("Players number < 3");
        }
    }
}
