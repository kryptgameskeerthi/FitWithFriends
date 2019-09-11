package com.kryptgames.health.fitwithfriends.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.adapters.ActivityTypeListAdapter;
import com.kryptgames.health.fitwithfriends.utils.FitCalculationUtils;
import com.yashovardhan99.timeit.Stopwatch;

import java.lang.reflect.Executable;


public class ExcerciseTrackActivity extends AppCompatActivity implements ActivityTypeListAdapter.FitActivityTypeClicked, SensorEventListener {

    private RecyclerView activityListView;
    public static RecyclerView.Adapter activityListAdapter;
    LinearLayoutManager layoutManager;
    private TextView textTimer, distanceTravelledTV;
    private Button startButton;
    private boolean isButtonClicked;
    SensorManager sManager;
    Sensor stepSensor;

    long steps;
    float currentDistance;
    float calories;
    String activityTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise_track);
        isButtonClicked = false;


        SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);


        activityListView = findViewById(R.id.activityTypeList);

        activityListAdapter = new ActivityTypeListAdapter(ExcerciseTrackActivity.this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        //, LinearLayoutManager.HORIZONTAL, true
        activityListView.setLayoutManager(layoutManager);
        activityListView.setAdapter(activityListAdapter);

        textTimer = (TextView) findViewById(R.id.timerTextView);
        distanceTravelledTV = findViewById(R.id.distanceTravelledTV);

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.setTextView(textTimer);

        startButton = findViewById(R.id.startButtonId);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isButtonClicked) {
                    sManager.registerListener(ExcerciseTrackActivity.this
                            , stepSensor, SensorManager.SENSOR_DELAY_FASTEST);

                    startButton.setBackgroundResource(R.drawable.button_round_selected);
                    startButton.setText("Done");
                    stopwatch.start();
                    isButtonClicked = true;
                } else {
                    activityTime = FitCalculationUtils.getFormattedTime(stopwatch.getElapsedTime());
                    calories = FitCalculationUtils.getCaloriesBurnt(stopwatch.getElapsedTime());
                    stopwatch.stop();
                    startButton.setBackgroundResource(R.drawable.button_round);
                    startButton.setText("Start");
                    sManager.unregisterListener(ExcerciseTrackActivity.this, stepSensor);

                    isButtonClicked = false;
                    showActivityCompletedDialog();
                }

            }
        });


    }

    @Override
    public void onFitTypeClicked(int index) {

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }


        if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            steps++;

            float newDistance = getDistanceRun(steps);
            if (!areEqualToTwoDecimal(newDistance, currentDistance)) {
                currentDistance = newDistance;
                updateDistanceTravelled(currentDistance);

            }


        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public float getDistanceRun(long steps) {
        float distance = (float) (steps * 78) / (float) 100000;
        // testing purpose
        //float distance = (float) (steps * 78) / (float) 1000;

        return distance;
    }

    public static boolean areEqualToTwoDecimal(float a, float b) {

        a = a * 100;

        b = b * 100;

        int a1 = (int) a;

        int b1 = (int) b;

        return a == b;
    }

    private void updateDistanceTravelled(float value) {
        int numPortion = (int) value;
        int decPortion = ((int) (value * 100)) % 100;
        String valueStr = Integer.toString(numPortion) + "." + String.format("%02d", decPortion);
        distanceTravelledTV.setText(valueStr);
    }


    public void showActivityCompletedDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(ExcerciseTrackActivity.this, R.style.AlertDialogStyle));
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.exercise_completed_dialog, null);

        //dialogBuilder.setCustomTitle(setDialogTitle("Adjust Dosage", launchingActivity));

        dialogBuilder.setView(dialogView);

        TextView totalDistance = dialogView.findViewById(R.id.totalDistanceTV);
        TextView totalTime = dialogView.findViewById(R.id.totalTimeTV);
        TextView totalSteps = dialogView.findViewById(R.id.totalStepsTV);
        TextView totalCalories = dialogView.findViewById(R.id.totalCaloriesTV);

        totalDistance.setText(Float.toString(currentDistance));
        totalTime.setText(activityTime);
        totalSteps.setText(Long.toString(steps));
        totalCalories.setText(Float.toString(calories));




        dialogBuilder.setPositiveButton("Home", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                Intent homeIntent = new Intent(ExcerciseTrackActivity.this, HomeScreenActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
        return;


    }


}
