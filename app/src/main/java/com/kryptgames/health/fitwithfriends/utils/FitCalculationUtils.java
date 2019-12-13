package com.kryptgames.health.fitwithfriends.utils;

import java.util.concurrent.TimeUnit;

public class FitCalculationUtils {

    public static String getFormattedTime(long elapsedTime) {
        long  totalseconds  = elapsedTime /1000;

        return  String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(elapsedTime),
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsedTime)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime)));
    }

    public static float getCaloriesBurnt(long elapsedTime) {

        return elapsedTime * 5 / (1000 *60);

    }

    public static float getDistanceRun(long steps) {
        float distance = (float) (steps * 78) / (float) 100000;
        // testing purpose
        //float distance = (float) (steps * 78) / (float) 1000;

        return distance;
    }
}
