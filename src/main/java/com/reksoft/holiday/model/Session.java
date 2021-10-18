package com.reksoft.holiday.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Table(name = "session")
@Entity
public class Session {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "stop_time")
    private Instant stopTime;

    @Column(name = "points")
    private Integer points;

    @Column(name = "number_of_holidays")
    private Integer numberOfHolidays;

    @OneToOne(optional=false, mappedBy="session")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "session",
            cascade = CascadeType.ALL)
    private List<Calculate> calculateList;

    public List<Calculate> getCalculateList() {
        return calculateList;
    }

    public void setCalculateList(List<Calculate> calculateList) {
        this.calculateList = calculateList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumberOfHolidays() {
        return numberOfHolidays;
    }

    public void setNumberOfHolidays(Integer numberOfHolidays) {
        this.numberOfHolidays = numberOfHolidays;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}