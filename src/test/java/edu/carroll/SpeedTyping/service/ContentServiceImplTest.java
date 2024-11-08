package edu.carroll.SpeedTyping.service;

import edu.carroll.SpeedTyping.jpa.model.Level;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;

import static org.springframework.test.util.AssertionErrors.*;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class ContentServiceImplTest {

    @Autowired
    private ContentService contentService;

    /**
     * Add a couple scores and a level to the database
     */
    @BeforeEach
    public void populateDatabase() {
        // Create a test level
        Level level = new Level();
        level.setLevelname("Level 1");
        level.setWordcount(50);
        level.setLeveldifficulty(Level.LevelDifficulty.MEDIUM);
        level.setContent("Hi, this is a test");
        // Save this data
        contentService.saveLevel(level);
    }

    // Happy
    @Test
    void getLevelsForLeveldifficulty_ValidInput() {
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
        // Act
        List<Level> returnedLevels = contentService.getLevelsForLeveldifficulty(Level.LevelDifficulty.HARD);

        // Assert
        assertTrue("Expected an empty list of levels for non-existing difficulty level, but got a list", returnedLevels.isEmpty());
    }

    @Test
    public void getLevelsForLeveldifficulty_NullInput() {
        // Act
        List<Level> emptyList = new LinkedList<>();

        // Assert
        assertEquals("Searching for 'null' level difficulty did not return and empty list", emptyList, contentService.getLevelsForLeveldifficulty(null));
    }

    @Test
    void findByLevelid_NoneFound() {
        Level result = contentService.findByLevelid(100);
        assertNull("Expected null for non-existing level id, but got a level", result);
    }

    // Crappy
    @Test
    @Transactional
    public void testSaveDuplicateLevelid() {
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
    public void findByLevelid_NegativeInput() {
        Level result = contentService.findByLevelid(-1);
        assertNull("Expected null for non-existing level id, but got a level", result);
    }
}
