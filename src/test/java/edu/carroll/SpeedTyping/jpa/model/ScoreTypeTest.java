package edu.carroll.SpeedTyping.jpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ScoreTypeTest {

    Score score;

    @BeforeEach
    void setUp() {
        score = new Score("Test User", new Date(), 120.0);
        score.setPlayid(1L);
        score.setLevel(new Level());
    }

    @Test
    void testPlayId() {
        assertEquals(1L, score.getPlayid());
    }

    @Test
    void testUsername() {
        assertEquals("Test User", score.getUsername());
    }

    @Test
    void testDate() {
        assertNotNull(score.getDate());
    }

    @Test
    void testTime() {
        assertEquals(120.0, score.getTime(), 0.001);
    }

    @Test
    void testLevel() {
        assertNotNull(score.getLevel());
    }

    // Crazy path tests
    @Test
    void testNullScore() {
        Score s = new Score(null, null, null);
        assertNull(s.getUsername());
        assertNull(s.getDate());
        assertNull(s.getTime());
    }
}