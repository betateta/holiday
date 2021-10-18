package com.reksoft.holiday.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

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

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRolename();
    }
}