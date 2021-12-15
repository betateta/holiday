package com.reksoft.holiday.controller;

import com.reksoft.holiday.dto.SessionGameMapper;
import com.reksoft.holiday.dto.SessionParameters;
import com.reksoft.holiday.exception.ValidationException;
import com.reksoft.holiday.mechanic.CalculateSession;
import com.reksoft.holiday.mechanic.ProgressBar;
import com.reksoft.holiday.model.Calculate;
import com.reksoft.holiday.model.SessionGame;
import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private UserService userServiceImpl;
    @Autowired
    private SessionService sessionServiceImpl;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CalculateSession calculateSession;
    @Autowired
    private SessionGameMapper sessionGameMapper;
    @Autowired
    private CalculateService calculateService;
    @Autowired
    private SseService sseService;
    @Autowired
    private ProgressBar progressBar;
    @Autowired
    private KeycloakUserAccess keycloakUserAccess;
    @Autowired
    private RoleService roleService;

    private SessionGame session;
    private SessionParameters sessionParameters;
    private User user;
    private static final Logger log = LogManager.getLogger(MainController.class);


    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request){
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return "welcome";
    }

    @GetMapping(path = "/")
    public String getWelcome(){
        return "welcome";
    }

    @GetMapping(path = "/begin")
    public String begin(Principal principal){
        Set<String> keycloakUserRoles = keycloakUserAccess.getUserRoles(principal.getName())
                .stream().filter(s -> (s.equals("USER")||s.equals("ADMIN")))
                .collect(Collectors.toSet());
        System.out.println("keycloakUserRoles:"+keycloakUserRoles);
        String role= keycloakUserRoles.stream().findFirst().get();
        System.out.println("role:"+role);
        switch (role){
            case "USER":{
                return "redirect:/user";
            }
            case "ADMIN":{
                return "redirect:/admin";
            }
            default:{
                return "welcome";

            }
        }
    }

    @GetMapping(value = "/user")
    public String view_auth_user (Principal principal,Model model, HttpServletResponse response,HttpSession httpSession ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authUserName = auth.getName();
        System.out.println("keycloak response status:"+response.getStatus());
        System.out.println("authUserName:"+authUserName );
        System.out.println("http session:"+httpSession );

        String keycloakUserName= keycloakUserAccess.getUsername(authUserName);;
        System.out.println("keycloakUserName:"+keycloakUserName);

        try {
            user = (User) userServiceImpl.loadUserByUsername(keycloakUserName);
        } catch (UsernameNotFoundException usernameNotFoundException) {
            user = new User();
            user.setUsername(keycloakUserName);
            userServiceImpl.saveAndFlushUser(user);
        }

        List<SessionGame> sessionGameList = new ArrayList<>();

        /*get list of all sessions, and the last session for current user*/
        if(user != null) {
            try {
                session = sessionServiceImpl.findLast(user);
                sessionGameList = sessionServiceImpl.findByUser(user);
            } catch (NoSuchElementException ex){
                System.out.println("Прошлая сессия пользователя не найдейна");
                log.info("Прошлая сессия пользователя не найдейна");
            }
        }
        /* if present, get parameters from last session*/
        if (session != null) {
            sessionParameters = sessionGameMapper.sessionToParameters(session);
            /*
                else, create new parameters with default values and setting current user
            */
        } else {
            sessionParameters = new SessionParameters();
        }
        sessionParameters.setUser(user);
        session = new SessionGame();
        session = sessionGameMapper.parametersToSession(sessionParameters);

        model.addAttribute("name",user.getUsername());
        model.addAttribute("sessions", sessionGameList);

        return "index";
    }


    @GetMapping(value = "/user/session")
    public String create_session (Model model){
        model.addAttribute("parameters", sessionParameters);
        session = sessionGameMapper.parametersToSession(sessionParameters);
        return "session";
    }



    @PostMapping(value = "/user/save")
    public String save(@ModelAttribute("parameters") @Valid SessionParameters parameters,
                       BindingResult errors) throws ValidationException {
        if (errors.hasErrors()) {
            log.warn("Invalid session parameters: "+ errors.getFieldError());
            return "session";
        }
        try {
            sessionServiceImpl.validateParameters(parameters);
        } catch (ValidationException validationException) {
            errors.rejectValue("playersNumberAddshot","errors.SessionParameters",
                    "Значение поля должно быть меньше или равно количеству игроков");
            return "session";
        }
        parameters.setUser(user);
        sessionParameters = parameters;
        session = sessionGameMapper.parametersToSession(sessionParameters);
       // sessionServiceImpl.saveAndFlush(session);
        return "session";
    }



    @GetMapping(value = "/user/get_statistic")
    public String getStatistic (Model model){
        log.debug("get mapping:statistic page");
        SessionGame currentSession = sessionServiceImpl.findLast(user);
        /*
        TODO: После чтения связанного списка удаляются дубликаты. Причина появления оных - не выяснена
        */
        Set<Calculate> calculateSet = currentSession.getCalculateSet();

        model.addAttribute("user",currentSession.getUser());
        model.addAttribute("session",currentSession);
        model.addAttribute("parameters",
                sessionGameMapper.sessionToParameters(currentSession));
        model.addAttribute("calculates",calculateSet);
        model.addAttribute("players",playerService.getAll());

        return "statistic";
    }

    @GetMapping(value = "/user/start_session")
    public String startCalc (Model model){
        if(sessionParameters!=null) {
           calculateSession.buildSessionGame(session);
            ExecutorService executor = Executors
                    .newSingleThreadExecutor();
           executor.submit(calculateSession);
           executor.shutdown();
           return "sse";
        }
        else {
            return "redirect:/user/session";
        }
    }

    @GetMapping("/user/test_sse")
    public String testSse (Model model){
      calculateSession.buildSessionGame(session);
        ExecutorService executor = Executors
                .newSingleThreadExecutor();
        Future<Integer> future = executor.submit(calculateSession);
        executor.shutdown();

       return "sse";
}


}
