package edu.carroll.SpeedTyping.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/")
    public String index() {
        return "Jack and Andrews Speed Typing Game!";
    }
}