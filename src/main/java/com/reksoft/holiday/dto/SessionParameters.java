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
    private Integer sessionPlayers = 10;

    @NotNull
    @Range(min = 1,max = 100)
    private Integer sessionDuration = 1;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotChance = 70;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotMin = 5;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersAddshotMax = 10;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer playersNumberAddshot = 8;

    @NotNull
    @Range(min = 1,max = 100)
    private Integer holidaySampleFreq = 10;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayFillChance = 56;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayPushChance = 30;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidaySimpleChance = 30;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayBanquetChance = 20;

    @NotNull
    @Range(min = 0,max = 100)
    private Integer holidayDinnerChance = 50;
}
