package com.reksoft.holiday.service;

import com.reksoft.holiday.model.Role;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return user;
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.<User>getById(id);
    }

    @Override
    public Integer getUserId(String name) {
        return userRepository.findByUsername(name).getId();
    }
    @Override
    public Set<Role> getUserRoles(Integer id) {
        return userRepository.<User>getById(id).getRoles();
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Integer userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(NoOpPasswordEncoder.getInstance().encode(user.getPassword()));
        user.setEnable(true);
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB == null) {
            return false;
        }
        userRepository.saveAndFlush(user);
        return true;
    }



}
