package edu.carroll.SpeedTyping.jpa.repo;

import java.util.List;

import edu.carroll.SpeedTyping.jpa.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list or a single element.
    List<Level> findByLevelNameIgnoreCase(String levelName);
    List<Level> findByLevelDifficulty(Integer levelDifficulty);
    List<Level> findByLevelId(Integer levelId);
}