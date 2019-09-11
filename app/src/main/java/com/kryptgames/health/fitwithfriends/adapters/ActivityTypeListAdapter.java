package com.kryptgames.health.fitwithfriends.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.FitWithFriendsApplication;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.FitActivityType;

import java.util.ArrayList;


public class ActivityTypeListAdapter extends RecyclerView.Adapter<ActivityTypeListAdapter.ViewHolder> {



    private FitActivityTypeClicked activity;
    private int selectionCount;

    public interface FitActivityTypeClicked {
        void onFitTypeClicked(int index);
    }

    @NonNull
    @Override
    public ActivityTypeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_type_item, viewGroup, false);
        return new ViewHolder(v);
    }

    public ActivityTypeListAdapter(Context context) {

        activity = (FitActivityTypeClicked) context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FitActivityType fitActivityType = FitWithFriendsApplication.fitActivityTypes.get(position);

        holder.activityTypeTV.setText(fitActivityType.getFitEventType());
        holder.activityTypeIconIV.setImageResource(fitActivityType.getUnselectedImageId());

    }

    @Override
    public int getItemCount() {
        return FitWithFriendsApplication.fitActivityTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView activityTypeIconIV;
        TextView activityTypeTV;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            activityTypeIconIV = itemView.findViewById(R.id.activityTypeIconId);
            activityTypeTV = itemView.findViewById(R.id.activityTypeId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectionCount ==0 ) {
                        activityTypeIconIV.setBackgroundResource(R.drawable.activity_type_selected);
                        //activityTypeIconIV.setImageResource(fitActivityType.getUnselectedImageId());

                        Toast.makeText(itemView.getContext(), "icon selected", Toast.LENGTH_SHORT).show();
                        selectionCount++;
                    } else {
                        Toast.makeText(itemView.getContext(), "already selected", Toast.LENGTH_SHORT).show();

                    }


                }
            });
        }
    }


}
