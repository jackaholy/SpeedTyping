package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.SequencedCollection;

/**
 * ContentService interface provides methods to retrieve content (level or levels) for a typing test.
 */
public interface ContentService {
    /**
     * Return the levels for the given level difficulty.
     *
     * @param leveldifficulty EASY, MEDIUM, or HARD. As of writing this, there are 10 of each of these levels.
     * @return The levels that match the given level difficulty, returns empty list if null is passed in or if no levels are found.
     */
    List<Level> getLevelsForLeveldifficulty(Level.LevelDifficulty leveldifficulty);

    /**
     * Find a level using level id
     *
     * @param currentLevel is the level id that will be searched for
     * @return The level that matches the given level id, return null if none are found
     */
    Level findByLevelid(Integer currentLevel);

    /**
     * Save a score to the repository. This method checks for valid input.
     *
     * @param score is the score to be saved.
     */
    boolean saveScore(Score score);

    /**
     *  Save a level to the repository. This method checks for valid input
     *
     * @param level is the level to be saved.
     */
    boolean saveLevel(Level level);
}
