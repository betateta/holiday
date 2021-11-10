package com.reksoft.holiday.controller;

import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.mechanic.CalculateSession;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.SessionService;
import com.reksoft.holiday.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private SessionService sessionServiceImpl;
    @Autowired
    private CalculateSession calculateSession;

    private SessionGame session;
    private SessionParameters sessionParameters;
    private User user;
    private static final Logger log = Logger.getLogger(MainController.class);

    @GetMapping(value = "/")
    public String view_auth_user (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        user = (User) userServiceImpl.loadUserByUsername(authUserName);
        session = sessionServiceImpl.findByUser(user);
        if (session != null) {
            sessionParameters = sessionServiceImpl.getSessionParameters(session);
        } else {
            session = new SessionGame();
            sessionParameters = new SessionParameters();
            session.setUser(user);
        }
        model.addAttribute("id",user.getId());
        model.addAttribute("name",user.getUsername());
        model.addAttribute("roles", user.getRoles());
        if (sessionServiceImpl.findByUser(user) != null){
            model.addAttribute("session_id", session.getId());
        }
        return "index";
    }
    @GetMapping(value = "/userslist")
    public String view_users_list (Model model){
        List<User> allUsers = userServiceImpl.allUsers();
        model.addAttribute("users", allUsers);
        return "users";
    }

    @GetMapping(value = "/session")
    public String create_session (Model model){
        model.addAttribute("parameters", sessionParameters);
        session = sessionServiceImpl.setSessionParameters(session,sessionParameters);
        sessionServiceImpl.save(session);
        return "session";
    }
    @GetMapping(value = "/backtomain")
    public String returnToUser (){
        return "redirect:/";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("parameters") @Valid SessionParameters parameters,
                       BindingResult errors) throws ValidationException {
        if (errors.hasErrors()) {
            log.warn("Invalid session parameters: "+ errors.getFieldError());
            return "session";
        }
        try {
            sessionServiceImpl.validateParameters(parameters);
        } catch (ValidationException validationException) {return "session";}
        sessionParameters=parameters;
        session = sessionServiceImpl.setSessionParameters(session,sessionParameters);
        sessionServiceImpl.save(session);
        return "session";
    }
    @GetMapping(value = "start_session")
    public String startNewSession (Model model){
        calculateSession.setSessionGame(session);
        sessionServiceImpl.save(calculateSession.getSessionGame());
        return "start_session";
    }
    @GetMapping(value = "back")
    public String returnToSession (){
        return "redirect:/session";
    }

}
