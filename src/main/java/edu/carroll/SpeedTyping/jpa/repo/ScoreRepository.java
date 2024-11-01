package edu.carroll.SpeedTyping.jpa.repo;

import edu.carroll.SpeedTyping.jpa.model.Level;
import edu.carroll.SpeedTyping.jpa.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    // JPA throws an exception if we attempt to return a single object that doesn't exist, so return a list
    // even though we only expect either an empty list or a single element.
    List<Score> findByUsernameIgnoreCase(String username);
    List<Score> findAllByLevelOrderByWpmDesc(Level level);}
