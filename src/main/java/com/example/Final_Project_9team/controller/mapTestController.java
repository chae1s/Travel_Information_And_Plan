package com.example.Final_Project_9team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mapTestController {
    @GetMapping("/")
    public String mapTest() {
        return "home";
    }
}
