package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;

import java.util.List;


public interface LeaderboardService {
    List<Score> getScoresForLevel(Level level);
    List<Score> getScoresForLeveldifficulty(Integer leveldifficulty);
    // get scores by level difficulty and sort by time
    List<Score> getNScoresForDifficultySortByTime(Integer leveldifficulty, int n);
    List<Level> getAllLevels();
    List<Score> getLeaderboard();
}
