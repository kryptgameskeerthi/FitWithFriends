package com.kryptgames.health.fitwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.kryptgames.health.fitwithfriends.R;

import java.util.Calendar;


public class CalenderFragment extends Fragment {
    public CalendarView calendarView;
    public TextView dateHolder,monthHolder,yearHolder;
    private Button prev,next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_missions_calender,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        calendarView = (CalendarView) view.findViewById(R.id.fwf_calenderview_calender);
        dateHolder = (TextView)view.findViewById(R.id.fwf_textview_startdate_holder);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                if(year<thisYear || year==thisYear&&month< thisMonth || year==thisYear&&month==thisMonth&&dayOfMonth<thisDay)
                    Toast.makeText(getContext(),"Please select a valid date",Toast.LENGTH_SHORT).show();
                else{
                    Fragment fragment=new InviteFriendsFragment();
                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.r2l_slide_in, R.anim.r2l_slide_out, R.anim.l2r_slide_in, R.anim.l2r_slide_out);                    fragmentTransaction.replace(R.id.fwf_layout_fragmentcontainer,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
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
