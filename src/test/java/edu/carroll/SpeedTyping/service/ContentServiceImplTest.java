package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.*;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class ContentServiceImplTest {

    @Autowired
    private ContentService contentService;
    private Level level;

    /**
     * Add a couple scores and a level to the database
     */
    public void populateDatabase() {
        // Create a test level
        level = new Level();
        level.setLevelname("Level 1");
        level.setWordcount(50);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Hi, this is a test");
    }

    @Test
    void getLevelsForLeveldifficulty_ValidInput() {
        populateDatabase();
        contentService.saveLevel(level);
        // Act
        List<Level> returnedLevels = contentService.getLevelsForLeveldifficulty(Level.LevelDifficulty.MEDIUM);

        // Assert
        assertTrue("The returned level list is empty", !returnedLevels.isEmpty());
        assertEquals("The level name is incorrect", "Level 1", returnedLevels.getFirst().getLevelname());
        assertEquals("The word count is incorrect", 50, returnedLevels.getFirst().getWordcount());
        assertEquals("The level difficulty is incorrect", Level.LevelDifficulty.MEDIUM, returnedLevels.getFirst().getLeveldifficulty());
        assertEquals("The content is incorrect", "Hi, this is a test", returnedLevels.getFirst().getContent());
    }

    @Test
    void getLevelsForLeveldifficulty_NoMatchingDifficulty() {
        populateDatabase();
        contentService.saveLevel(level);
        // Act
        List<Level> returnedLevels = contentService.getLevelsForLeveldifficulty(Level.LevelDifficulty.HARD);

        // Assert
        assertTrue("Expected an empty list of levels for non-existing difficulty level, but got a list", returnedLevels.isEmpty());
    }

    @Test
    public void getLevelsForLeveldifficulty_NullInput() {
        populateDatabase();
        contentService.saveLevel(level);
        assertTrue("Searching for 'null' level difficulty did not return and empty list", contentService.getLevelsForLeveldifficulty(null).isEmpty());
    }

    @Test
    void findByLevelid_NoneFound() {
        populateDatabase();
        contentService.saveLevel(level);
        Level result = contentService.findByLevelid(100);
        assertNull("Expected null for non-existing level id, but got a non-null result", result);
    }

    @Test
    public void findByLevelid_NegativeInput() {
        populateDatabase();
        contentService.saveLevel(level);
        Level result = contentService.findByLevelid(-1);
        assertNull("Expected null for non-existing level id, but got a non-null result", result);
    }

    @Test
    public void saveScore_NullScore() {
        assertFalse("saveScore_NullScore: Saving a null score should return false to indicate a failed save.", contentService.saveScore(null));
    }

    @Test
    public void saveScore_NoUsername() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score();
        score.setWpm(50.0);
        score.setDate(Calendar.getInstance().getTime());
        score.setLevel(level);
        assertFalse("saveScore_NoUsername: Content service should return false when a score with no username is passed in to be saved.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_NoSubmissionDate() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score();
        score.setWpm(50.0);
        score.setUsername("Andrew");
        score.setLevel(level);
        assertFalse("saveScore_NoUsername: Content service should return false when a score with no date is passed in to be saved.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_NoWPM() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score();
        score.setUsername("Andrew");
        score.setDate(Calendar.getInstance().getTime());
        score.setLevel(level);
        assertFalse("saveScore_NoUsername: Content service should return false when a score with no wpm is passed in to be saved.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_NoLevelId() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score();
        score.setWpm(50.0);
        score.setUsername("Andrew");
        score.setDate(Calendar.getInstance().getTime());
        assertFalse("saveScore_NoUsername: Content service should return false when a score with no level id is passed in to be saved.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_InvalidLevelId() {
        populateDatabase();
        contentService.saveLevel(level);
        // Create new level
        Level levelNotInDatabase = new Level("Invalid Level", 50, Level.LevelDifficulty.EASY, "Invalid content");

        Score score = new Score();
        score.setWpm(50.0);
        score.setUsername("Andrew");
        score.setDate(Calendar.getInstance().getTime());
        score.setLevel(levelNotInDatabase);
        assertFalse("saveScore_NoUsername: Content service should return false when attempting to save a score with a level that is not in the database.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_InvalidUsernameCharacters() { //TODO: Build the response to this case in the controller
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score("@ndrew", Calendar.getInstance().getTime(), 50.0);
        score.setLevel(level);
        assertFalse("saveScore_InvalidUsernameCharacters: A score was passed in with a non-alphanumeric character, which should be invalid and return false.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_InvalidUsernameLength() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score("a", Calendar.getInstance().getTime(), 50.0);
        score.setLevel(level);
        assertFalse("saveScore_InvalidUsernameLength: A score was passed in with a length of 1, which should be invalid and return false.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_LongUsernameLength() {
        populateDatabase();
        contentService.saveLevel(level);

        // Represent the large string by its length
        int largeLength = 1000000000;
        Score score = new Score("LENGTH:" + largeLength, Calendar.getInstance().getTime(), 50.0);
        score.setLevel(level);
        assertFalse("saveScore_LongUsernameLength: A score was passed in with a length of 1000000000, which is invalid and should return false.", contentService.saveScore(score));
    }

    @Test
    public void saveScore_NegativeWPM() {
        populateDatabase();
        contentService.saveLevel(level);
        Score score = new Score("Andrew", Calendar.getInstance().getTime(), -1.0);
        score.setLevel(level);
        assertFalse("saveScore_NegativeWPM: A score was passed in with a wpm of -1.0, which should be invalid and return false.", contentService.saveScore(score));
    }

    // saveLevel
    @Test
    public void saveLevel_NullLevel() {
        assertFalse("saveLevel_NullLevel: Saving a null level should return false to indicate a failed save.", contentService.saveLevel(null));
    }

    @Test
    public void saveLevel_NewLevel() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setWordcount(100);
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent("Sample content.");
        boolean result = contentService.saveLevel(level);
        assertTrue("saveLevel_NewLevel: Saving a new level should return true.", result);
        assertNotNull("saveLevel_NewLevel: Level ID should be set after saving.", level.getId());
    }

    @Test
    public void saveLevel_UpdateLevelName() {
        // First, save a new level
        Level level = new Level();
        level.setLevelname("Intermediate");
        level.setWordcount(200);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Intermediate level content.");
        contentService.saveLevel(level);

        // Update the existing level
        level.setLevelname("Advanced");
        boolean result = contentService.saveLevel(level);
        assertTrue("saveLevel_ExistingLevel: Updating an existing level should return true.", result);
    }

    @Test
    @Transactional
    public void saveLevel_DuplicateLevelId() {
        populateDatabase();
        contentService.saveLevel(level);
        Level level = new Level();
        level.setLevelname("Level 2");
        level.setWordcount(50);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Hi, this is another test");
        List<Level> returnedLevels = contentService.getLevelsForLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setId(returnedLevels.getFirst().getId());
        contentService.saveLevel(level);
        // Because the levels have the same id, the original id should be overwritten by the new id
        assertEquals("The level name should have been updated", "Level 2", contentService.findByLevelid(level.getId()).getLevelname());
    }


    @Test
    public void saveLevel_NoLevelName() {
        Level level = new Level();
        level.setWordcount(100);
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent("Sample content.");
        assertFalse("saveLevel_NoLevelName: Saving a level without a name should return false.", contentService.saveLevel(level));
    }

    @Test
    public void saveLevel_NullLevelName() {
        Level level = new Level();
        level.setLevelname(null); // Explicitly set to null
        level.setWordcount(100);
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent("Sample content.");
        assertFalse("saveLevel_NullLevelName: Saving a level with null name should return false.", contentService.saveLevel(level));
    }

    @Test
    public void saveLevel_NoWordCount() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent("Sample content.");
        assertFalse("saveLevel_NoWordCount: Saving a level without word count should return false.", contentService.saveLevel(level));
    }

    @Test
    public void saveLevel_NullWordCount() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setWordcount(null); // Explicitly set to null
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent("Sample content.");
        assertFalse("saveLevel_NullWordCount: Saving a level with null word count should return false.", contentService.saveLevel(level));
    }

    @Test
    public void saveLevel_NoContent() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setWordcount(100);
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        boolean result = contentService.saveLevel(level);
        assertFalse("saveLevel_NoContent: Saving a level without content should return false.", result);
    }

    @Test
    public void saveLevel_NullContent() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setWordcount(100);
        level.setLeveldifficulty(Level.LevelDifficulty.EASY);
        level.setContent(null); // Explicitly set to null
        assertFalse("saveLevel_NullContent: Saving a level with null content should return false.", contentService.saveLevel(level));
    }

    @Test
    public void saveLevel_NoLevelDifficulty() {
        Level level = new Level();
        level.setLevelname("Beginner");
        level.setWordcount(100);
        level.setContent("Sample content.");
        boolean result = contentService.saveLevel(level);
        assertFalse("saveLevel_NoLevelDifficulty: Saving a level without difficulty should return false.", result);
    }

    @Test
    public void saveLevel_NullLevelDifficulty() {
        Level level = new Level();
        level.setLevelname("Expert");
        level.setWordcount(300);
        level.setContent("Expert level content.");
        level.setLeveldifficulty(null);
        boolean result = contentService.saveLevel(level);
        assertFalse("saveLevel_InvalidLevelDifficulty: Saving a level with null difficulty should return false.", result);
    }

    @Test
    public void saveLevel_NegativeWordCount() {
        Level level = new Level();
        level.setLevelname("Negative Word Count Level");
        level.setWordcount(-50);
        level.setLeveldifficulty(Level.LevelDifficulty.HARD);
        level.setContent("Sample content.");
        boolean result = contentService.saveLevel(level);
        assertFalse("saveLevel_NegativeWordCount: Saving a level with negative word count should return false.", result);
    }

}
