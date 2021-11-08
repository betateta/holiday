package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Role;
import com.reksoft.holiday.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User getUser(Integer id);

    Integer getUserId(String name);

    Set<Role> getUserRoles(Integer id);

    List<User> allUsers();

    User findUserById(Integer userId);

    boolean saveUser(User user);

    boolean updateUser(User user);
}
