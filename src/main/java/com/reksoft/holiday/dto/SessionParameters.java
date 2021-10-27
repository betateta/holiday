package com.reksoft.holiday.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionParameters {

    @Range(min = 3,max = 100)
    private Integer sessionPlayers;

    @Range(min = 1,max = 100)
    private Integer sessionDuration;

    @Range(min = 0,max = 100)
    private Integer playersAddshotChance;

    @Range(min = 0,max = 100)
    private Integer playersAddshotMin;

    @Range(min = 0,max = 100)
    private Integer playersAddshotMax;

    @Range(min = 0,max = 100)
    private Integer playersNumberAddshot;

    @Range(min = 1,max = 100)
    private Integer holidaySampleFreq;

    @Range(min = 0,max = 100)
    private Integer holidayFillChance;

    @Range(min = 0,max = 100)
    private Integer holidayPushChance;

    @Range(min = 0,max = 100)
    private Integer holidaySimpleChance;

    @Range(min = 0,max = 100)
    private Integer holidayBanquetChance;

    @Range(min = 0,max = 100)
    private Integer holidayDinnerChance;
}
