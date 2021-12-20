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
    @Autowired
    private ResourceService resourceService;

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
    public String getWelcome(Model model){
        model.addAttribute("welcome_title", resourceService.getBundle().getString("welcome.title"));
        model.addAttribute("welcome_text", resourceService.getBundle().getString("welcome.text"));
        model.addAttribute("welcome_button", resourceService.getBundle().getString("welcome.button"));
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
        model.addAttribute("session_title", resourceService.getBundle().getString("session.title"));
        model.addAttribute("sessionPlayers", resourceService.getBundle().getString("session.sessionPlayers"));
        model.addAttribute("sessionDuration", resourceService.getBundle().getString("session.sessionDuration"));
        model.addAttribute("playersAddshotChance", resourceService.getBundle().getString("session.playersAddshotChance"));
        model.addAttribute("playersAddshotMin", resourceService.getBundle().getString("session.playersAddshotMin"));
        model.addAttribute("playersAddshotMax", resourceService.getBundle().getString("session.playersAddshotMax"));
        model.addAttribute("playersNumberAddshot", resourceService.getBundle().getString("session.playersNumberAddshot"));
        model.addAttribute("holidaySampleFreq", resourceService.getBundle().getString("session.holidaySampleFreq"));
        model.addAttribute("holidayFillChance", resourceService.getBundle().getString("session.holidayFillChance"));
        model.addAttribute("holidayPushChance", resourceService.getBundle().getString("session.holidayPushChance"));
        model.addAttribute("holidaySimpleChance", resourceService.getBundle().getString("session.holidaySimpleChance"));
        model.addAttribute("holidayBanquetChance", resourceService.getBundle().getString("session.holidayBanquetChance"));
        model.addAttribute("holidayDinnerChance", resourceService.getBundle().getString("session.holidayDinnerChance"));
        model.addAttribute("session_button_save", resourceService.getBundle().getString("session.button.save"));
        model.addAttribute("session_button_begin", resourceService.getBundle().getString("session.button.begin"));
        model.addAttribute("button_back", resourceService.getBundle().getString("common.button.back"));
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

        model.addAttribute("statistic_title", resourceService.getBundle().getString("statistic.title"));
        model.addAttribute("statistic_button_new", resourceService.getBundle().getString("statistic.button.new"));
        model.addAttribute("statistic_table_parameters_title", resourceService.getBundle().getString("statistic.table.parameters.title"));
        model.addAttribute("statistic_table_parameters_user", resourceService.getBundle().getString("statistic.table.parameters.user"));
        model.addAttribute("statistic_table_parameters_begin", resourceService.getBundle().getString("statistic.table.parameters.begin"));
        model.addAttribute("statistic_table_parameters_end", resourceService.getBundle().getString("statistic.table.parameters.end"));
        model.addAttribute("statistic_table_parameters_duration", resourceService.getBundle().getString("statistic.table.parameters.duration"));
        model.addAttribute("statistic_table_parameters_players", resourceService.getBundle().getString("statistic.table.parameters.players"));
        model.addAttribute("statistic_table_parameters_holidays", resourceService.getBundle().getString("statistic.table.parameters.holidays"));
        model.addAttribute("statistic_table_parameters_uncomplete", resourceService.getBundle().getString("statistic.table.parameters.uncomplete"));
        model.addAttribute("statistic_table_parameters_points", resourceService.getBundle().getString("statistic.table.parameters.points"));
        model.addAttribute("statistic_list_players_title", resourceService.getBundle().getString("statistic.list.players.title"));
        model.addAttribute("statistic_list_players_name", resourceService.getBundle().getString("statistic.list.players.name"));
        model.addAttribute("statistic_list_players_points", resourceService.getBundle().getString("statistic.list.players.points"));
        model.addAttribute("statistic_list_players_shots", resourceService.getBundle().getString("statistic.list.players.shots"));
        model.addAttribute("statistic_list_param_title", resourceService.getBundle().getString("statistic.list.param.title"));
        model.addAttribute("statistic_list_param_AddshotChance", resourceService.getBundle().getString("statistic.list.param.AddshotChance"));
        model.addAttribute("statistic_list_param_AddshotMin", resourceService.getBundle().getString("statistic.list.param.AddshotMin"));
        model.addAttribute("statistic_list_param_AddshotMax", resourceService.getBundle().getString("statistic.list.param.AddshotMax"));
        model.addAttribute("statistic_list_param_NumberAddshot", resourceService.getBundle().getString("statistic.list.param.NumberAddshot"));
        model.addAttribute("statistic_list_param_SampleFreq", resourceService.getBundle().getString("statistic.list.param.SampleFreq"));
        model.addAttribute("statistic_list_param_FillChance", resourceService.getBundle().getString("statistic.list.param.FillChance"));
        model.addAttribute("statistic_list_param_PushChance", resourceService.getBundle().getString("statistic.list.param.PushChance"));
        model.addAttribute("statistic_list_param_SimpleChance", resourceService.getBundle().getString("statistic.list.param.SimpleChance"));
        model.addAttribute("statistic_list_param_BanquetChance", resourceService.getBundle().getString("statistic.list.param.BanquetChance"));
        model.addAttribute("statistic_list_param_DinnerChance", resourceService.getBundle().getString("statistic.list.param.DinnerChance"));
        model.addAttribute("statistic_table_holidays_title", resourceService.getBundle().getString("statistic.table.holidays.title"));
        model.addAttribute("statistic_table_holidays_begin", resourceService.getBundle().getString("statistic.table.holidays.begin"));
        model.addAttribute("statistic_table_holidays_end", resourceService.getBundle().getString("statistic.table.holidays.end"));
        model.addAttribute("statistic_table_holidays_type", resourceService.getBundle().getString("statistic.table.holidays.type"));
        model.addAttribute("statistic_table_holidays_cap", resourceService.getBundle().getString("statistic.table.holidays.cap"));
        model.addAttribute("statistic_table_holidays_points", resourceService.getBundle().getString("statistic.table.holidays.points"));
        model.addAttribute("statistic_table_holidays_members", resourceService.getBundle().getString("statistic.table.holidays.members"));
        model.addAttribute("statistic_list_members_title", resourceService.getBundle().getString("statistic.list.members.title"));
        model.addAttribute("statistic_list_members_name", resourceService.getBundle().getString("statistic.list.members.name"));
        model.addAttribute("statistic_list_members_in", resourceService.getBundle().getString("statistic.list.members.in"));
        model.addAttribute("statistic_list_members_out", resourceService.getBundle().getString("statistic.list.members.out"));
        model.addAttribute("statistic_list_members_time", resourceService.getBundle().getString("statistic.list.members.time"));
        model.addAttribute("statistic_list_members_points", resourceService.getBundle().getString("statistic.list.members.points"));
        model.addAttribute("statistic_list_members_org", resourceService.getBundle().getString("statistic.list.members.org"));
        model.addAttribute("statistic_isOrg_yes", resourceService.getBundle().getString("statistic.isOrg.yes"));
        model.addAttribute("statistic_isOrg_no", resourceService.getBundle().getString("statistic.isOrg.no"));
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
            model.addAttribute("sse_title", resourceService.getBundle().getString("sse.title"));
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
        model.addAttribute("sse_title", resourceService.getBundle().getString("sse.title"));
       return "sse";
}


}
