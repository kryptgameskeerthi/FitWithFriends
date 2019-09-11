package com.kryptgames.health.fitwithfriends.presenters;

import com.kryptgames.health.fitwithfriends.models.FitActivity;
import com.kryptgames.health.fitwithfriends.models.FitnessMission;
import com.kryptgames.health.fitwithfriends.models.FitnessUser;
import com.kryptgames.health.fitwithfriends.models.Reward;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface DbPresenterContract {

    public void registerUser(FitnessUser user);

    public FitnessUser fetchUserById(String userId);

    public ArrayList<FitnessUser> getAllUsers();


    public void getPresetGoals();

    public ArrayList<Reward> getRewardsByGoalId(String goalId);

    public void createUserMission(FitnessMission mission);

    public void setMissionGoal(FitnessMission mission, String goalId);

    public void setMissionReward(FitnessMission mission, String rewardId);


    public void addUserFitActivity(FitActivity activity);

    public ArrayList<FitActivity> getUserActivityHistory(String userId);

    public void showAllRewards();

    public ArrayList<FitnessMission> getMissionsLinkedWithAReward(String rewardId);

    public ArrayList<Reward> getCompletedRewardsForAUser(String userId);








}
