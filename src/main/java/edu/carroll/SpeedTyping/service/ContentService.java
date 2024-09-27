package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContentService {
    List<Level> getLevelsForLevelDifficulty(Integer levelDifficulty);
}
