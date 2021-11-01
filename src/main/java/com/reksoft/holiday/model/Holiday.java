package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Table(name = "holiday")
@Entity
public class Holiday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

  //  @Lob
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

    @OneToOne(optional = false, mappedBy = "holiday")
    private Calculate calculate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Holiday holiday = (Holiday) o;
        return id != null && Objects.equals(id, holiday.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}