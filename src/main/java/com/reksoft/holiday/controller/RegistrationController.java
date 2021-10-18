package com.reksoft.holiday.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    @GetMapping ("/registration")
    public String view(){
        return "GET registation page";
    }
    @PostMapping ("/registration")
    public String view_post(){
        return "POST registation page";
    }
}
