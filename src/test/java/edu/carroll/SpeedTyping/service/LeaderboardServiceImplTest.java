package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.LinkedList;
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
        // Create first score
        Score score1 = new Score();
        score1.setLevel(level);
        score1.setTime(10.0);
        score1.setDate(new Date());
        score1.setUsername("andrew");
        // Create second score
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

    @Test
    public void testGetScoresForLevelDifficulty() {
        List<Score> scores = leaderboardService.getScoresForLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        assertNotNull("Retrieved scores should not be null.", scores);
        assertFalse("No scores retrieved", scores.isEmpty());
        assertEquals("The retrieved level should have a difficulty of MEDIUM.", Level.LevelDifficulty.MEDIUM, scores.getFirst().getLevel().getLeveldifficulty());
    }

    @Test
    public void testGetScoresForLeveldifficulty_NoLevelsFound() {
        // Retrieve scores for a difficulty with no levels
        List<Score> scores = leaderboardService.getScoresForLeveldifficulty(Level.LevelDifficulty.HARD);
        assertNotNull("Resulting list should not be null.", scores);
        assertTrue("Resulting list should be empty.", scores.isEmpty());
    }

    @Test
    public void testGetScoresForLeveldifficulty_NullDifficulty() {
        // Retrieve scores with null difficulty
        List<Score> scores = leaderboardService.getScoresForLeveldifficulty(null);
        assertNotNull("Resulting list should not be null.", scores);
        assertTrue("Resulting list should be empty.", scores.isEmpty());
    }

    // getNScoresForDifficultySortByTime: Check handling of huge 'n'
    @Test
    public void getNScoresForDifficultySortByTimeHugeN() {
        List<Score> scores = leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.MEDIUM, 10);
        assertEquals("Two scores should have been returned because that's all we put into difficulty MEDIUM", 2, scores.size());
    }

    @Test
    public void getScoresForLeveldifficulty_NullInput() {
        List<Score> emptyScores = new LinkedList<>();
        assertEquals("Scores retrieved for level difficulty 'null' should be an empty list", emptyScores, leaderboardService.getScoresForLeveldifficulty(null));
    }

    @Test
    public void getScoresForDifficultySortByWpm_NullInput() {
        List<Score> emptyScores = new LinkedList<>();
        assertEquals("Scores retrieved for level difficulty 'null' should be an empty list", emptyScores, leaderboardService.getNScoresForDifficultySortByWpm(null, 5));
    }

    @Test
    public void getScoresForDifficultySortByWpm_ZeroLevels() {
        List<Score> emptyScores = new LinkedList<>();
        assertEquals("N scores retrieved for level difficulty medium should be an empty list", emptyScores, leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.MEDIUM, 0));
    }

    // getNScoresForDifficultySortByTime: Check handling for when the program asks for negative scores
    @Test
    public void getNScoresForDifficultySortByTimeNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.MEDIUM, -6);
        });
    }

    @Test
    public void getNScoresForDifficultySortByWpm_WeirdDifficulty() {
        assertThrows(IllegalArgumentException.class, () -> {
            leaderboardService.getNScoresForDifficultySortByWpm(Level.LevelDifficulty.valueOf("banana"), 5);
        });
    }
}