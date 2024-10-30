package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import static org.springframework.test.util.AssertionErrors.*;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class ContentServiceImplTest {

    @Autowired
    private ContentService contentService;

    @Autowired
    private LevelRepository levelRepo;

    @Autowired
    private ScoreRepository scoreRepo;

    @Autowired
    private LeaderboardService leaderboardService;

    /**
     * Add a couple scores and a level to the database
     */
    @BeforeEach
    public void populateDatabase() {
        // Create a test level
        Level level = new Level();
        level.setLevelname("Level 1");
        level.setWordcount(50);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Hi, this is a test");
        // Create two scores for this level
        Score score1 = new Score();
        score1.setLevel(level);
        score1.setTime(10.0);
        score1.setDate(new Date());
        score1.setUsername("andrew");
        Score score2 = new Score();
        score2.setLevel(level);
        score2.setTime(15.0);
        score2.setDate(new Date());
        score2.setUsername("jack");
        // Save this data
        levelRepo.save(level);
        scoreRepo.save(score1);
        scoreRepo.save(score2);
    }

    // Happy
    @Test
    public void testGetLevelsForLeveldifficulty() {
        List<Level> levels = contentService.getLevelsForLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        assertEquals("Only one medium level was added so far", 1, levels.size());
    }

    // Crappy
    @Test
    public void testSaveDuplicateLevelid() {
        Level level = new Level();
        level.setLevelname("Level 2");
        level.setWordcount(50);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Hi, this is another test");
        contentService.saveLevel(level);
    }


}
