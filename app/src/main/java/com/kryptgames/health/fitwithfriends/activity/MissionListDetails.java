package com.kryptgames.health.fitwithfriends.activity;


import com.github.mikephil.charting.data.BarEntry;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

import java.util.ArrayList;

public class MissionListDetails {

    private String goal;
    private String duration;
    private ArrayList<BarEntry> Info;
    private ArrayList<String> users;
    private String title;
    private ArrayList<InvitePopupPojo> horizontalList;




    public MissionListDetails(String goal, String duration, ArrayList<BarEntry> info, ArrayList<String> users, String title, ArrayList<InvitePopupPojo> horizontalList) {
        this.goal = goal;
        this.duration = duration;
        Info = info;
        this.users = users;
        this.title = title;
        this.horizontalList = horizontalList;
    }

    public ArrayList<BarEntry> getInfo() {
        return Info;
    }

    public ArrayList<String> getUsers() {
        return users;
    }


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<InvitePopupPojo> getHorizontalList() {
        return horizontalList;
    }

    public void setHorizontalList(ArrayList<InvitePopupPojo> horizontalList) {
        this.horizontalList = horizontalList;
    }





}
