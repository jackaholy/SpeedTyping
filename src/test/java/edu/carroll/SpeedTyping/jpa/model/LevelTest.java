package edu.carroll.SpeedTyping.jpa.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LevelTest {
    private static final Logger log = LoggerFactory.getLogger(LevelTest.class);
    
    Level level;
    
    @BeforeEach
    public void setUp() {
        level = new Level("Test Level", 100, 2, "Test Content");
    }

    // Happy path tests
    @Test
    public void testLevelId() {
        level.setLevelid(1);
        assertEquals(1, level.getLevelid());
        log.info("REMOVE LATER: Testing logging");
        System.out.println("REMOVE LATER: Testing logging (sout)");
    }

    @Test
    public void testGetLevelDifficultyName() {
        assertEquals("MEDIUM", level.getLevelDifficultyName());
    }

    // Crappy path tests
    @Test
    public void testInvalidLevelDifficulty() {
        assertThrows(IllegalArgumentException.class, () -> level.setLeveldifficulty(10));
    }

    // Crazy path tests
    @Test
    public void testNullLevel() {
        Level l = new Level(null, null, 2, null);
        assertNull(l.getLevelname());
        assertNull(l.getWordcount());
        assertNull(l.getContent());
    }
}