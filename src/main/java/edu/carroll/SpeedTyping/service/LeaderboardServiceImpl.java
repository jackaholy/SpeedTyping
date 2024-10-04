package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import edu.carroll.SpeedTyping.web.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that implements the LeaderboardService interface and retrieves leaderboard information.
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScoreRepository scoreRepository;

    private final LevelRepository levelRepository;

    /**
     * Constructs a LeaderboardServiceImpl with the specified ScoreRepository and LevelRepository.
     *
     * @param scoreRepository The ScoreRepository used to retrieve score data.
     * @param levelRepository The LevelRepository used to retrieve level data.
     */
    public LeaderboardServiceImpl(ScoreRepository scoreRepository, LevelRepository levelRepository) {
        this.scoreRepository = scoreRepository;
        this.levelRepository = levelRepository;
    }

    /**
     * Retrieves a list of scores for a specific level.
     *
     * @param level The level for which scores are to be retrieved.
     * @return List of Score objects representing the scores for the specified level, ordered by time descending.
     */
    public List<Score> getScoresForLevel(Level level) {
        return scoreRepository.findAllByLevelOrderByTimeDesc(level);
    }

    /**
     * Retrieves the scores for a specific level difficulty.
     *
     * @param leveldifficulty The difficulty level for which scores are to be retrieved.
     * @return List of Score objects representing the scores for the specified difficulty level.
     */
    @Override
    public List<Score> getScoresForLeveldifficulty(Integer leveldifficulty) {
        log.info("getScoresForLeveldifficulty: Retrieving score for level {}", leveldifficulty);
        // first, get the levels at the specified difficulty
        List<Level> diffLevels = levelRepository.findByLeveldifficulty(leveldifficulty);
        List<Score> scores = new ArrayList<>();
        // now, extract the scores from the retrieved levels.
        for (Level level : diffLevels) {
            scores.addAll(getScoresForLevel(level));
        }
        return scores;
    }

    /**
     * Retrieves a list of 'n' number of scores for a specific level difficulty sorted by time. Return all scores if less than n.
     *
     * @param leveldifficulty The difficulty level for which scores are to be retrieved.
     * @param n The number of scores to retrieve.
     * @return List of Score objects representing the scores for the specified difficulty level, sorted by time in descending order. If the number of scores is greater than n, only
     *  the first n scores will be returned.
     */
    @Override
    public List<Score> getNScoresForDifficultySortByTime(Integer leveldifficulty, int n) {
        List<Score> diffLevels = getScoresForLeveldifficulty(leveldifficulty);
        diffLevels.sort(Comparator.comparing(Score::getTime).reversed());
        // Extract the n first elements
        return diffLevels.subList(0, Math.min(n, diffLevels.size()));
    }

    /**
     * Retrieves all levels from the database.
     *
     * @return List of Level objects representing all levels stored in the database.
     */
    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    /**
     * Retrieves the leaderboard by combining scores from all levels.
     *
     * @return List of Score objects representing the combined leaderboard with scores from all levels.
     */
    public List<Score> getLeaderboard() {
        return getAllLevels().stream()
                .flatMap(level -> getScoresForLevel(level).stream())
                .collect(Collectors.toList());
    }
}
