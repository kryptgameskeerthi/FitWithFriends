package com.kryptgames.health.fitwithfriends;

public class NewMissionsPojo {

    public String  title,distance,duration,group;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public NewMissionsPojo(String title, String distance, String duration, String group) {
        this.title = title;
        this.distance = distance;
        this.duration = duration;
        this.group = group;
    }
}
