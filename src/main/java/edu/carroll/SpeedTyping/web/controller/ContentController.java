package edu.carroll.SpeedTyping.web.controller;


import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.model.Test;
import edu.carroll.SpeedTyping.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

import java.util.List;

/**
 * Description: Content controller will handle the requests for content from the typing page.
 */
@Controller
public class ContentController {
    public final ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @RequestMapping(value = "/typing", method = RequestMethod.GET)
    public String typingContent(@RequestParam Integer difficulty, Model model) {
        final Random random = new Random();
        List<Level> selectedLevels = contentService.getLevelsForLeveldifficulty(difficulty);
        final int randLevel = random.nextInt(10);
        Level selectedLevel = selectedLevels.get(randLevel);
        model.addAttribute("chosenLevel", selectedLevel);
        Test test = new Test();
        test.setCurrentLevel(selectedLevel.getLevelid());
        model.addAttribute("test", test);
        return "typing";
    }
}
