package com.reksoft.holiday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private Integer sessionPoints;

    @Column(name = "shots")
    private Integer shots;

    @Transient
    private Boolean isBusy;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "player",
            cascade = CascadeType.ALL)
    private List<Member> memberList;

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
                  Integer shots,
                  Boolean isBusy) {
        this.name = name;
        this.sessionPoints = sessionPoints;
        this.shots = shots;
        this.isBusy = isBusy;
    }

}