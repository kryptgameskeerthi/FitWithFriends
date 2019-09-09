package com.kryptgames.health.fitwithfriends.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kryptgames.health.fitwithfriends.R;

import java.util.Calendar;


public class CalenderActivity extends AppCompatActivity {

    public CalendarView calendarView;
    public TextView dateHolder,monthHolder,yearHolder;
    private Button prev,next;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.l2r_slide_in, R.anim.l2r_slide_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.r2l_slide_in, R.anim.r2l_slide_out);
        setContentView(R.layout.new_missions_calender);

        calendarView = (CalendarView) findViewById(R.id.fwf_calenderview_calender);
        dateHolder = (TextView)findViewById(R.id.fwf_textview_startdate_holder);

        Calendar calendar=Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        dateHolder.setText(thisDay+" "+monthFinder(thisMonth).substring(0,3)+" "+thisYear);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String trimMonth;
                trimMonth=monthFinder(month).substring(0,3);
                dateHolder.setText(dayOfMonth+" "+trimMonth+" "+year);
                Intent intent=new Intent(getApplicationContext(), InviteFriendsActivity.class);
                startActivity(intent);

            }
        });

    }

    private String monthFinder(int a){
        String month;
        String x[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
        month=x[a];
        return month;
    }
}
