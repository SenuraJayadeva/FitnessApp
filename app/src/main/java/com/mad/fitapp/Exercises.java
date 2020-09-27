package com.mad.fitapp;

public class Exercises {

    private String ExerciseID;
    private String ExerciseName;
    private String Reps;
    private String Sets;

    public Exercises() {
    }

    public Exercises(String exerciseID, String exerciseName, String reps, String sets) {
        ExerciseID = exerciseID;
        ExerciseName = exerciseName;
        Reps = reps;
        Sets = sets;
    }

    public String getExerciseID() {
        return ExerciseID;
    }

    public void setExerciseID(String exerciseID) {
        ExerciseID = exerciseID;
    }

    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getReps() {
        return Reps;
    }

    public void setReps(String reps) {
        Reps = reps;
    }

    public String getSets() {
        return Sets;
    }

    public void setSets(String sets) {
        Sets = sets;
    }
}
