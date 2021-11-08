package com.reksoft.holiday.service;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.model.SessionGame;
import org.springframework.stereotype.Component;

@Component
public class SessionConfigImpl implements SessionConfig {
    @Override
    public SessionGame setSessionParameters(SessionGame session, SessionParameters parameters){
        session.setSessionPlayers(parameters.getSessionPlayers());
        session.setSessionDuration(parameters.getSessionDuration());
        session.setPlayersAddshotChance(parameters.getPlayersAddshotChance());
        session.setPlayersAddshotMin(parameters.getPlayersAddshotMin());
        session.setPlayersAddshotMax(parameters.getPlayersAddshotMax());
        session.setPlayersNumberAddshot(parameters.getPlayersNumberAddshot());
        session.setHolidaySampleFreq(parameters.getHolidaySampleFreq());
        session.setHolidayFillChance(parameters.getHolidayFillChance());
        session.setHolidayPushChance(parameters.getHolidayPushChance());
        session.setHolidaySimpleChance(parameters.getHolidaySimpleChance());
        session.setHolidayBanquetChance(parameters.getHolidayBanquetChance());
        session.setHolidayDinnerChance(parameters.getHolidayDinnerChance());
        return session;
    }

    @Override
    public SessionParameters getSessionParameters(SessionGame session){
        SessionParameters parameters = new SessionParameters();
        return parameters.builder()
                .sessionPlayers(session.getSessionPlayers())
                .sessionDuration(session.getSessionDuration())
                .playersAddshotChance(session.getPlayersAddshotChance())
                .playersAddshotMin(session.getPlayersAddshotMin())
                .playersAddshotMax(session.getPlayersAddshotMax())
                .playersNumberAddshot(session.getPlayersNumberAddshot())
                .holidaySampleFreq(session.getHolidaySampleFreq())
                .holidayFillChance(session.getHolidayFillChance())
                .holidayPushChance(session.getHolidayPushChance())
                .holidaySimpleChance(session.getHolidaySimpleChance())
                .holidayBanquetChance(session.getHolidayBanquetChance())
                .holidayDinnerChance(session.getHolidayDinnerChance())
                .build();

    }
}
