package com.reksoft.holiday.controller;

import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.SessionServiceImpl;
import com.reksoft.holiday.service.UserService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private Configuration configuration;
    @Autowired
    private UserService userService;
    @Autowired
    private SessionServiceImpl sessionServiceImpl;

    private SessionGame session;
    private User user;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String view_auth_user (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        user = (User) userService.loadUserByUsername(authUserName);
        session = sessionServiceImpl.findByUser(user);
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getUsername());
        model.addAttribute("roles", user.getRoles());
        if (sessionServiceImpl.findByUser(user) != null){
            model.addAttribute("session_id", session.getId());
        }
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
        SessionGame session_parameters = new SessionGame(user);
        model.addAttribute("parameters", session_parameters);
        return "session";
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save( @ModelAttribute("parameters") SessionGame parameters) {
        parameters.setId(user.getId());
        parameters.setUser(user);
        sessionServiceImpl.save(parameters);
        return "session";
    }

}
