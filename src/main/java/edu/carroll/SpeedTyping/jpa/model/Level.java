package edu.carroll.SpeedTyping.jpa.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "level")
public class Level {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer levelid;

    @OneToMany(mappedBy = "level")
    private Set<Score> scores;

    @Column(name = "levelname", nullable = false)
    private String levelname;

    @Column(name = "wordcount", nullable = false)
    private Integer wordcount;

    public enum LevelDifficulty{
        EASY,
        MEDIUM,
        HARD
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "leveldifficulty")
    private LevelDifficulty leveldifficulty;

    @Column(name = "content", nullable = false, length = 65535)
    private String content;

    public Level() {
    }

    public Level(String levelname, Integer wordcount, LevelDifficulty leveldifficulty, String content) {
        this.levelname = levelname;
        this.wordcount = wordcount;
        this.leveldifficulty = leveldifficulty;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Level level = (Level) o;
        return Objects.equals(levelid, level.levelid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(levelid);
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public String getLevelDifficultyName() {
        return leveldifficulty.toString();
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public Integer getWordcount() {
        return wordcount;
    }

    public void setWordcount(Integer wordcount) {
        this.wordcount = wordcount;
    }

    public LevelDifficulty getLeveldifficulty() {
        return leveldifficulty;
    }

    public void setLeveldifficulty(LevelDifficulty leveldifficulty) {
        this.leveldifficulty = leveldifficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getLevelid() {
        return levelid;
    }

    public void setLevelid(Integer levelid) {
        this.levelid = levelid;
    }
}
