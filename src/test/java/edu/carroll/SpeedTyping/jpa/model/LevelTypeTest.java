package edu.carroll.SpeedTyping.jpa.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LevelTypeTest {
    
    Level level;
    
    @BeforeEach
    void setUp() {
        level = new Level("Test Level", 100, 2, "Test Content");
    }

    // Happy path tests
    @Test
    void testLevelId() {
        level.setLevelid(1);
        assertEquals(1, level.getLevelid());
    }

    @Test
    void testLevelName() {
        assertEquals("Test Level", level.getLevelname());
    }

    @Test
    void testLevelDifficulty() {
        assertEquals(2, level.getLeveldifficulty());
    }

    @Test
    void testWordCount() {
        assertEquals(100, level.getWordcount());
    }
    
    @Test
    void testContent() {
        assertEquals("Test Content", level.getContent());
    }

    @Test
    void testGetLevelDifficultyName() {
        assertEquals("MEDIUM", level.getLevelDifficultyName());
    }

    // Crappy path tests
    @Test
    void testInvalidLevelDifficulty() {
        assertThrows(IllegalArgumentException.class, () -> level.setLeveldifficulty(10));
    }

    // Crazy path tests
    @Test
    void testNullLevel() {
        Level l = new Level(null, null, 2, null);
        assertNull(l.getLevelname());
        assertNull(l.getWordcount());
        assertNull(l.getContent());
    }
}