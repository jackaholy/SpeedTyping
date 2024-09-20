package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {
    private final ScoreRepository scoreRepository;

    private final LevelRepository levelRepository;

    public LeaderboardServiceImpl(ScoreRepository scoreRepository, LevelRepository levelRepository) {
        this.scoreRepository = scoreRepository;
        this.levelRepository = levelRepository;
    }

    public List<Score> getScoresForLevel(Level level) {
        return scoreRepository.findAllByLevelOrderByTimeDesc(level);
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
