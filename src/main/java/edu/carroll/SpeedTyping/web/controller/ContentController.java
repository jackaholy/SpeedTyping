package edu.carroll.SpeedTyping.web.controller;


import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.model.TypeTest;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import edu.carroll.SpeedTyping.service.ContentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Random;

import java.util.List;

/**
 * Description: Content controller will handle the requests for the typing page
 */
@Controller
public class ContentController {
    private static final Logger log = LoggerFactory.getLogger(ContentController.class);

    public final ContentService contentService;
    public final LevelRepository levelRepo;
    public final ScoreRepository scoreRepo;

    public ContentController(ContentService contentService, LevelRepository levelRepo, ScoreRepository scoreRepo) {
        this.contentService = contentService;
        this.levelRepo = levelRepo;
        this.scoreRepo = scoreRepo;
    }

    /**
     * Retrieves a typing content based on the provided difficulty level and prepares necessary data for the typing test.
     *
     * @param difficulty The difficulty level for selecting typing content.
     * @param model The model to which data will be added for rendering.
     * @return The name of the template to render for the typing content.
     */
    @RequestMapping(value = "/typing", method = RequestMethod.GET)
    public String typingContent(@RequestParam Level.LevelDifficulty difficulty, Model model) {
        // Get the levels for the level difficulty and pick a random level
        final Random random = new Random();
        List<Level> selectedLevels = contentService.getLevelsForLeveldifficulty(difficulty);
        final int randLevel = random.nextInt(selectedLevels.size());
        Level selectedLevel = selectedLevels.get(randLevel);
        model.addAttribute("chosenLevel", selectedLevel);
        // Generate log message when user selects level.
        log.info("Chosen typing test: {}", selectedLevel.getLevelDifficultyName());
        // Add this data to a TypeTest and add this test to the model.
        TypeTest typeTest = new TypeTest();
        typeTest.setCurrentLevel(selectedLevel.getLevelid());
        model.addAttribute("typeTest", typeTest);
        return "typing";
    }

    /**
     * Process and save the typing test data to the database. Specifically, a score will be stored in the score table.
     *
     * @param typeTest The object containing typing test data such as username, time, level, and typed content.
     * @param result The binding result that holds possible validation errors.
     * @param attrs Redirect attributes for passing data between request attributes and redirect attributes.
     * @return The name of the template to render after processing the data.
     */
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

    /**
     * Calculates the number of correct words typed by comparing the typed content with the level content.
     *
     * @param content The typed content provided by the user.
     * @param level The content of the typing level for comparison.
     * @return The count of correct words typed.
     */
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
}
