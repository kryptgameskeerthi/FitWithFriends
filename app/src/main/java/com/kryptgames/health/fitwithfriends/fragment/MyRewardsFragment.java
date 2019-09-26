package com.kryptgames.health.fitwithfriends.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.models.MyRewardsPojo;
import com.kryptgames.health.fitwithfriends.R;

import java.util.ArrayList;
import java.util.List;

public class MyRewardsFragment extends Fragment {

    private ArrayList<MyRewardsPojo> mlist = new ArrayList<>();

    public static Fragment newInstance() {

        return new MyRewardsFragment() ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_rewards,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.fwf_layout_myrewardsrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(mlist,getContext()));
        populateList();
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView rewardTitle,useByDate;
        private ImageView rewardImage;
        private Button redeemButton;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.my_rewards_cardview,container,false));
           cardView = itemView.findViewById(R.id.fwf_cardview_myrewards);
           rewardTitle = itemView.findViewById(R.id.fwf_textview_rewardtitle);
           useByDate = itemView.findViewById(R.id.fwf_textview_usebydate);
           rewardImage = itemView.findViewById(R.id.fwf_imageview_rewardimage);
           redeemButton=itemView.findViewById(R.id.fwf_button_redeemnow);
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        private List<MyRewardsPojo> detailsList;
        Context context;
        public RecyclerViewAdapter(List<MyRewardsPojo>detailsList, Context context){
            this.detailsList=detailsList;
            this.context=context;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.useByDate.setText(detailsList.get(position).getUseByDate());
        holder.rewardTitle.setText(detailsList.get(position).getTitle());
        holder.rewardImage.setImageResource(detailsList.get(position).getRewardImage());
        holder.redeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                QRCGeneratorFragment fragment=new QRCGeneratorFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.r2l_slide_in, R.anim.r2l_slide_out, R.anim.l2r_slide_in, R.anim.l2r_slide_out);

                Bundle bundle=new Bundle();
                bundle.putString("title",detailsList.get(position).getTitle());
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.main_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

        }
        @Override
        public int getItemCount() {
            return detailsList.size();
        }
    }
    public void populateList(){

        MyRewardsPojo one = new MyRewardsPojo(R.drawable.homepageimage,"Free beer at zero 40","Oct 20 2018");
        MyRewardsPojo two = new MyRewardsPojo(R.drawable.homepageimage,"Free beer at zero 50","Oct 20 2018");
        MyRewardsPojo three = new MyRewardsPojo(R.drawable.homepageimage,"Free beer at zero 60","Oct 20 2018");
        MyRewardsPojo four = new MyRewardsPojo(R.drawable.homepageimage,"Free beer at zero 70","Oct 20 2018");
        MyRewardsPojo five = new MyRewardsPojo(R.drawable.homepageimage,"Free beer at zero 80","Oct 20 2018");
        mlist.add(one);
        mlist.add(two);
        mlist.add(three);
        mlist.add(four);
        mlist.add(five);
    }
}

