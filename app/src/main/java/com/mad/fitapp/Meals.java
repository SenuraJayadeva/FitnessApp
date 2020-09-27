package com.mad.fitapp;

public class Meals {

    private String mealID;
    private String mealName;
    private String description;
    private String calories;

    public Meals() {
    }

    public Meals(String mealID, String mealName, String description, String calories) {
        this.mealID = mealID;
        this.mealName = mealName;
        this.description = description;
        this.calories = calories;
    }

    public String getMealID() {
        return mealID;
    }

    public void setMealID(String mealID) {
        this.mealID = mealID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
