package com.reksoft.holiday.model;

import javax.persistence.*;

@Table(name = "holiday")
@Entity
public class Holiday {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "init_number", nullable = false)
    private Integer initNumber;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "min_capacity", nullable = false)
    private Integer minCapacity;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "points_rate", nullable = false)
    private Double pointsRate;

    public Double getPointsRate() {
        return pointsRate;
    }

    public void setPointsRate(Double pointsRate) {
        this.pointsRate = pointsRate;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getInitNumber() {
        return initNumber;
    }

    public void setInitNumber(Integer initNumber) {
        this.initNumber = initNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}