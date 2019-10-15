package com.kryptgames.health.fitwithfriends.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.models.NewMissionsPojo;
import com.kryptgames.health.fitwithfriends.R;

import java.util.ArrayList;
import java.util.List;

public class NewMissionsRecyclerFragment extends Fragment {

    private RecyclerViewAdapter recyclerViewAdapter;
    private List<NewMissionsPojo> mlist=new ArrayList<>();
    private int checkedPosition=-1;
    private TextView title;

    public static Fragment newInstance() {

        return new NewMissionsRecyclerFragment() ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_missions_recycler_view,container,false);
        title=view.findViewById(R.id.fwf_textview_title);
        title.setText("Select Mission");
        RecyclerView recyclerView=view.findViewById(R.id.fwf_layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(mlist,getContext()));
        populateList();
        return view;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView header,km,days,frnds;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater,ViewGroup container){

            super(inflater.inflate(R.layout.new_missions_card_view,container,false));

            cardView = itemView.findViewById(R.id.fwf_layout_newmissions_cardview);
            header = itemView.findViewById(R.id.fwf_textview_missioninfo);
            km = itemView.findViewById(R.id.fwf_textview_kilometers_digits);
            days = itemView.findViewById(R.id.fwf_textview_days_digits);
            frnds = itemView.findViewById(R.id.fwf_textview_friends_digits);

        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

        private List<NewMissionsPojo> detailsList;
        Context context;
        public RecyclerViewAdapter(List<NewMissionsPojo>detailsList, Context context){
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
            if(checkedPosition==-1){
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.snow));}
            else
            {
                if(checkedPosition==position)
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.sage));
                else
                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.snow));

            }

            holder.header.setText(detailsList.get(position).getTitle());
            holder.km.setText(""+detailsList.get(position).distance);
            holder.frnds.setText(""+detailsList.get(position).group);
            holder.days.setText(""+detailsList.get(position).duration);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.sage));
                    if (checkedPosition != position) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = position;
                    }



                    Fragment fragment=RewardsRecyclerFragment.newInstance();
                    FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.r2l_slide_in, R.anim.r2l_slide_out, R.anim.l2r_slide_in, R.anim.l2r_slide_out);

                    Bundle bundle=new Bundle();
                    bundle.putInt("group",detailsList.get(position).group);
                    bundle.putString("title",detailsList.get(position).title);
                    fragment.setArguments(bundle);

                    fragmentTransaction.replace(R.id.fwf_layout_fragmentcontainer,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    notifyDataSetChanged();


                }
            });

        }

        @Override
        public int getItemCount() {
            return detailsList.size();
        }
    }
    private void populateList() {
        NewMissionsPojo one=new NewMissionsPojo("Run 50 km in four days",50,4,5);
        NewMissionsPojo two = new NewMissionsPojo("Run 100 km in eight days",100,8,7);
        NewMissionsPojo three= new NewMissionsPojo("Run 200 km in two weeks",200,14,10);
        NewMissionsPojo four =new NewMissionsPojo("Run 500 km in one month",500,30,15);
        NewMissionsPojo five = new NewMissionsPojo("Run 200 km in three weeks",200,21,7);
        mlist.add(one);
        mlist.add(two);
        mlist.add(three);
        mlist.add(four);
        mlist.add(five);
    }
}
