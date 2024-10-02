package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.service.LeaderboardService;
import edu.carroll.SpeedTyping.service.LeaderboardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class LeaderboardController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public String showLeaderboard(Model model) {
        List<Score> leaderboard = leaderboardService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        log.info("Switched to page: Leaderboard");
        return "leaderboard";
    }
}