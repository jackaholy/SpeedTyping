package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.model.Test;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import edu.carroll.SpeedTyping.service.LeaderboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

import java.util.Calendar;
import java.util.List;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScoreRepository scoreRepo;

    private final LevelRepository levelRepo;

    private final LeaderboardService leaderboardService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("score", new Score());
        List<Score> leaderboard = leaderboardService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        log.info("Switched to page: Home");
        return "home";
    }

    public MainController(ScoreRepository scoreRepo, LevelRepository levelRepo, LeaderboardService leaderboardService) {
        this.scoreRepo = scoreRepo;
        this.levelRepo = levelRepo;
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/addData")
    public String addDataPost(@Valid @ModelAttribute Test test, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "leaderboard";
        }
        try {
            // Add data from the test into a score object, then save to the database
            Score score = new Score();
            score.setUsername(test.getUsername());
            score.setLevel(levelRepo.findByLevelId(test.getCurrentLevel()).getFirst());
            // Figure out the current date and set it in the score
            score.setDate(Calendar.getInstance().getTime());
            score.setTime(1000.0); // TODO: Everyone gets the same time until we figure out timing
            scoreRepo.save(score);
            log.info("Saved score: {}", score);
        } catch (Exception ex) {
            result.addError(new ObjectError("globalError", "Failed to save data into database."));
            log.error("Failed to save score object: {}", test, ex);
        }
        return "leaderboard";
    }

    // Maps to the home page.
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("score", new Score());
        List<Score> leaderboard = leaderboardService.getLeaderboard();
        model.addAttribute("leaderboard", leaderboard);
        log.info("Switched to page: Home");
        return "home";
    }

}