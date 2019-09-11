package com.kryptgames.health.fitwithfriends.models;

public class FitActivity {

    private long elapsedTime;
    private float caloriesBurnt;
    private int steps;
    private float distance;

    public FitActivity(long elapsedTime, int steps) {
        this.elapsedTime = elapsedTime;
        this.steps = steps;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(float caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
