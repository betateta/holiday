package com.reksoft.holiday.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "role")
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "rolename", nullable = false)
    private String rolename;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="users_roles",
            joinColumns=@JoinColumn (name="role_id"),
            inverseJoinColumns=@JoinColumn(name="user_id"))
    private Set<User> users;

    public Role() {

    }

    public Role(Integer id) {
        this.id = id;
    }

    public Role(Integer id, String rolename) {
        this.id = id;
        this.rolename = rolename;
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return id != null && Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}