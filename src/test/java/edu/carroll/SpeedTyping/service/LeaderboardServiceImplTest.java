package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.*;

@Transactional
@SpringBootTest
public class LeaderboardServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(LeaderboardServiceImplTest.class);

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
    public void testGetScoresForLevelDifficulty() {
        List<Score> scores = leaderboardService.getScoresForLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        assertNotNull("Retrieved scores should not be null.", scores);
        assertFalse("No scores retrieved", scores.isEmpty());
        assertEquals("The retrieved level should have a difficulty of MEDIUM.", Level.LevelDifficulty.MEDIUM, scores.getFirst().getLevel().getLeveldifficulty());
    }

    // Crappy

    // getNScoresForDifficultySortByTime: Check handling of huge 'n'
    @Test
    public void getNScoresForDifficultySortByTimeHugeN() {
        List<Score> scores = leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.MEDIUM, 10);
        assertEquals("Two scores should have been returned because that's all we put into difficulty MEDIUM", 2, scores.size());
    }

    // Crazy

    // getNScoresForDifficultySortByTime: Check handling for when the program asks for negative scores
    @Test
    public void getNScoresForDifficultySortByTimeNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.MEDIUM, -6);
        });
    }
}
