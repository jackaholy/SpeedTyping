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
    /** Get a list of levels for the specified level difficulty */
    List<Level> getLevelsForLeveldifficulty(Level.LevelDifficulty leveldifficulty);
    /** Grab levels matching the given level id. Returns null if none is found **/
    Level findByLevelid(Integer currentLevel);
    /** Save a score to the repository. Check for valid input **/
    void saveScore(Score score);
    /** Save a level to the repository. Check for valid input **/
    void saveLevel(Level level);
}
