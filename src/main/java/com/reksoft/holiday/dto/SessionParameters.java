package com.reksoft.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Validated
public class SessionParameters {

    @Min(5)
    private Integer sessionPlayers;

    private Integer sessionDuration;

    @NotBlank(message = "требуется ввести вероятность дополнительных попыток")
    private Integer playersAddshotChance;

    @NotBlank(message = "требуется ввести минимальное количество дополнительных попыток ")
    private Integer playersAddshotMin;

    @NotBlank(message = "требуется ввести максимальное количество дополнительных попыток")
    private Integer playersAddshotMax;

    @NotBlank(message = "требуется ввести количество игроков с шансом на дополнительные попытки")
    private Integer playersNumberAddshot;

    @NotBlank(message = "требуется ввести частоту дискретизации")
    private Integer holidaySampleFreq;

    @NotBlank(message = "требуется ввести веротяность заполнения праздника")
    private Integer holidayFillChance;

    @NotBlank(message = "требуется ввести веротяность смещения игрока с праздника")
    private Integer holidayPushChance;

    @NotBlank(message = "требуется ввести веротяность простого праздника")
    private Integer holidaySimpleChance;

    @NotBlank(message = "требуется ввести веротяность пиршенства")
    private Integer holidayBanquetChance;

    @NotBlank(message = "требуется ввести веротяность ужина")
    private Integer holidayDinnerChance;
}
