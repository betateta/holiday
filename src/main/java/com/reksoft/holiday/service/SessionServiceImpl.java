package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final SessionRepository sessionRepository;
    private static final Logger log = LogManager.getLogger(SessionServiceImpl.class);

    @Override
    public List<SessionGame> findByUser(User user) {
        List<SessionGame> sessionGameList = sessionRepository.findByUser(user);
        System.out.println("Sessions for current user: "+ sessionGameList.size());
        log.debug("Sessions for current user: "+ sessionGameList.size());
        return sessionGameList;
    }

    @Override
    public void save(SessionGame session) {
        System.out.println("Save session for current user");
        log.debug("Save session for current user");
        sessionRepository.save(session);
    }

    @Override
    public void saveAndFlush(SessionGame sessionGame) {
        sessionRepository.saveAndFlush(sessionGame);
        System.out.println("Save and flush session for current user, id:"+sessionGame.getId());
        log.debug("Save and flush session for current user, id:"+sessionGame.getId());
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
        SessionGame lastSession = sessionGameList.stream()
                .reduce((acc,sessionGame) -> {
                    if (acc.getStartTime().isAfter(sessionGame.getStartTime())) {
                        return acc;
                    }
                    else
                        return sessionGame;})
                .get();
        System.out.println("Finding last session for current user, id:"
                +lastSession.getId()
                +"; start time:"+lastSession.getStartTime());
        log.debug("Finding last session for current user, id:"+lastSession.getId());
        return lastSession;
    }

    @Override
    public SessionGame findAnyLast() {
        List<SessionGame> sessionGameList = sessionRepository.findAll();
        SessionGame lastSession = sessionGameList.stream()
                .reduce((acc,sessionGame) -> {
                    if (acc.getStartTime().isAfter(sessionGame.getStartTime())) {
                        return acc;
                    }
                    else
                        return sessionGame;})
                .get();
        System.out.println("Finding any last session, id:" + lastSession.getId());
        log.debug("Finding any last session, id:" + lastSession.getId()
                +"; start time:"+lastSession.getStartTime());
        return lastSession;
    }

    @Override
    public void flush() {
        sessionRepository.flush();
    }
}
