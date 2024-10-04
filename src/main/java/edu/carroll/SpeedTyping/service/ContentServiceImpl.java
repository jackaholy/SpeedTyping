package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ContentServiceImpl class provides methods to retrieve content (level or levels) for a typing test.
 */
@Service
public class ContentServiceImpl implements ContentService {
    // LevelRepository to interact with the Level data from the database.
    public final LevelRepository levelRepository;

    // ScoreRepository to interact with the Score data from the database.
    public final ScoreRepository scoreRepository;

    /**
     * Constructor for the ContentServiceImpl class
     * Initialize repositories that will be used.
     *
     * @param levelRepository level repo to be used (should be handled by spring)
     * @param scoreRepository score repo to be used (should be handled by spring)
     *
     */
    public ContentServiceImpl(LevelRepository levelRepository, ScoreRepository scoreRepository) {
        this.levelRepository = levelRepository;
        this.scoreRepository = scoreRepository;
    }

    /**
     * Retrieves a list of Level objects based on the specified level difficulty.
     *
     * @param leveldifficulty The level difficulty to search for.
     * @return A list of Level objects matching the specified level difficulty.
     */
    public List<Level> getLevelsForLeveldifficulty(Integer leveldifficulty) {
        return levelRepository.findByLeveldifficulty(leveldifficulty);
    }

}
