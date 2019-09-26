package com.kryptgames.health.fitwithfriends.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.FitWithFriendsApplication;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.FitActivity;
import com.kryptgames.health.fitwithfriends.utils.FitCalculationUtils;


import java.util.ArrayList;
import java.util.List;

public class ExerciseListFragment extends Fragment {

    private ArrayList<FitActivity> activityList;


    public static Fragment newInstance() {


        return new ExerciseListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        activityList = FitWithFriendsApplication.getDbPresenter().getUserActivityHistory(null);

        View view = inflater.inflate(R.layout.fragment_exercise_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.exercise_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(activityList, getContext()));
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView exerciseListDistanceTV, exerciseListCaloriesTV, exerciseListTimeTV;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.exercise_list_item, container, false));
            exerciseListDistanceTV = itemView.findViewById(R.id.exerciseListDistanceTV);
            exerciseListCaloriesTV = itemView.findViewById(R.id.exerciseListCaloriesTV);
            exerciseListTimeTV = itemView.findViewById(R.id.exerciseListTimeTV);
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private List<FitActivity> detailsList;
        Context context;

        public RecyclerViewAdapter(List<FitActivity> detailsList, Context context) {
            this.detailsList = detailsList;
            this.context = context;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

            FitActivity currentActivity = activityList.get(position);

            holder.exerciseListDistanceTV.setText(Float.toString(currentActivity.getDistance()) + " kms");
            holder.exerciseListCaloriesTV.setText(Float.toString(currentActivity.getCaloriesBurnt()) + "calories");
            holder.exerciseListTimeTV.setText(FitCalculationUtils.getFormattedTime(currentActivity.getElapsedTime()));


        }

        @Override
        public int getItemCount() {

            return activityList.size();
        }
    }

}

