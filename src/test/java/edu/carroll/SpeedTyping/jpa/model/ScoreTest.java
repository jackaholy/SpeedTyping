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
//
//    @Test
//    void testGetPlayid_ReturnsCorrectPlayid() {
//        Long expectedPlayid = 12345L;
//        score = new Score();
//        score.setPlayid(expectedPlayid);
//
//        Long actualPlayid = score.getPlayid();
//
//        assertEquals(expectedPlayid, actualPlayid, "getPlayid() should return the correct playid value.");
//    }

//    @Test
//    void testGetPlayid_ReturnsNullWhenPlayidNotSet() {
//        score = new Score();
//
//        Long actualPlayid = score.getPlayid();
//
//        // Assert
//        assertNull(actualPlayid, "getPlayid() should return null if playid is not set.");
//    }

    @Test
    void testHashCode_ConsistentHashCode() {
        int hashCode1 = score.hashCode();
        int hashCode2 = score.hashCode();
        assertEquals(hashCode1, hashCode2, "hashCode() should return the same value on multiple calls with the same state.");
    }

    // Crazy path tests
    @Test
    public void testNullScore() {
        Score s = new Score(null, null, null);
        assertNull(s.getUsername());
        assertNull(s.getSubmissiondate());
        assertNull(s.getWpm());
    }
}