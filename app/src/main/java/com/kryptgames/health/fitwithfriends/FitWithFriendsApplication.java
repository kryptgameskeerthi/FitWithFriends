package com.kryptgames.health.fitwithfriends;

import android.app.Application;

import com.kryptgames.health.fitwithfriends.models.FitActivityType;

import java.util.ArrayList;

public class FitWithFriendsApplication extends Application {

    public static ArrayList<FitActivityType> fitActivityTypes;

    @Override
    public void onCreate() {
        super.onCreate();

        fitActivityTypes = new ArrayList<FitActivityType>();

        fitActivityTypes.add(new FitActivityType("Running", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Hiking", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Swimming", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Walking", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Cycling", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Weights", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));

    }
}