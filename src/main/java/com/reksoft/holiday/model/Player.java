package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "players")
@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "session_points")
    private Integer sessionPoints = 0;

    @Column(name = "std_shots")
    private Integer stdShots = 0;

    @Column(name = "bonus_shots")
    private Integer bonusShots = 0;

    @Transient
    private Integer shots = 0;

    @Transient
    private Boolean isBusy=false;

    @Transient
    private Boolean isOrganizer=false;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "player",
            cascade = CascadeType.ALL)
    private Set<Member> memberSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Player player = (Player) o;
        return id != null && Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }


    public Player(String name,
                  Integer sessionPoints,
                  Integer stdShots, Integer bonusShots, Integer shots,
                  Boolean isBusy,Boolean isOrganizer) {
        this.name = name;
        this.sessionPoints = sessionPoints;
        this.stdShots = stdShots;
        this.bonusShots = bonusShots;
        this.shots = shots;
        this.isBusy = isBusy;
        this.isOrganizer = isOrganizer;
    }
}