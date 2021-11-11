package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(optional=false,  cascade=CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "stop_time")
    private Instant stopTime;

    @Column(name = "session_points")
    private Integer points;

    @Column(name = "number_of_holidays")
    private Integer numberOfHolidays;

    @Column(name = "number_of_incomplete_holidays")
    private Integer numberOfIncompleteHolidays;

    @Column(name = "session_players")
    private Integer sessionPlayers;

    @Column(name = "session_duration")
    private int sessionDuration;

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

    @OneToMany(fetch = FetchType.EAGER,
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