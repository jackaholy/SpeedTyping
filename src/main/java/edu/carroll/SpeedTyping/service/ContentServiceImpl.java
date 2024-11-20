package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * ContentServiceImpl class provides methods to retrieve content (level or levels) for a typing test.
 */
@Service
public class ContentServiceImpl implements ContentService {
    private static final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    // LevelRepository to interact with the Level data from the database.
    public final LevelRepository levelRepository;

    // ScoreRepository to interact with the Score data from the database.
    public final ScoreRepository scoreRepository;

    /** This is the minimum length that a player's username can be. */
    private final int MIN_USERNAME_LENGTH = 3;

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
    public List<Level> getLevelsForLeveldifficulty(Level.LevelDifficulty leveldifficulty) {
        log.info("getLevelsForLeveldifficulty: difficulty: {}", leveldifficulty);
        if (leveldifficulty == null) {
            return new LinkedList<>();
        }
        return levelRepository.findByLeveldifficulty(leveldifficulty);
    }

    @Override
    public Level findByLevelid(Integer currentLevel) {
        log.info("findByLevelid: current level id = {}", currentLevel);
        Optional<Level> level = levelRepository.findById(currentLevel);
        return level.orElse(null);
    }

    @Override
    public boolean saveScore(Score score) {
        if (score == null) {
            log.error("saveScore: score is null");
            return false;
        } else if (!score.isValid()) {
            log.error("saveScore: This score is deemed invalid by self check. Score: {}", score);
            return false;
        } else if (score.getUsername().length() < MIN_USERNAME_LENGTH || !score.getUsername().matches("[a-zA-Z0-9]+")) {
            log.error("saveScore: This score has an invalid username. Score: {}", score);
            return false;
        } else if (score.getWpm() < 0) {
            log.error("saveScore: This score has a negative wpm. Score: {}", score);
            return false;
        } else if (score.getLevel().getId() == null || levelRepository.findById(score.getLevel().getId()).isEmpty()) {
            log.error("saveScore: This level is empty or contains a null id. Score: {}, Level: {}", score, score.getLevel());
            return false;
        }

        double wpm = score.getWpm();
        if (wpm > 500) {
            log.warn("saveScore: wpm is over 500!!!");
        }
        scoreRepository.save(score);
        return true;
    }

    @Override
    public boolean saveLevel(Level level) {
        log.info("saveLevel: level: {}", level);
        if (level == null) {
            log.error("saveLevel: level is null");
            return false;
        } else if (level.getLevelname() == null || level.getLevelname().isEmpty()) {
            log.error("saveLevel: levelname is null or empty Level: {}", level);
            return false;
        } else if (level.getWordcount() == null || level.getWordcount() < 0) {
            log.error("saveLevel: level wordcount is null or less than 0 Level: {}", level);
            return false;
        } else if (level.getContent() == null || level.getContent().isEmpty()) {
            log.error("saveLevel: level content is null or empty Level: {}", level);
            return false;
        } else if (level.getLeveldifficulty() == null) {
            log.error("saveLevel: level leveldifficulty is null, Level: {}", level);
            return false;
        } else if (level.getId() != null) {
            Level existingLevel = levelRepository.findById(level.getId()).orElse(null);
            if (existingLevel != null) {
                log.warn("saveLevel: level already exists. Replaced level = {}", existingLevel);
                existingLevel.setLevelname(level.getLevelname());
                existingLevel.setWordcount(level.getWordcount());
                existingLevel.setLeveldifficulty(level.getLeveldifficulty());
                existingLevel.setContent(level.getContent());
                levelRepository.save(existingLevel);
                return true;
            }
        }
        levelRepository.save(level);
        return true;
    }

}
