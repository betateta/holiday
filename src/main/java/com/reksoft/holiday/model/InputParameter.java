package com.reksoft.holiday.model;

import javax.persistence.*;

@Table(name = "input_parameters")
@Entity
public class InputParameter {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "session_players")
    private Integer sessionPlayers;

    @Column(name = "session_duration")
    private Integer sessionDuration;

    @Column(name = "players_addshot_chance")
    private Integer playersAddshotChance;

    @Column(name = "players_addshot_min")
    private Integer playersAddshotMin;

    @Column(name = "players_addshot_max")
    private Integer playersAddshotMax;

    @Column(name = "players_number_addshot")
    private Integer playersNumberAddshot;

    @Column(name = "holiday_sample_freq")
    private Integer holidaySampleFreq;

    @Column(name = "holiday_fill_chance")
    private Integer holidayFillChance;

    @Column(name = "holiday_push_chance")
    private Integer holidayPushChance;

    @Column(name = "holiday_simple_chance")
    private Integer holidaySimpleChance;

    @Column(name = "holiday_banquet_chance")
    private Integer holidayBanquetChance;

    @Column(name = "holiday_dinner_chance")
    private Integer holidayDinnerChance;

    @OneToOne(optional=false, mappedBy="inputParameters")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getHolidayDinnerChance() {
        return holidayDinnerChance;
    }

    public void setHolidayDinnerChance(Integer holidayDinnerChance) {
        this.holidayDinnerChance = holidayDinnerChance;
    }

    public Integer getHolidayBanquetChance() {
        return holidayBanquetChance;
    }

    public void setHolidayBanquetChance(Integer holidayBanquetChance) {
        this.holidayBanquetChance = holidayBanquetChance;
    }

    public Integer getHolidaySimpleChance() {
        return holidaySimpleChance;
    }

    public void setHolidaySimpleChance(Integer holidaySimpleChance) {
        this.holidaySimpleChance = holidaySimpleChance;
    }

    public Integer getHolidayPushChance() {
        return holidayPushChance;
    }

    public void setHolidayPushChance(Integer holidayPushChance) {
        this.holidayPushChance = holidayPushChance;
    }

    public Integer getHolidayFillChance() {
        return holidayFillChance;
    }

    public void setHolidayFillChance(Integer holidayFillChance) {
        this.holidayFillChance = holidayFillChance;
    }

    public Integer getHolidaySampleFreq() {
        return holidaySampleFreq;
    }

    public void setHolidaySampleFreq(Integer holidaySampleFreq) {
        this.holidaySampleFreq = holidaySampleFreq;
    }

    public Integer getPlayersNumberAddshot() {
        return playersNumberAddshot;
    }

    public void setPlayersNumberAddshot(Integer playersNumberAddshot) {
        this.playersNumberAddshot = playersNumberAddshot;
    }

    public Integer getPlayersAddshotMax() {
        return playersAddshotMax;
    }

    public void setPlayersAddshotMax(Integer playersAddshotMax) {
        this.playersAddshotMax = playersAddshotMax;
    }

    public Integer getPlayersAddshotMin() {
        return playersAddshotMin;
    }

    public void setPlayersAddshotMin(Integer playersAddshotMin) {
        this.playersAddshotMin = playersAddshotMin;
    }

    public Integer getPlayersAddshotChance() {
        return playersAddshotChance;
    }

    public void setPlayersAddshotChance(Integer playersAddshotChance) {
        this.playersAddshotChance = playersAddshotChance;
    }

    public Integer getSessionDuration() {
        return sessionDuration;
    }

    public void setSessionDuration(Integer sessionDuration) {
        this.sessionDuration = sessionDuration;
    }

    public Integer getSessionPlayers() {
        return sessionPlayers;
    }

    public void setSessionPlayers(Integer sessionPlayers) {
        this.sessionPlayers = sessionPlayers;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}