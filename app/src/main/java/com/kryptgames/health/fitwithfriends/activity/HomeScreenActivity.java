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
import com.kryptgames.health.fitwithfriends.BlankFragment;
import com.kryptgames.health.fitwithfriends.CalenderFragment;
import com.kryptgames.health.fitwithfriends.MyRewardsFragment;
import com.kryptgames.health.fitwithfriends.Pager;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.RewardsRecyclerFragment;

import java.util.zip.Inflater;

public class HomeScreenActivity extends AppCompatActivity {

    private ImageButton notifications,menu;
    private ActionBar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView notificationCount;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_drawer);

        //openDialog();

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.activity_toolbar);
        getSupportActionBar().getCustomView();
        viewPager=(ViewPager) findViewById(R.id.fwf_layout_view_pager);
        TabLayout tabLayout=(TabLayout) findViewById(R.id.fwf_layout_tab);

        tabLayout.addTab(tabLayout.newTab().setText("Active"));
        tabLayout.addTab(tabLayout.newTab().setText("New"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.rust));

        Pager pager = new Pager(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pager);




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
                Intent trackExerciseIntent = new Intent(HomeScreenActivity.this, ExcerciseTrackActivity.class);
                startActivity(trackExerciseIntent);
                finish();
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
    public void selectTab(int position){
        viewPager.setCurrentItem(position);
    }

    /*public void openDialog(){
        InvitePopup invitePopup=new InvitePopup();
        invitePopup.show(getSupportFragmentManager(),"example");
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelelctedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId())
            {
                case R.id.fwf_navigation_home:
                    viewPager.setVisibility(View.VISIBLE);
                    viewPager.setCurrentItem(0);
                    selectedFragment=new BlankFragment();
                    break;
                case R.id.fwf_navigation_activities:
                    viewPager.setVisibility(View.GONE);
                    selectedFragment=new BlankFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the activities icon",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fwf_navigation_rewards:
                    viewPager.setVisibility(View.GONE);
                    selectedFragment=new MyRewardsFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the rewards icon",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fwf_navigation_profile:
                    viewPager.setVisibility(View.GONE);
                    selectedFragment=new BlankFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the profile icon",Toast.LENGTH_SHORT).show();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,selectedFragment).commit();
            return true;
        }
    };

}