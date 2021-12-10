package com.reksoft.holiday.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        HttpStatus httpStatus;
        if (status instanceof HttpStatus) {
            model.addAttribute("statusCode",(HttpStatus) status);
        }
        else {
            model.addAttribute("statusCode",HttpStatus.valueOf((Integer) status));
        }
        return "error_code";

    }
}
