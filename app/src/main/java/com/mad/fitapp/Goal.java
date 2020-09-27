package com.mad.fitapp;

public class Goal {
    private String goalId;
    private String date;
    private String weight;
    private String steps;
    private String heartRate;

    public Goal() {}

    public Goal(String goalId, String date, String weight, String steps, String heartRate) {
        this.goalId = goalId;
        this.date = date;
        this.weight = weight;
        this.steps = steps;
        this.heartRate = heartRate;
    }

    public String getDate() {
        return date;
    }

    public String getWeight() {
        return weight;
    }

    public String getSteps() {
        return steps;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getGoalId() {
        return goalId;
    }
}
