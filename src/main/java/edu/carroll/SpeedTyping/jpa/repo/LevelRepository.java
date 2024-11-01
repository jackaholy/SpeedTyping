package edu.carroll.SpeedTyping.jpa.repo;

import java.util.List;
import java.util.Optional;

import edu.carroll.SpeedTyping.jpa.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list or a single element.
    List<Level> findByLevelnameIgnoreCase(String levelname);
    List<Level> findByLeveldifficulty(Level.LevelDifficulty leveldifficulty);
    Optional<Level> findById(Integer Id);
}