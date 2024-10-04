package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ContentService interface provides methods to retrieve content (level or levels) for a typing test.
 */
@Service
public interface ContentService {
    List<Level> getLevelsForLeveldifficulty(Integer leveldifficulty);
}
