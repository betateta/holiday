package com.reksoft.holiday.mechanic;

import com.reksoft.holiday.model.User;
import com.reksoft.holiday.service.SessionServiceImpl;
import com.reksoft.holiday.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalculateSessionTest {
    @Autowired
    private SessionServiceImpl sessionServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private CalculateSession calculateSession;

    @Test
    public void testCreateCalculate (){

        User user = (User) userServiceImpl.loadUserByUsername("user");
        calculateSession.setSessionGame(sessionServiceImpl.findByUser(user));
        System.out.println(calculateSession.getSessionGame());
    }
}
