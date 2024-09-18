package edu.carroll.SpeedTyping;

import java.util.List;

import edu.carroll.SpeedTyping.jpa.repo.LevelRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

// This class optionally pre-populates the database with login data.  In
// a real application, this would be done with a completely different
// method.
@Component
public class DbInit {
    // XXX - This is wrong on so many levels....
    private static final String defaultUsername = "username";
    private static final String defaultPassHash = "-1164577301";

    private final LevelRepository levelRepo;

    public DbInit(LevelRepository levelRepo) {
        this.levelRepo = levelRepo;
    }

    // invoked during application startup
    @PostConstruct
    public void loadData() {
        // If the user doesn't exist in the database, populate it
        // ADD CODE TO CREATE DEFAULT LEVELS

        final List<Level> defaultLevels = levelRepo.findByUsernameIgnoreCase(defaultUsername);
        if (defaultUsers.isEmpty()) {
            Login defaultUser = new Login();
            defaultUser.setUsername(defaultUsername);
            defaultUser.setHashedPassword(defaultPassHash);
            loginRepo.save(defaultUser);
        }
    }
}
