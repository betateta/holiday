package com.reksoft.holiday.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionParameters {

    @Min(20)
    private Integer sessionPlayers;
    @Min(10)
    private Integer sessionDuration;

    @Min( value=1,message = "требуется ввести вероятность дополнительных попыток")
    private Integer playersAddshotChance;

    @Min(value=1,message = "требуется ввести минимальное количество дополнительных попыток ")
    private Integer playersAddshotMin;

    @Min(value=1,message = "требуется ввести максимальное количество дополнительных попыток")
    private Integer playersAddshotMax;

    @Min(value=1,message = "требуется ввести количество игроков с шансом на дополнительные попытки")
    private Integer playersNumberAddshot;

    @Min(value=1,message = "требуется ввести частоту дискретизации")
    private Integer holidaySampleFreq;

    @Min(value=1,message = "требуется ввести веротяность заполнения праздника")
    private Integer holidayFillChance;

    @Min(value=1,message = "требуется ввести веротяность смещения игрока с праздника")
    private Integer holidayPushChance;

    @Min(value=1,message = "требуется ввести веротяность простого праздника")
    private Integer holidaySimpleChance;

    @Min(value=1,message = "требуется ввести веротяность пиршенства")
    private Integer holidayBanquetChance;

    @Min(value=1,message = "требуется ввести веротяность ужина")
    private Integer holidayDinnerChance;
}
