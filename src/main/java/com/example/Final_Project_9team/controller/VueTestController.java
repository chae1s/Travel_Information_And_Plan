package com.example.Final_Project_9team.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VueTestController {

    @GetMapping("home")
    public String home() {
        return "home";
    }
}
