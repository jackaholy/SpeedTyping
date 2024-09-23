package edu.carroll.SpeedTyping;

import java.util.List;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

// This class optionally pre-populates the database with login data.  In
// a real application, this would be done with a completely different
// method.
@Component
public class DbInit {
    // XXX - This is wrong on so many levels....
    private static final String defaultLevelName = "level1";
    private static final Integer defaultWordCount = 14;
    private static final Integer level_difficulty = 1;
    private static final String content = "Hi, this is just a test level and should not be used later on.";

    private final LevelRepository levelRepo;

    public DbInit(LevelRepository levelRepo) {
        this.levelRepo = levelRepo;
    }

    // invoked during application startup
//    @PostConstruct
//    public void loadData() {
//        // If the user doesn't exist in the database, populate it
//        final List<Level> defaultLevels = levelRepo.findByLevelNameIgnoreCase(defaultLevelName);
//        if (defaultLevels.isEmpty()) {
//            Level defaultLevel = new Level(defaultLevelName, defaultWordCount, level_difficulty, content);
//            levelRepo.save(defaultLevel);
//        }
//    }
}
