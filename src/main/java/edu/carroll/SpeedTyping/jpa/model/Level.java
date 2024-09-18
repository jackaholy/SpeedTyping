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
    private Integer level_id;

    @OneToMany(mappedBy = "level")
    private Set<Score> scores;

    @Column(name = "level_name", nullable = false)
    private String levelName;

    @Column(name = "word_count", nullable = false)
    private Integer wordCount;

    @Column(name = "level_difficulty")
    private Integer level_difficulty;

    @Column(name = "content", nullable = false)
    private String content;

    public Level() {
    }

    public Level(String levelName, Integer wordCount, Integer level_difficulty, String content) {
        this.levelName = levelName;
        this.wordCount = wordCount;
        this.level_difficulty = level_difficulty;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;
        return Objects.equals(level_id, level.level_id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level_id);
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

    public Integer getLevel_difficulty() {
        return level_difficulty;
    }

    public void setLevel_difficulty(Integer level_difficulty) {
        this.level_difficulty = level_difficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
