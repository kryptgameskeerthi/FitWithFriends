package com.kryptgames.health.fitwithfriends.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kryptgames.health.fitwithfriends.R;

public class HomeScreenActivity extends AppCompatActivity {

    private ImageButton notifications,menu;
    private ActionBar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView notificationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_drawer);


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_toolbar);
        getSupportActionBar().getCustomView();

        TabLayout tabLayout=(TabLayout) findViewById(R.id.fwf_layout_tab);
        ViewPager viewPager=(ViewPager) findViewById(R.id.fwf_layout_view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.rust));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        toolbar = getSupportActionBar();
        BottomNavigationView navigation=(BottomNavigationView)findViewById(R.id.fwf_navigationview_bottomnavigationbar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelelctedListener);

        notifications=findViewById(R.id.fwf_imagebutton_notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenActivity.this,"you are about to visit notifications page",Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton = findViewById(R.id.fwf_button_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeScreenActivity.this,"navigation to add new activity page",Toast.LENGTH_SHORT).show();
            }
        });

        menu = findViewById(R.id.fwf_imagebutton_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelelctedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.fwf_navigation_home:
                    Toast.makeText(HomeScreenActivity.this,"You have clicked on the home icon",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.fwf_navigation_activities:
                    Toast.makeText(HomeScreenActivity.this,"You have clicked on the activities icon",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.fwf_navigation_rewards:
                    Toast.makeText(HomeScreenActivity.this,"You have clicked on the rewards icon",Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.fwf_navigation_profile:
                    Toast.makeText(HomeScreenActivity.this,"You have clicked on the profile icon",Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };
}