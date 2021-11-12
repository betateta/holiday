package com.reksoft.holiday.dto;

import com.reksoft.holiday.model.SessionGame;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionGameMapper {
    SessionParameters sessionToParameters (SessionGame sessionGame);
    SessionGame parametersToSession (SessionParameters sessionParameters);
}
