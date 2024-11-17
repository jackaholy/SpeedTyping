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
        score.setId(1L);
        score.setLevel(new Level());
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

    @Test
    void testHashCode_ConsistentHashCode() {
        int hashCode1 = score.hashCode();
        // TODO: create two different objects, same properties, check for false.
        int hashCode2 = score.hashCode();
        assertEquals(hashCode1, hashCode2, "hashCode() should return the same value on multiple calls with the same state.");
    }

    @Test
    void equals_ObjectComparisonEqual() {
        Score scoreCopy = new Score();
        scoreCopy.setUsername(score.getUsername());
        scoreCopy.setLevel(score.getLevel());
        scoreCopy.setDate(score.getDate());
        scoreCopy.setTime(score.getTime());
        assertEquals(score, scoreCopy, "equals() should return true when comparing to the same object.");
    }

    @Test
    void equals_ObjectComparisonNotEqual() {
        Score newScore = new Score();
        newScore.setUsername("Patrick Mahomes");
        newScore.setLevel(score.getLevel());
        newScore.setDate(score.getDate());
        newScore.setTime(score.getTime());
        assertNotEquals(score, newScore, "equals() should return false when the usernames do not match.");
    }

    @Test
    void equals_NullComparison() {
        assertNotEquals(null, score, "equals() should return false when comparing to null.");
    }
}