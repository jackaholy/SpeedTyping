package edu.carroll.SpeedTyping.web.controller;


import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String typingContent(Model model) {
        final Random random = new Random();
        List<Level> selectedLevels = contentService.getLevelsForLevel_difficulty(1);
        final int randLevel = random.nextInt(10);
        Level selectedLevel = selectedLevels.get(randLevel);
        model.addAttribute("chosenLevel", selectedLevel);
        return "typing";
    }

}
