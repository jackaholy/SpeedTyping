package edu.carroll.SpeedTyping.jpa.model;

/**
 * A test will store the data we need from one play of the typing test
 */
public class TypeTest {
    private String username;
    private Double time;
    private Integer currentLevel;
    private String typedContent;

    public TypeTest() {

    }

    @Override
    public String toString() {
        return "Test{" +
                "username='" + username + '\'' +
                ", time=" + time +
                ", currentLevel=" + currentLevel +
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

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getTypedContent() {
        return typedContent;
    }

    public void setTypedContent(String typedContent) {
        this.typedContent = typedContent;
    }
}
