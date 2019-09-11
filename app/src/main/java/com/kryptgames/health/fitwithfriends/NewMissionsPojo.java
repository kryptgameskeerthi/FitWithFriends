package com.kryptgames.health.fitwithfriends;

public class NewMissionsPojo {

    public String  title;
    public int distance,duration,group;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public NewMissionsPojo(String title, int distance, int duration, int group) {
        this.title = title;
        this.distance = distance;
        this.duration = duration;
        this.group = group;
    }
}
