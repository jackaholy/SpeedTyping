package edu.carroll.SpeedTyping.jpa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class TestTypeTest {

    private TypeTest testObject;

    @BeforeEach
    void setUp() {
        testObject = new TypeTest();
    }

    @Test
    void testUsername() {
        testObject.setUsername("TestUser");
        assertEquals("TestUser", testObject.getUsername());
    }

    @Test
    void testTime() {
        Double testTime = 15.2;
        testObject.setTime(testTime);
        assertEquals(testTime, testObject.getTime());
    }

    @Test
    void testCurrentLevel() {
        Integer testLevel = 3;
        testObject.setCurrentLevel(testLevel);
        assertEquals(testLevel, testObject.getCurrentLevel());
    }

}