package com.kryptgames.health.fitwithfriends.models;

import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompletedMissionsVerticalPojo {
    String title;
    int goal,contribution,reward;
    ArrayList<InvitePopupPojo> list;
    ArrayList<BarEntry> teamMissionInfo;
    ArrayList<String> users;
    private boolean isSelected=false;


    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public ArrayList<BarEntry> getTeamMissionInfo() {
        return teamMissionInfo;
    }

    public void setTeamMissionInfo(ArrayList<BarEntry> teamMissionInfo) {
        this.teamMissionInfo = teamMissionInfo;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public CompletedMissionsVerticalPojo(String title, int goal, int contribution, ArrayList<InvitePopupPojo> list, ArrayList<BarEntry> teamMissionInfo, ArrayList<String> users, int reward) {
        this.title = title;
        this.goal = goal;
        this.contribution = contribution;
        this.list = list;
        this.teamMissionInfo=teamMissionInfo;
        this.users=users;
        this.reward=reward;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
    }

    public ArrayList<InvitePopupPojo> getList() {
        return list;
    }

    public void setList(ArrayList<InvitePopupPojo> list) {
        this.list = list;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }
}
