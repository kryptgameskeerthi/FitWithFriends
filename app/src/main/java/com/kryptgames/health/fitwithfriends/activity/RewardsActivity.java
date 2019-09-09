package com.kryptgames.health.fitwithfriends.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.RewardsRecyclerFragment;

public class RewardsActivity extends AppCompatActivity {

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            overridePendingTransition(R.anim.l2r_slide_in, R.anim.l2r_slide_out);
        }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.r2l_slide_in, R.anim.r2l_slide_out);
        setContentView(R.layout.new_missions_fragment_container);

        FragmentManager fm= getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fwf_layout_fragmentcontainer);

        if(fragment==null)
        {
            fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.fwf_layout_fragmentcontainer,fragment)
                    .commit();
        }
    }

    protected Fragment createFragment() {
        return new RewardsRecyclerFragment().newInstance();

    }
}
