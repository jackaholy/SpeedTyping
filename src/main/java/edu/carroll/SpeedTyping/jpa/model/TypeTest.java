package edu.carroll.SpeedTyping.jpa.model;

/**
 * A test will store the data we need from one play of the typing test. As of writing this, this class is used to transfer
 * tested information to the backend java controller where logic and database storage is done.
 */
public class TypeTest {
    /** The name of the user as determined by a prompt that appears at the end of each test.  */
    private String username;
    /** How long it took the player to type until the length of typed content matched the length of the target content currently measured by characters. */
    private Double time;
    /** The id of the level that was played during the typing test. */
    private Integer levelId;
    /** The string representing the content typed by the user. */
    private String typedContent;

    public TypeTest() {

    }

    @Override
    public String toString() {
        return "Test{" +
                "username='" + username + '\'' +
                ", time=" + time +
                ", levelId=" + levelId +
                ", typedContent='" + typedContent + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getTypedContent() {
        return typedContent;
    }

    public void setTypedContent(String typedContent) {
        this.typedContent = typedContent;
    }
}
