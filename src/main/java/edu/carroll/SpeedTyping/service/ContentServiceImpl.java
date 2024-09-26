package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    public final LevelRepository levelRepository;

    public final ScoreRepository scoreRepository;

    public ContentServiceImpl(LevelRepository levelRepository, ScoreRepository scoreRepository) {
        this.levelRepository = levelRepository;
        this.scoreRepository = scoreRepository;
    }

    @Override
    public List<Level> getLevelsForLevel_difficulty(Integer level_difficulty) {
        return levelRepository.findByLevel_difficulty(level_difficulty);
    }
}
