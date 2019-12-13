package com.kryptgames.health.fitwithfriends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RootFragment extends Fragment {
    public RootFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_missions_fragment_container, container, false);
        if (getFragmentManager() != null) {
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.fwf_layout_fragmentcontainer, new NewMissionsRecyclerFragment());

            transaction.commit();
        }
        return  view;

    }
}
