package com.reksoft.holiday.controller;

import com.reksoft.holiday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping (path = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUserName(Integer id){
    id = 1;
    System.out.println(id);
        return this.userService.getUser(id).toString();
    }
}
