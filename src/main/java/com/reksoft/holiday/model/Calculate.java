package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "calculates")
@Entity
public class Calculate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    //@OneToOne(cascade=CascadeType.ALL)
    @OneToOne
    @JoinColumn(name="holiday_id")
    private Holiday holiday;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn (name="session_id")
    @ToString.Exclude
    private SessionGame session;

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

    @Column(name = "is_correct_stop")
    private boolean isCorrectStop;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "calculate",
            cascade = CascadeType.ALL)
    private Set<Member> memberSet;

    @Transient
    private Integer numberOfPlayers = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Calculate calculate = (Calculate) o;
        return id != null && Objects.equals(id, calculate.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}