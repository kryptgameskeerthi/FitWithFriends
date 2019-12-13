package com.kryptgames.health.fitwithfriends.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import com.kryptgames.health.fitwithfriends.fragment.BlankFragment;
import com.kryptgames.health.fitwithfriends.fragment.MyRewardsFragment;
import com.kryptgames.health.fitwithfriends.adapters.Pager;

import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.fragment.ExerciseListFragment;
import com.kryptgames.health.fitwithfriends.fragment.UserProfilePageFragment;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {

    private ImageButton notifications,menu;
    private ActionBar toolbar;
    private FloatingActionButton floatingActionButton;
    private TextView notificationCount;
    private ViewPager viewPager;

    private String senderNumber,missionTitle;
    private ArrayList<InvitePopupPojo> participantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_drawer);
        //openDialog();

        senderNumber=getIntent().getStringExtra("senderNumber");
        if(senderNumber!=null){
        missionTitle=getIntent().getStringExtra("missionTitle");
        participantsList=getIntent().getExtras().getParcelableArrayList("userinfo");

       /* try{JSONArray participantsArray = new JSONArray(getIntent().getParcelableArrayListExtra("userinfo"));
        for(int i=0;i<participantsArray.length();i++) {
            JSONObject participantsArrayJSONObject = participantsArray.getJSONObject(i);
            String image=participantsArrayJSONObject.getString("userImage");
            String name=participantsArrayJSONObject.getString("userName");
            participantsList.add(new InvitePopupPojo(image,name));
        }}catch (JSONException e){e.printStackTrace();}*/


            openDialog();}


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

    public void openDialog(){
        InvitePopup invitePopup=new InvitePopup();
        Bundle bundle=new Bundle();
        bundle.putString("userNumber",senderNumber);
        bundle.putString("missionTitle",missionTitle);
        bundle.putParcelableArrayList("userinfo",participantsList);
        invitePopup.setArguments(bundle);
        invitePopup.show(getSupportFragmentManager(),"example");
    }

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
                    selectedFragment=new ExerciseListFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the profile icon",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fwf_navigation_rewards:
                    viewPager.setVisibility(View.GONE);
                    selectedFragment=new MyRewardsFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the rewards icon",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fwf_navigation_profile:
                    viewPager.setVisibility(View.GONE);
                    selectedFragment=new UserProfilePageFragment();
                    //Toast.makeText(HomeScreenActivity.this,"You have clicked on the profile icon",Toast.LENGTH_SHORT).show();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,selectedFragment).commit();
            return true;
        }
    };

}