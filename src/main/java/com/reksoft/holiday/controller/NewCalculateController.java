package com.reksoft.holiday.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewCalculateController {
    @GetMapping ("/new_calculate")
    public String view() {
        return "New calculate page";
    }
}
