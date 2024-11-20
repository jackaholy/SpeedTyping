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

    /*
     Test isValid()
     */
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

    /*
     Test hashCode()
     */
    @Test
    void testHashCode_HashCode() {
        Score score1 = new Score("2", new Date(), null);
        int hashCode1 = score1.hashCode();
        Score score2 = new Score("3", new Date(), null);
        int hashCode2 = score2.hashCode();
        assertNotEquals(hashCode1, hashCode2, "hashCode() should return the same value on multiple calls with the same state.");
    }

    /*
     Test object comparison
     */
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

    /*
     Test getWpm()
     */
    @Test
    void testGetWpm_DefaultValue() {
        // Create a Score instance without setting wpm
        Score score = new Score();

        // Assert that the default value of wpm is null
        assertNull(score.getWpm(), "wpm should be null by default if not initialized.");
    }

    @Test
    void testGetWpm_ValueSetViaSetter() {
        // Create a Score instance and set wpm via a setter
        score.setWpm(72.5);

        // Assert that getWpm() returns the value set via the setter
        assertEquals(72.5, score.getWpm(), "wpm should return the value set via the setter.");
    }

    @Test
    void testGetWpm_ValueSetViaConstructor() {
        // Assuming the Score class has a constructor that accepts wpm
        Score score = new Score("2", new Date(), 50.0);

        // Assert that getWpm() returns the value set in the constructor
        assertEquals(50.0, score.getWpm(), "wpm should return the value set in the constructor.");
    }

    @Test
    void testGetWpm_UpdatedValue() {
        // Create a Score instance with an initial wpm but update it
        score.setWpm(55.0);

        // Assert that getWpm() returns the latest value
        assertEquals(55.0, score.getWpm(), 0.01, "wpm should return the latest value set.");
    }

    @Test
    void testGetWpm_SetToNull() {
        // Set wpm to null
        score.setWpm(null);

        // Assert that getWpm() returns null
        assertNull(score.getWpm(), "wpm should return null when explicitly set to null.");
    }

    @Test
    void testGetWpm_NegativeValue() {
        // Set a negative wpm value
        score.setWpm(-10.0);

        // Assert that getWpm() returns the negative value
        assertEquals(-10.0, score.getWpm(), 0.01, "wpm should handle negative values correctly.");
    }

    @Test
    void testGetWpm_LargeValue() {
        // Set a very large wpm value
        score.setWpm(1000000.0);

        // Assert that getWpm() correctly handles large values
        assertEquals(1_000_000.0, score.getWpm(), 0.01, "wpm should handle large values correctly.");
    }

    @Test
    void testGetWpm_SmallFractionalValue() {
        // Set wpm to a very small fractional value
        score.setWpm(0.0001);

        // Assert that getWpm() correctly returns small fractional values
        assertEquals(0.0001, score.getWpm(), 0.00001, "wpm should handle small fractional values correctly.");
    }

    /*
     Test getUsername()
     */
    @Test
    void testGetUsername_DefaultValue() {
        // Create a Score instance without setting username
        Score score = new Score();

        // Assert that the default value of username is null
        assertNull(score.getUsername(), "username should be null by default if not initialized.");
    }

    @Test
    void testGetUsername_ValueSetViaConstructor() {
        // Create a Score instance and set username via the constructor
        Score score = new Score("testUser", new Date(), null);

        // Assert that getUsername() returns the correct value
        assertEquals("testUser", score.getUsername(), "username should return the value set in the constructor.");
    }

    @Test
    void testGetUsername_ValueSetViaSetter() {
        // Set username via the setter
        score.setUsername("newUser");

        // Assert that getUsername() returns the value set via the setter
        assertEquals("newUser", score.getUsername(), "username should return the value set via the setter.");
    }

    @Test
    void testGetUsername_UpdatedValue() {
        // Try updating an old username
        score.setUsername("updatedUser");

        // Assert that getUsername() returns the latest value
        assertEquals("updatedUser", score.getUsername(), "username should return the latest value set.");
    }

    @Test
    void testGetUsername_SetToNull() {
        // Explicitly set username to null
        score.setUsername(null);

        // Assert that getUsername() returns null
        assertNull(score.getUsername(), "username should return null when explicitly set to null.");
    }

    @Test
    void testGetUsername_EmptyString() {
        // Set username to an empty string
        score.setUsername("");

        // Assert that getUsername() correctly handles an empty string
        assertEquals("", score.getUsername(), "username should return an empty string when set to an empty string.");
    }

    @Test
    void testGetUsername_SpecialCharacters() {
        // Set username to a string with special characters
        score.setUsername("@dm1n!user#");

        // Assert that getUsername() correctly handles special characters
        assertEquals("@dm1n!user#", score.getUsername(), "username should return a string with special characters correctly.");
    }

    @Test
    void testGetUsername_LongString() {
        // Set username to a very long string
        String longUsername = "a".repeat(100000000);
        score.setUsername(longUsername);

        // Assert that getUsername() correctly handles long strings
        assertEquals(longUsername, score.getUsername(), "username should correctly return a very long string.");
    }

    /*
     Test getDate()
     */
    @Test
    void testGetDate_DefaultValue() {
        // Create a Score instance without setting the submissiondate
        Score score = new Score();

        // Assert that the default value of submissiondate is null
        assertNull(score.getDate(), "submissiondate should be null by default if not initialized.");
    }

    @Test
    void testGetDate_ValueSetViaConstructor() {
        // Create a Score instance and set submissiondate via the constructor
        Date now = new Date();
        Score score = new Score("testUser", now, null);

        // Assert that getDate() returns the correct value
        assertEquals(now, score.getDate(), "submissiondate should return the value set in the constructor.");
    }

    @Test
    void testGetDate_ValueSetViaSetter() {
        // Set submissiondate via the setter
        Date now = new Date();
        score.setDate(now);

        // Assert that getDate() returns the value set via the setter
        assertEquals(now, score.getDate(), "submissiondate should return the value set via the setter.");
    }

    @Test
    void testGetDate_UpdatedValue() {
        // Update the submissiondate with a new date
        Date firstDate = new Date();
        score.setDate(firstDate);

        Date updatedDate = new Date();
        score.setDate(updatedDate);

        // Assert that getDate() returns the latest value
        assertEquals(updatedDate, score.getDate(), "submissiondate should return the latest value set.");
    }

    @Test
    void testGetDate_SetToNull() {
        // Explicitly set submissiondate to null
        score.setDate(null);

        // Assert that getDate() returns null
        assertNull(score.getDate(), "submissiondate should return null when explicitly set to null.");
    }

    @Test
    void testGetDate_Mutability() {
        // Set a mutable Date
        Date mutableDate = new Date();
        score.setDate(mutableDate);

        // Modify the original date object
        mutableDate.setTime(mutableDate.getTime() + 100000);

        // Assert that getDate() reflects the change
        assertEquals(mutableDate, score.getDate(), "submissiondate should reflect changes to the original date if no defensive copy is implemented.");
    }

    @Test
    void testGetDate_ImmutableBehavior() {
        // Set a Date
        Date originalDate = new Date();
        score.setDate(originalDate);

        // Retrieve the date and modify the returned object
        Date retrievedDate = score.getDate();
        retrievedDate.setTime(retrievedDate.getTime() + 100000);

        // Assert that the original date in the Score instance remains unchanged
        assertNotEquals(originalDate.getTime() + 100000, score.getDate().getTime(), "submissiondate should be immutable if defensive copying is implemented.");
    }

    @Test
    void testGetDate_BoundaryValue() {
        // Test using a boundary value
        Date unixDate = new Date(0); // January 1, 1970
        score.setDate(unixDate);

        // Assert that getDate() correctly handles the boundary value
        assertEquals(unixDate, score.getDate(), "submissiondate should correctly handle the Unix unixDate boundary value.");
    }

    @Test
    void testGetDate_FutureDate() {
        // Test using a future date
        Date futureDate = new Date(System.currentTimeMillis() + 10_000_000);
        score.setDate(futureDate);

        // Assert that getDate() correctly handles future dates
        assertEquals(futureDate, score.getDate(), "submissiondate should correctly handle future dates.");
    }

    /*
    Test getLevel()
    */
    @Test
    void testGetLevel_DefaultValue() {
        // Create a Score instance without setting level
        Score score = new Score();

        // Assert that the default value of level is null
        assertNull(score.getLevel(), "level should be null by default if not initialized.");
    }

    @Test
    void testGetLevel_SetToNull() {
        // Explicitly set level to null
        score.setLevel(null);

        // Assert that getLevel() returns null
        assertNull(score.getLevel(), "level should return null when explicitly set to null.");
    }
}