package com.reksoft.holiday.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Table(name = "calculate")
@Entity
public class Calculate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "holiday_id", nullable = false)
    private Integer holidayId;
/*
    @Column(name = "session_id")
    private Integer sessionId;

 */

    @ManyToOne(fetch=FetchType.LAZY,
            cascade=CascadeType.ALL)
    @JoinColumn (name="session_id")
    private Session session;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "stop_time")
    private Instant stopTime;

    @Column(name = "uniq_players_number")
    private Integer uniqPlayersNumber;

    @Column(name = "points")
    private Integer points;

    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="sponsor_1_id")
    private User sponsor_1;

    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="sponsor_2_id")
    private User sponsor_2;

    public User getSponsor_1() {
        return sponsor_1;
    }

    public void setSponsor_1(User sponsor_1) {
        this.sponsor_1 = sponsor_1;
    }

    public User getSponsor_2() {
        return sponsor_2;
    }

    public void setSponsor_2(User sponsor_2) {
        this.sponsor_2 = sponsor_2;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getUniqPlayersNumber() {
        return uniqPlayersNumber;
    }

    public void setUniqPlayersNumber(Integer uniqPlayersNumber) {
        this.uniqPlayersNumber = uniqPlayersNumber;
    }

    public Instant getStopTime() {
        return stopTime;
    }

    public void setStopTime(Instant stopTime) {
        this.stopTime = stopTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Integer getHolidayId() {
        return holidayId;
    }

    public void setHolidayId(Integer holidayId) {
        this.holidayId = holidayId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}