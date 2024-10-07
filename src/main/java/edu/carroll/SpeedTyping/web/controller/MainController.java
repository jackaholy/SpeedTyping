package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.model.TypeTest;
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

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScoreRepository scoreRepo;

    private final LevelRepository levelRepo;

    private final LeaderboardService leaderboardService;

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

    public MainController(ScoreRepository scoreRepo, LevelRepository levelRepo, LeaderboardService leaderboardService) {
        this.scoreRepo = scoreRepo;
        this.levelRepo = levelRepo;
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/addData")
    public String addDataPost(@Valid @ModelAttribute TypeTest typeTest, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "leaderboard";
        }
        Level testedLevel = null;
        try {
            // Add data from the test into a score object, then save to the database
            Score score = new Score();
            score.setUsername(typeTest.getUsername());
            testedLevel = levelRepo.findByLevelid(typeTest.getCurrentLevel()).getFirst();
            score.setLevel(testedLevel);
            // Process our data to calculate date, accuracy, and time
            score.setDate(Calendar.getInstance().getTime());
            double time = typeTest.getTime(); // Currently assuming the passed in time is the time for typing in seconds
            // Calculate the number of correct words typed
            int correctWordsTyped = calculateCorrectWordsTyped(typeTest.getTypedContent(), testedLevel.getContent());
            double wordsPerMinute = (correctWordsTyped / time) * 60;
            score.setTime(wordsPerMinute);
            scoreRepo.save(score);
            log.info("Saved score: {}", score);
        } catch (Exception ex) {
            result.addError(new ObjectError("globalError", "Failed to save data into database."));
            log.error("Failed to save score object: {}", typeTest, ex);
        }
        if (testedLevel != null) {
            return "redirect:/leaderboard?difficulty=" + testedLevel.getLeveldifficulty();
        } else {
            return "leaderboard";
        }
    }

    public int calculateCorrectWordsTyped(String content, String level) {
        int correctWordsTyped = 0;
        String[] typedWords = content.split(" ");
        String[] levelWords = level.split(" ");
        for (int i = 0; i < typedWords.length; i++) {
            if (i < levelWords.length && typedWords[i].equals(levelWords[i])) {
                correctWordsTyped++;
            }
        }
        return correctWordsTyped;
    }

    // Maps to the home page.
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