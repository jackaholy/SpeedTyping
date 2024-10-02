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

@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    private final ScoreRepository scoreRepository;

    private final LevelRepository levelRepository;

    public LeaderboardServiceImpl(ScoreRepository scoreRepository, LevelRepository levelRepository) {
        this.scoreRepository = scoreRepository;
        this.levelRepository = levelRepository;
    }

    public List<Score> getScoresForLevel(Level level) {
        return scoreRepository.findAllByLevelOrderByTimeDesc(level);
    }

    @Override
    public List<Score> getScoresForLeveldifficulty(Integer leveldifficulty) {
        log.info("getScoresForLeveldifficulty: Retrieving score for level {}", leveldifficulty);
        List<Level> diffLevels = levelRepository.findByLeveldifficulty(leveldifficulty);
        List<Score> scores = new ArrayList<>();
        for (Level level : diffLevels) {
            scores.addAll(getScoresForLevel(level));
        }
        return scores;
    }

    @Override
    public List<Score> getNScoresForDifficultySortByTime(Integer leveldifficulty, int n) {
        List<Score> diffLevels = getScoresForLeveldifficulty(leveldifficulty);
        diffLevels.sort(Comparator.comparing(Score::getTime));
        // Extract the n first elements
        return diffLevels.subList(0, Math.min(n, diffLevels.size()));
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    public List<Score> getLeaderboard() {
        return getAllLevels().stream()
                .flatMap(level -> getScoresForLevel(level).stream())
                .collect(Collectors.toList());
    }
}
