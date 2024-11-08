package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Service class that implements the LeaderboardService interface and retrieves leaderboard information.
 */
@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    private static final Logger log = LoggerFactory.getLogger(LeaderboardServiceImpl.class);

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
     * @return List of Score objects representing the scores for the specified level, ordered by wpm descending.
     */
    public List<Score> getScoresForLevel(Level level) {
        return scoreRepository.findAllByLevelOrderByWpmDesc(level);
    }

    /**
     * Retrieves the scores for a specific level difficulty.
     *
     * @param leveldifficulty The difficulty level for which scores are to be retrieved.
     * @return List of Score objects representing the scores for the specified difficulty level.
     */
    @Override
    public List<Score> getScoresForLeveldifficulty(Level.LevelDifficulty leveldifficulty) {
        log.info("getScoresForLeveldifficulty: Retrieving score(s) for level difficulty: {}", leveldifficulty);
        // first, get the levels at the specified difficulty
        if (leveldifficulty == null) {
            return new LinkedList<>();
        }
        List<Level> diffLevels = levelRepository.findByLeveldifficulty(leveldifficulty);
        List<Score> scores = new ArrayList<>();
        // now, extract the scores from the retrieved levels.
        for (Level level : diffLevels) {
            scores.addAll(getScoresForLevel(level));
        }
        return scores;
    }

    /**
     * Retrieves a list of 'n' number of scores for a specific level difficulty sorted by wpm. Return all scores if less than n.
     *
     * @param leveldifficulty The difficulty level for which scores are to be retrieved.
     * @param n The number of scores to retrieve.
     * @return List of Score objects representing the scores for the specified difficulty level, sorted by wpm in descending order. If the number of scores is greater than n, only
     *  the first n scores will be returned.
     */
    @Override
    public List<Score> getNScoresForDifficultySortByWpm(Level.LevelDifficulty leveldifficulty, int n) {
        log.info("getNScoresForDifficultySortByWpm: Retrieving {} scores for difficulty {}", n, leveldifficulty);
        if (leveldifficulty == null) {
            return new LinkedList<>();
        }
        if (n < 0) {
            throw new IllegalArgumentException("n must be greater than or equal to 0");
        }
        List<Score> diffLevels = getScoresForLeveldifficulty(leveldifficulty);
        diffLevels.sort(Comparator.comparing(Score::getWpm).reversed());
        // Extract the n first elements
        List<Score> subList = diffLevels.subList(0, Math.min(n, diffLevels.size()));
        // round times to DECIMALS decimal places
        final int DECIMALS = 1;
        for (Score score : subList) {
            score.setWpm(Math.round(score.getWpm() * Math.pow(10, DECIMALS)) / Math.pow(10, DECIMALS));
        }
        return subList;
    }
}
