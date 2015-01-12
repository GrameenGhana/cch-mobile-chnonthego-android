package org.digitalcampus.oppia.model;

public class CourseChild {

    private String topic;
    private int pretestScore;
    private int posttestScore;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getPretestScore() {
        return pretestScore;
    }

    public void setPretestScore(int pretestScore) {
        this.pretestScore = pretestScore;
    }
    public int getPosttestScore() {
        return posttestScore;
    }

    public void setPosttestScore(int posttestScore) {
        this.posttestScore = posttestScore;
    }
}