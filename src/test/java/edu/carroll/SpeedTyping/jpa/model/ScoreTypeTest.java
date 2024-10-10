package edu.carroll.SpeedTyping.jpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ScoreTypeTest {

    private Score score;

    @BeforeEach
    public void setUp() {
        score = new Score("Test User", new Date(), 120.0);
        score.setPlayid(1L);
        score.setLevel(new Level());
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