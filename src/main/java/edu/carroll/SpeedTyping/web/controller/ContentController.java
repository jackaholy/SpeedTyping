package edu.carroll.SpeedTyping.web.controller;


import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.model.TypeTest;
import edu.carroll.SpeedTyping.service.ContentService;
import jakarta.servlet.http.HttpServletResponse;
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

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * Retrieves a typing content based on the provided difficulty level and prepares necessary data for the typing test.
     *
     * @param difficulty The difficulty level for selecting typing content.
     * @param model The model to which data will be added for rendering.
     * @return The name of the template to render for the typing content.
     */
    @RequestMapping(value = "/typing", method = RequestMethod.GET)
    public String typingContent(@RequestParam Level.LevelDifficulty difficulty, HttpServletResponse response, Model model) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache"); // Older browsers
        response.setHeader("Expires", "0"); // Makes sure it expires right away
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
        typeTest.setLevelId(selectedLevel.getId());
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
            testedLevel = contentService.findByLevelid(typeTest.getLevelId());
            score.setLevel(testedLevel);
            // Process our data to calculate date, accuracy, and time
            score.setDate(Calendar.getInstance().getTime());
            double time = typeTest.getTime(); // Currently assuming the passed in time is the time for typing in seconds // TODO: Add null checking
            // Calculate the number of correct words typed
            int correctWordsTyped = calculateCorrectWordsTyped(typeTest.getTypedContent(), testedLevel.getContent());
            double wordsPerMinute = (correctWordsTyped / time) * 60;
            score.setTime(wordsPerMinute);
            contentService.saveScore(score);
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
