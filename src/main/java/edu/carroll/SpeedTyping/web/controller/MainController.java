package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.service.LeaderboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * MainController class is responsible for handling requests related to the main functionality of the application.
 */
@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final LeaderboardService leaderboardService;

    /**
     * Retrieves top 5 scores for easy, medium, and hard leaderboards and adds them to the model.
     *
     * @param model the model to which leaderboard information is added
     * @return the view name for the home page
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("score", new Score());
        // Add easy, medium, and hard leaderboards to the model with the top 5 scores
        model.addAttribute("easyLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(1, 5));
        model.addAttribute("mediumLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(2, 5));
        model.addAttribute("hardLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(3, 5));
        log.info("Switched to page: Home");
        return "home";
    }

    public MainController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    // Maps to the home page. Same code as above, there's probably a smarter way to do this that avoids the repeat code.
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("score", new Score());
        // Add easy, medium, and hard leaderboards to the model with the top 5 scores
        model.addAttribute("easyLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(1, 5));
        model.addAttribute("mediumLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(2, 5));
        model.addAttribute("hardLeaderboard", leaderboardService.getNScoresForDifficultySortByTime(3, 5));
        log.info("Switched to page: Home");
        return "home";
    }
}