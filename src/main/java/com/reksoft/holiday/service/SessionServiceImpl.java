package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;

    @Override
    public List<SessionGame> findByUser(User user) {
        return sessionRepository.findByUser(user);
    }

    @Override
    public void save(SessionGame session) {
        sessionRepository.saveAndFlush(session);
    }

    @Override
    public void validateParameters(SessionParameters sessionParameters) throws ValidationException {
        if (isNull(sessionParameters)) {
            throw new ValidationException("sessionParameters object is null");
        } else if (sessionParameters.getSessionPlayers() < sessionParameters.getPlayersNumberAddshot()) {
            throw new ValidationException("Number of players smaller the number of players with add shot chance");
        }
    }

    @Override
    public void delete(SessionGame session) {
        sessionRepository.delete(session);
    }

    @Override
    public SessionGame findLast(User user) {
        List<SessionGame> sessionGameList = findByUser(user);
        SessionGame lastSession = sessionGameList.stream().reduce((acc,y) -> {
            if (acc.getStopTime().isAfter(y.getStopTime())) {return acc;}
            else return y;}).get();
        return lastSession;
    }
}
