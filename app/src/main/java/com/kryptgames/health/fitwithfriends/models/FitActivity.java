package com.kryptgames.health.fitwithfriends.models;

import com.kryptgames.health.fitwithfriends.utils.FitCalculationUtils;

public class FitActivity {

    private long elapsedTime, steps;
    private float caloriesBurnt;
    private float distance;

    public FitActivity(long elapsedTime, long steps) {
        this.elapsedTime = elapsedTime;
        this.steps = steps;
        distance = FitCalculationUtils.getDistanceRun(steps);
        caloriesBurnt = FitCalculationUtils.getCaloriesBurnt(elapsedTime);
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

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
