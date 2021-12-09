package com.reksoft.holiday.controller;

import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

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
}
