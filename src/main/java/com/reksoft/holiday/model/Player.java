package com.reksoft.holiday.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "players")
@Entity
public class Player {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "name")
    private String name;

    @Column(name = "player_points")
    private Integer playerPoints;

    @Column(name = "is_organizator")
    private Boolean isOrganizator;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="players_calculates",
            joinColumns=@JoinColumn (name="player_id"),
            inverseJoinColumns=@JoinColumn(name="calculate_id"))
    private Set<Calculate> calculates;

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
}