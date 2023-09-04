package com.example.Final_Project_9team.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController implements ErrorController {

    @GetMapping("/error")
    public String redirectRoot() {
        return "forward:/index.html";
    }

    public String getErrorPath() {
        return null;
    }
}
