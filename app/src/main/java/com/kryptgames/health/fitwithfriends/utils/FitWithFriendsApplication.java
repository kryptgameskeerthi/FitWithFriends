package com.kryptgames.health.fitwithfriends.utils;

import android.app.Application;

import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.FitActivityType;
import com.kryptgames.health.fitwithfriends.presenters.DbPresenterContract;
import com.kryptgames.health.fitwithfriends.presenters.TestDbPresenter;

import java.util.ArrayList;

public class FitWithFriendsApplication extends Application {

    public static ArrayList<FitActivityType> fitActivityTypes;

    private static DbPresenterContract dbPresenter;

    @Override
    public void onCreate() {
        super.onCreate();

        dbPresenter =  new TestDbPresenter();

        fitActivityTypes = new ArrayList<FitActivityType>();

        fitActivityTypes.add(new FitActivityType("Running", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Hiking", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Swimming", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Walking", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Cycling", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));
        fitActivityTypes.add(new FitActivityType("Weights", R.drawable.ic_navigation_home_icon, R.drawable.ic_navigation_trophy_icon));

    }

    public static DbPresenterContract getDbPresenter() {
        return dbPresenter;
    }
}