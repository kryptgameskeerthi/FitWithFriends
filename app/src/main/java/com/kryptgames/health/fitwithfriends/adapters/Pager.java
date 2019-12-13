package com.kryptgames.health.fitwithfriends.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kryptgames.health.fitwithfriends.fragment.BlankFragment;
import com.kryptgames.health.fitwithfriends.fragment.RecyclerFragment;
import com.kryptgames.health.fitwithfriends.fragment.RootFragment;


public class Pager extends FragmentPagerAdapter {

    int tabCount;


    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecyclerFragment recyclerFragment=new RecyclerFragment();
                return recyclerFragment;
            case 1:
                RootFragment newMissionsRecyclerFragment = new RootFragment();
                return newMissionsRecyclerFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Active";
            case 1:
                return "New";

            default:
                return null;
        }
    }
}