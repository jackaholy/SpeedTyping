package edu.carroll.SpeedTyping.jpa.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long playid; // TODO: Adjust to id

    @ManyToOne
    @JoinColumn(name = "levelid", nullable = false)
    private Level level;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date", nullable = false) // TODO: submission date
    private Date date;

    @Column(name = "time", nullable = false)
    private Double time; // TODO: time = WPM

    public Score() {
    }

    public Score(String username, Date date, Double time) {
        this.username = username;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Score{" +
                ", username='" + username + "'" +
                ", date=" + date +
                ", time=" + time +
                "}"; // TODO: include id
    }

    /**
     * Tells us if the current score is valid for saving
     * @return true if the score is valid for saving, false if not
     */
    public boolean isValid() {
        return level != null && username != null && date != null && time != null;
    }

    public Long getPlayid() {
        return playid;
    }

    public void setPlayid(Long playid) {
        this.playid = playid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Score otherScore = (Score)o;
        return playid.equals(otherScore.getPlayid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(playid, username, date, time);
    }

}