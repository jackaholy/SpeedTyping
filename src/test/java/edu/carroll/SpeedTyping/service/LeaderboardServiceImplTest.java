package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import edu.carroll.SpeedTyping.jpa.repo.ScoreRepository;
import edu.carroll.SpeedTyping.web.controller.MainController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class LeaderboardServiceImplTest {
    private static final Logger log = LoggerFactory.getLogger(LeaderboardServiceImplTest.class);

    private LevelRepository levelRepo;
    private ScoreRepository scoreRepo;

    private LeaderboardService leaderboardService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Add a couple scores and levels to the database
     */
    @Transactional
    @BeforeEach
    public void populateDatabase() {
        Level level = new Level();
        level.setLeveldifficulty(2);
        Score score1 = new Score();
        score1.setLevel(level);
        score1.setTime(10.0);
        Score score2 = new Score();
        score2.setLevel(level);
        score2.setTime(15.0);
        levelRepo.save(level);
        scoreRepo.save(score1);
        scoreRepo.save(score2);
        log.info("Test run");
//        System.out.println("Test run");
//        entityManager.persist(level);
//        entityManager.persist(score1);
//        entityManager.persist(score2);
    }

    // Happy
    @Test
    public void testGetScoresForLevelDifficulty() {
        List<Score> scores = leaderboardService.getScoresForLeveldifficulty(2);
        assertNotNull(scores);
        // Choose a score and check its difficulty
        assertEquals(2, scores.getFirst().getLevel().getLeveldifficulty());
    }

    // Crappy

    // Crazy
}
