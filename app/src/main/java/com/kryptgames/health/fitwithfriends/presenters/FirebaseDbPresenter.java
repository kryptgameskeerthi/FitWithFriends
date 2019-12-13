package com.kryptgames.health.fitwithfriends.presenters;

import com.kryptgames.health.fitwithfriends.models.FitActivity;
import com.kryptgames.health.fitwithfriends.models.FitnessMission;
import com.kryptgames.health.fitwithfriends.models.FitnessUser;
import com.kryptgames.health.fitwithfriends.models.Reward;

import java.util.ArrayList;

public class FirebaseDbPresenter implements DbPresenterContract {
    @Override
    public void registerUser(FitnessUser user) {

    }

    @Override
    public FitnessUser fetchUserById(String userId) {
        return null;
    }

    @Override
    public ArrayList<FitnessUser> getAllUsers() {
        return null;
    }

    @Override
    public void getPresetGoals() {

    }

    @Override
    public ArrayList<Reward> getRewardsByGoalId(String goalId) {
        return null;
    }

    @Override
    public void createUserMission(FitnessMission mission) {

    }

    @Override
    public void setMissionGoal(FitnessMission mission, String goalId) {

    }

    @Override
    public void setMissionReward(FitnessMission mission, String rewardId) {

    }

    @Override
    public void addUserFitActivity(FitActivity activity) {

    }

    @Override
    public ArrayList<FitActivity> getUserActivityHistory(String userId) {
        return null;
    }

    @Override
    public void showAllRewards() {

    }

    @Override
    public ArrayList<FitnessMission> getMissionsLinkedWithAReward(String rewardId) {
        return null;
    }

    @Override
    public ArrayList<Reward> getCompletedRewardsForAUser(String userId) {
        return null;
    }
}
