package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Table(name = "members")
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "calculate_id")
    private Calculate calculate;

    @Column(name = "input_time")
    private Instant inputTime;

    @Column(name = "output_time")
    private Instant outputTime;

    @Column(name = "duration")
    private long duration;

    @Column(name = "holiday_points")
    private Integer holidayPoints;

    @Column(name = "is_organizator")
    private Boolean isOrganizator;

    public Member(Player player, Calculate calculate) {
        this.player = player;
        this.calculate = calculate;
    }

    public Member(Player player, Calculate calculate, Boolean isOrganizator) {
        this.player = player;
        this.calculate = calculate;
        this.isOrganizator = isOrganizator;
    }

    public Member(Player player, Calculate calculate, Instant inputTime, Boolean isOrganizator) {
        this.player = player;
        this.calculate = calculate;
        this.inputTime = inputTime;
        this.isOrganizator = isOrganizator;
    }
}