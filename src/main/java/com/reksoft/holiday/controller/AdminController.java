package com.reksoft.holiday.controller;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.SessionService;
import com.reksoft.holiday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;

    @GetMapping(path = "/admin")
    public String getAdmin(Model model){
        return "admin";
    }

    @GetMapping(value = "/admin/userslist")
    public String view_users_list (Model model){
        List<User> allUsers = userService.allUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping(path = "/admin/delete{userName}")
    public String deleteUser(@PathVariable("userName") String userName, Model model){
        User user = (User) userService.loadUserByUsername(userName);
        Set<SessionGame> sessionGameSet = new HashSet<>(sessionService.findByUser(user));
        for (SessionGame item: sessionGameSet
             ) {
            sessionService.delete(item);
        }
        userService.deleteByUserName(userName);
        return "redirect:/admin/userslist";
    }
}
