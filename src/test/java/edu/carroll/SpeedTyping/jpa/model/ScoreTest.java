package edu.carroll.SpeedTyping.jpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreTest {

    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score("Test User", new Date(), 120.0);
        score.setPlayid(1L);
        score.setLevel(new Level());
    }

    @Test
    public void testIsValid() {
        assertTrue(score.isValid());
    }

    @Test
    void testIsValid_AllFieldsNotNull() {
        assertTrue(score.isValid(), "isValid() should return true when all fields are not null.");
    }

    @Test
    void testIsValid_LevelIsNull() {
        score = new Score(null, new Date(), 120.0);
        assertFalse(score.isValid(), "isValid() should return false when level is null.");
    }

    @Test
    void testIsValid_DateIsNull() {
        score = new Score("1", null, 120.0);
        assertFalse(score.isValid(), "isValid() should return false when date is null.");
    }

    @Test
    void testIsValid_TimeIsNull() {
        Score score = new Score("1", new Date(), null);
        assertFalse(score.isValid(), "isValid() should return false when time is null.");
    }

    // Crazy path tests
    @Test
    public void testNullScore() {
        Score s = new Score(null, null, null);
        assertNull(s.getUsername());
        assertNull(s.getDate());
        assertNull(s.getTime());
    }
}