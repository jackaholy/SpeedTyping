package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.service.LeaderboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Manages leaderboard-related operations.
 */
@Controller
public class LeaderboardController {
    private static final Logger log = LoggerFactory.getLogger(LeaderboardController.class);
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    /**
     * Retrieves the leaderboard data and adds it to the frontend model
     *
     * @param model the model to add the leaderboard data to
     * @return the name of the leaderboard view
     */
    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public String showLeaderboard(@RequestParam Level.LevelDifficulty difficulty, Model model) {
        List<Score> leaderboard = leaderboardService.getScoresForLeveldifficulty(difficulty);
        model.addAttribute("leaderboard", leaderboard);
        log.info("Switched to page: Leaderboard");
        return "leaderboard";
    }
}