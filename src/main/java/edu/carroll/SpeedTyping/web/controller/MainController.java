package edu.carroll.SpeedTyping.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Maps the root URL to the home page.
    @GetMapping("/")
    public String root() {
        return "home";
    }

    // Maps to the home page.
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    // Maps to the leaderboard page.
    @GetMapping("/leaderboard")
    public String leaderboard() {
        return "leaderboard";
    }
}