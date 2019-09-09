package com.kryptgames.health.fitwithfriends;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.activity.CalenderActivity;

import java.util.ArrayList;
import java.util.List;

public class RewardsRecyclerFragment extends Fragment {
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<RewardsPojo> mlist=new ArrayList<>();
    private int checkedPosition=-1;

    public static Fragment newInstance() {
        return new RewardsRecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_missions_recycler_view,container,false);

        RecyclerView recyclerView=view.findViewById(R.id.fwf_layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RewardsRecyclerFragment.RecyclerViewAdapter(mlist,getContext()));
        populateList();
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.new_missions_rewards,container,false));
            cardView = itemView.findViewById(R.id.fwf_cardview_rewards);
            imageView= itemView.findViewById(R.id.fwf_imageview_rewardimage);
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RewardsRecyclerFragment.RecyclerViewHolder>{

        private List<RewardsPojo> detailsList;
        Context context;
        public RecyclerViewAdapter(List<RewardsPojo>detailsList, Context context){
            this.detailsList=detailsList;
            this.context=context;
        }
        @NonNull
        @Override
        public RewardsRecyclerFragment.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RewardsRecyclerFragment.RecyclerViewHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RewardsRecyclerFragment.RecyclerViewHolder holder, int position) {

            if(checkedPosition==-1){
                holder.cardView.setAlpha(1);
                holder.imageView.setAlpha(1f);}
            else
            {
                if(checkedPosition==position)
                {holder.cardView.setAlpha(0.6f);
                holder.imageView.setAlpha(0.6f);}
                else
                {holder.cardView.setAlpha(1);
                holder.imageView.setAlpha(1f);}

            }

            holder.imageView.setImageResource(detailsList.get(position).getImage());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.cardView.setAlpha(0.6f);
                    holder.imageView.setAlpha(0.6f);
                    if (checkedPosition != position) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = position;
                    }
                    Intent intent=new Intent(getContext(), CalenderActivity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return detailsList.size();
        }
    }

    private void populateList() {
        RewardsPojo one= new RewardsPojo(R.drawable.homepageimage);
        RewardsPojo two= new RewardsPojo(R.drawable.homepageimage);
        RewardsPojo three= new RewardsPojo(R.drawable.homepageimage);
        RewardsPojo four= new RewardsPojo(R.drawable.homepageimage);
        RewardsPojo five= new RewardsPojo(R.drawable.homepageimage);
        RewardsPojo six= new RewardsPojo(R.drawable.homepageimage);
        mlist.add(one);
        mlist.add(two);
        mlist.add(three);
        mlist.add(four);
        mlist.add(five);
        mlist.add(six);
    }
}

