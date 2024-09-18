package edu.carroll.SpeedTyping.jpa.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "score")
public class Score {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long play_id;

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "date", nullable = false)
    private Integer date;

    @Column(name = "time", nullable = false)
    private Double time;

    public Score() {
    }

    public Score(String username, Integer date, Double time) {
        this.username = username;
        this.date = date;
        this.time = time;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Score{" +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    public Long getPlay_id() {
        return play_id;
    }

    public void setPlay_id(Long play_id) {
        this.play_id = play_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Score otherScore = (Score)o;
        return play_id.equals(otherScore.getPlay_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(play_id, username, date, time);
    }

}