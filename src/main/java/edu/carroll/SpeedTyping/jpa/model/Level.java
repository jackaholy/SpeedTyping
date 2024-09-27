package edu.carroll.SpeedTyping.jpa.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "level")
public class Level  {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer levelId;

    @OneToMany(mappedBy = "level")
    private Set<Score> scores;

    @Column(name = "level_name", nullable = false)
    private String levelName;

    @Column(name = "word_count", nullable = false)
    private Integer wordCount;

    @Column(name = "level_difficulty")
    private Integer levelDifficulty;

    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    public Level() {
    }

    public Level(String levelName, Integer wordCount, Integer levelDifficulty, String content) {
        this.levelName = levelName;
        this.wordCount = wordCount;
        this.levelDifficulty = levelDifficulty;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;
        return Objects.equals(levelId, level.levelId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(levelId);
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getLevelDifficulty() {
        return levelDifficulty;
    }

    public void setLevel_difficulty(Integer levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }
}
