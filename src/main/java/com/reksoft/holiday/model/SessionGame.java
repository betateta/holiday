package com.reksoft.holiday.model;

import lombok.*;
import org.hibernate.Hibernate;

import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import javax.validation.constraints.Min;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


import java.time.Instant;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "sessions")
@Entity
public class SessionGame {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(optional=false,  cascade=CascadeType.ALL)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "stop_time")
    private Instant stopTime;

    @Column(name = "session_points")
    private Integer points;

    @Column(name = "number_of_holidays")
    private Integer numberOfHolidays;

    @NotBlank(message = "требуется ввести количество игроков")
    @Column(name = "session_players")
    private Integer sessionPlayers;

    //@NotBlank(message = "требуется ввести длительность сессии")
    //@Min(value=10, message="значение должно быть больше нуля")
    //@Max(value=3, message="значение должно быть меньше 100")
    //@Range(min = 0l,max=1l, message = "Please select positive numbers Only")
    @Column(name = "session_duration")
    //@Min(value=1, message="значение должно быть больше нуля")
    @Positive
    private int sessionDuration;


    @NotBlank(message = "требуется ввести вероятность дополнительных попыток")
    @Column(name = "players_addshot_chance")
    private Integer playersAddshotChance;

    @NotBlank(message = "требуется ввести минимальное количество дополнительных попыток ")
    @Column(name = "players_addshot_min")
    private Integer playersAddshotMin;

    @NotBlank(message = "требуется ввести максимальное количество дополнительных попыток")
    @Column(name = "players_addshot_max")
    private Integer playersAddshotMax;

    @NotBlank(message = "требуется ввести количество игроков с шансом на дополнительные попытки")
    @Column(name = "players_number_addshot")
    private Integer playersNumberAddshot;

    @NotBlank(message = "требуется ввести частоту дискретизации")
    @Column(name = "holiday_sample_freq")
    private Integer holidaySampleFreq;

    @NotBlank(message = "требуется ввести веротяность заполнения праздника")
    @Column(name = "holiday_fill_chance")
    private Integer holidayFillChance;

    @NotBlank(message = "требуется ввести веротяность смещения игрока с праздника")
    @Column(name = "holiday_push_chance")
    private Integer holidayPushChance;

    @NotBlank(message = "требуется ввести веротяность простого праздника")
    @Column(name = "holiday_simple_chance")
    private Integer holidaySimpleChance;

    @NotBlank(message = "требуется ввести веротяность пиршенства")
    @Column(name = "holiday_banquet_chance")
    private Integer holidayBanquetChance;

    @NotBlank(message = "требуется ввести веротяность ужина")
    @Column(name = "holiday_dinner_chance")
    private Integer holidayDinnerChance;


    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "session",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Calculate> calculateList;

    public SessionGame(Integer id) {
        this.id = id;
    }

    public SessionGame(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SessionGame session = (SessionGame) o;
        return id != null && Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}