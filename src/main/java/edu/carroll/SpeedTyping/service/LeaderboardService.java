package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;

import java.util.List;

/**
 * Interface for retrieving leaderboard information.
 */
public interface LeaderboardService {
    /** Get scores for the chosen level */
    List<Score> getScoresForLevel(Level level);
    /** Get scores for the chosen level difficulty */
    List<Score> getScoresForLeveldifficulty(Integer leveldifficulty);
    /** Get n number of scores by level difficulty and sort by time. If n > scores.length, return scores.length levels */
    List<Score> getNScoresForDifficultySortByTime(Integer leveldifficulty, int n);
    /** Get all the levels */
    List<Level> getAllLevels();
}
