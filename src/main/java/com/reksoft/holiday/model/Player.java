package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    @Column(name = "player_points")
    private Integer playerPoints;

    @Column(name = "std_shots")
    private Integer stdShots;

    @Column(name = "bonus_shots")
    private Integer bonusShots;

    @Column(name = "is_organizator")
    private Boolean isOrganizator;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="players_calculates",
            joinColumns=@JoinColumn (name="player_id"),
            inverseJoinColumns=@JoinColumn(name="calculate_id"))
    private Set<Calculate> calculates;

    @ManyToOne (fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn (name="sponsored_holiday")
    @ToString.Exclude
    private Calculate sponsoredHoliday;

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

    public Player(String name, Integer playerPoints, Integer stdShots, Integer bonusShots, Boolean isOrganizator) {
        this.name = name;
        this.playerPoints = playerPoints;
        this.stdShots = stdShots;
        this.bonusShots = bonusShots;
        this.isOrganizator = isOrganizator;
    }
}