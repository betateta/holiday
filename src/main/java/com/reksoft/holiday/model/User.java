package com.reksoft.holiday.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enable", nullable = false)
    private Boolean enable = false;

    @Column(name = "points", nullable = true)
    private Integer points = 0;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable (name="users_roles",
            joinColumns=@JoinColumn (name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles;

    @OneToOne (optional=true, cascade=CascadeType.ALL)
    @JoinColumn (name="input_parameters_id")
    private InputParameter inputParameters;

    @OneToOne (optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="session_id")
    private Session session;

    @OneToOne(optional=false, mappedBy="sponsor_1")
    private Calculate calc1;

    @OneToOne(optional=false, mappedBy="sponsor_2")
    private Calculate calc2;

    public InputParameter getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(InputParameter inputParameters) {
        this.inputParameters = inputParameters;
    }

    public Calculate getCalc1() {
        return calc1;
    }

    public void setCalc1(Calculate calc1) {
        this.calc1 = calc1;
    }

    public Calculate getCalc2() {
        return calc2;
    }

    public void setCalc2(Calculate calc2) {
        this.calc2 = calc2;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnable();
    }
}