package com.reksoft.holiday.dto;


import com.reksoft.holiday.mechanic.Dice;
import com.reksoft.holiday.mechanic.DiceInterface;
import com.reksoft.holiday.model.User;
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

    private User user;

    @NotNull
    @Range(min = 3,max = 500)
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
    @Range(min = 0,max = 500)
    private Integer playersNumberAddshot = 8;

    @NotNull
    @Range(min = 1,max = 100)
    private Integer holidaySampleFreq = 30;

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

    public void setUpRandomParameters() {
        DiceInterface diceInterface = new Dice();
        final Integer minPlayers = 3;
        final Integer maxPlayers = 200;

        setSessionPlayers(diceInterface.getRandFromRange(minPlayers,maxPlayers));
        setSessionDuration(diceInterface.getRandFromRange(1,10));
        setPlayersAddshotChance(diceInterface.getRandFromRange(0,100));
        setPlayersAddshotMin(diceInterface.getRandFromRange(0,10));
        setPlayersAddshotMax(diceInterface.getRandFromRange(getPlayersAddshotMin(),
                getPlayersAddshotMin()+10));
        setPlayersNumberAddshot(diceInterface.getRandFromRange(0,
                getSessionPlayers()));
        setHolidaySampleFreq(diceInterface.getRandFromRange(1,100));
        setHolidayFillChance(diceInterface.getRandFromRange(0,100));
        setHolidayPushChance(diceInterface.getRandFromRange(0,100));

        while (getHolidaySimpleChance()!=0 || getHolidayBanquetChance()!=0 || getHolidayDinnerChance()!=0){
            setHolidayBanquetChance(diceInterface.getRandFromRange(0,100));
            setHolidayDinnerChance(diceInterface.getRandFromRange(0,100));
            setHolidaySimpleChance(diceInterface.getRandFromRange(0,100));
        }
    }
}
