package edu.carroll.SpeedTyping.jpa.repo;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ScoreRepository extends JpaRepository<Score, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list or a single element.
    List<Score> findByUsernameIgnoreCase(String username);
    List<Score> findAllByLevelOrderByTimeDesc(Level level);}
