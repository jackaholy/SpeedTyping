package edu.carroll.SpeedTyping.jpa.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "score")
public class Score {
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(Score.class);

    @Id
    @GeneratedValue
    private Long id;

    /** This is the level the user was playing when they attained this score. */
    @ManyToOne
    @JoinColumn(name = "levelid", nullable = false)
    private Level level;

    /** This is what the player input for username when prompted at the end of the typing test. */
    @Column(name = "username", nullable = false)
    private String username;

    /** This is the timestamp for when the user submitted their typing test. */
    @Column(name = "submissiondate", nullable = false)
    private Date submissiondate;

    /** Correct words typed per minute. */
    @Column(name = "wpm", nullable = false)
    private Double wpm;

    public Score() {
    }

    public Score(String username, Date submissiondate, Double wpm) {
        this.username = username;
        this.submissiondate = submissiondate;
        this.wpm = wpm;
    }

    @Override
    public String toString() {
        return "Score{" +
                ", username='" + username + "'" +
                ", date=" + submissiondate +
                ", time=" + wpm +
                ", id=" + id +
                "}";
    }

    public Date getSubmissiondate() {
        return submissiondate;
    }

    public void setSubmissiondate(Date submissiondate) {
        this.submissiondate = submissiondate;
    }

    public Double getWpm() {
        return wpm;
    }

    public void setWpm(Double wpm) {
        this.wpm = wpm;
    }

    /**
     * Tells us if the current score is valid for saving. Valid for saving means all fields that will be saved (except ID) are not null. ID will be set by mysql.
     * @return true if the score is valid for saving, false if not
     */
    public boolean isValid() {
        return level != null && username != null && submissiondate != null && wpm != null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // TODO: Deprecate the following date and time getters and setters (we changed the name convention)
    public Date getDate() {
        return submissiondate;
    }

    public void setDate(Date submissiondate) {
        this.submissiondate = submissiondate;
    }

    public Double getTime() {
        return wpm;
    }

    public void setTime(Double time) {
        this.wpm = time;
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
        if (!this.isValid() || !otherScore.isValid()) {
            log.warn("equals: invalid scores were compared for equality");
            return false;
        }
        if (!this.username.equals(otherScore.username)) {
            return false;
        }
        if (!this.submissiondate.equals(otherScore.submissiondate)) {
            return false;
        }
        if (!this.wpm.equals(otherScore.wpm)) {
            return false;
        }
        if (!this.level.equals(otherScore.level)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, submissiondate, wpm);
    }

}