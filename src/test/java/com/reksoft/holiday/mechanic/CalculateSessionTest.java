package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.SessionService;
import com.reksoft.holiday.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculateSessionTest {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CalculateSession calculateSession;

    @Test
    public void testCreateCalculate (){

        User user = (User) userService.loadUserByUsername("user");
        calculateSession.setSessionGame(sessionService.findByUser(user));
        System.out.println(calculateSession.getSessionGame());
    }
}
