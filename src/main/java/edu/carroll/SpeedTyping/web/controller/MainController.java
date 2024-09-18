package edu.carroll.SpeedTyping.web.controller;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private ScoreRepository scoreRepo;

    @Autowired
    private LevelRepository levelRepo;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("home");
        model.addAttribute("score", new Score());
        return "home";
    }

    @PostMapping("/addData")
    public String addDataPost(@Valid @ModelAttribute Score score, BindingResult result, RedirectAttributes attrs) {
        Level level = levelRepo.findByLevelNameIgnoreCase("level1").getFirst();

        score.setLevel(level);
        if (result.hasErrors()) {
            return "home";
        }
        try {
            scoreRepo.save(score);
            log.info("Saved score: {}", score);
        } catch (Exception ex) {
            result.addError(new ObjectError("globalError", "Failed to save data into database."));
            log.error("Failed to save score object: {}", score, ex);
        }
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