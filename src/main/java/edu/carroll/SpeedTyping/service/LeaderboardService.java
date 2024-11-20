package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;

import java.util.List;

/**
 * Interface for retrieving leaderboard information.
 */
public interface LeaderboardService {

    /**
     * Get scores for the chosen level difficulty. As of writing this, there are 10 of each level difficulty. Null input will return an empty list.
     *
     * @param leveldifficulty The difficulty of the levels we are searching for (EASY, MEDIUM, HARD).
     * @return A list of scores that match the input level difficulty.
     */
    List<Score> getScoresForLeveldifficulty(Level.LevelDifficulty leveldifficulty);

    /**
     * Get n number of scores by level difficulty and sort by wpm. If n > scores.length, return scores.length levels. If level difficulty is null, return an empty list.
     *
     * @param leveldifficulty the difficulty of level we are searching for.
     * @param n the number of levels we would like to retrieve.
     * @return n number of levels of the given level difficulty sorted by words per minute. If the database contains less than n levels, return that many. Return empty list if level difficulty is null.
     */
    List<Score> getNScoresForDifficultySortByWpm(Level.LevelDifficulty leveldifficulty, int n);
}
