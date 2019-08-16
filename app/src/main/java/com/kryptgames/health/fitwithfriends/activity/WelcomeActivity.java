package com.kryptgames.health.fitwithfriends.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.kryptgames.health.fitwithfriends.R;

public class WelcomeActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 300000;// 3s


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(WelcomeActivity.this, getNextScreen());
                startActivity(homeIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

    private Class<?> getNextScreen() {
        return PhoneAuthenticationActivity.class;
        // For Now Just adding the static admin screen but need to build the Logic to select appropriate screen
    }
}
