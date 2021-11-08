package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.SessionGame;

public interface SessionConfig {
    SessionGame setSessionParameters(SessionGame session, SessionParameters parameters);

    SessionParameters getSessionParameters(SessionGame session);
}
