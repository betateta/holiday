package com.reksoft.holiday.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionParameters {

    @NotNull
    @Range(min = 3,max = 100)
    private Integer sessionPlayers;

    @NotNull
    @Range(min = 1,max = 100)
    private Integer sessionDuration;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotChance;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotMin;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotMax;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersNumberAddshot;

    @NotNull
    @Range(min = 1,max = 100)
    private Integer holidaySampleFreq;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayFillChance;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayPushChance;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidaySimpleChance;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayBanquetChance;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayDinnerChance;
}
