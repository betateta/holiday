package com.reksoft.holiday.controller;

import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.UserService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private Configuration configuration;
    @Autowired
    private UserService userService;

    private User user;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String view_auth_user (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        user = (User) userService.loadUserByUsername(authUserName);
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getUsername());
        model.addAttribute("points",user.getPoints());
        model.addAttribute("roles", user.getRoles());
        return "index";
    }
    @RequestMapping(value = "/userslist", method = RequestMethod.GET)
    public String view_users_list (Model model){
        List<User> allUsers = userService.allUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String new_session (Model model){


        return "session";
    }
}
