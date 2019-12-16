package com.kryptgames.health.fitwithfriends.fragment;


import com.kryptgames.health.fitwithfriends.adapters.RecyclerViewHorizontalListAdapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;
import com.kryptgames.health.fitwithfriends.activity.ExcerciseTrackActivity;
import com.kryptgames.health.fitwithfriends.activity.MissionListDetails;

import java.util.ArrayList;
import java.util.List;

public class RecyclerFragment extends Fragment {

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ArrayList<BarEntry> entries =new ArrayList<>();
    ArrayList<BarEntry> list = new ArrayList<>();
    private ArrayList<MissionListDetails> mlist = new ArrayList<>();
    private ArrayList<InvitePopupPojo> nList = new ArrayList<>();

    Handler handler;
    Runnable runnable;


    public static Fragment newInstance() {
        return new RecyclerFragment();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(mlist, getContext()));
        //populateListHorizontal();
        populateList();
        getData();
        getList();
        return view;

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {


        private ProgressBar progressBar;
        private TextView percentage, goal, duration, contributors, titleMenu;
        private ImageButton iconAdd, menu;
        private RecyclerView imageRecyclerView;
        private int progressStatus = 0;
        private Handler handler = new Handler();
        private CardView mCardView;
        private HorizontalBarChart mChart;
        RelativeLayout relativeExpandable;



        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);


        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.card_view, container, false));

            mCardView = itemView.findViewById(R.id.card_container);
            progressBar = itemView.findViewById(R.id.progressBar);
            percentage = itemView.findViewById(R.id.percentage);
            goal = itemView.findViewById(R.id.goal);
            duration = itemView.findViewById(R.id.duration);
            contributors = itemView.findViewById(R.id.contributors);
            iconAdd = itemView.findViewById(R.id.iconAdd);
            imageRecyclerView = itemView.findViewById(R.id.imageRecyclerView);
            titleMenu = itemView.findViewById(R.id.titleMenu);
            menu = itemView.findViewById(R.id.menu);
            mChart = itemView.findViewById(R.id.mChart);
            relativeExpandable = itemView.findViewById(R.id.relativeExpandable);

            // Start long running operation in a background thread
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressStatus);
                                percentage.setText("" + progressStatus);
                                goal.setText("" + progressStatus + "%" + " " + "Completed");
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private ArrayList<MissionListDetails> horizontalList;
        Context context;

        public RecyclerViewAdapter(ArrayList<MissionListDetails> detailsList, Context context) {
            this.horizontalList = detailsList;
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {

            holder.titleMenu.setText(horizontalList.get(position).getTitle());
            holder.goal.setText(horizontalList.get(position).getGoal());
            holder.duration.setText(horizontalList.get(position).getDuration());
            RecyclerViewHorizontalListAdapter myAdapter = new RecyclerViewHorizontalListAdapter(nList, context);
            holder.imageRecyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            holder.imageRecyclerView.setAdapter(myAdapter);

            BarDataSet barDataSet=new BarDataSet(horizontalList.get(position).getInfo(),"");
            barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
            IndexAxisValueFormatter formatter1=new IndexAxisValueFormatter(getValues());
            barDataSet.setValueTextSize(12);
            barDataSet.setValueFormatter(formatter1);
            BarData barData=new BarData(barDataSet);
            barData.setBarWidth(0.95f);
            holder.mChart.setData(barData);
            XAxis xAxis=holder.mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
            xAxis.setEnabled(true);
            xAxis.setXOffset(48);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setTextColor(getResources().getColor(R.color.snow));
            xAxis.setTextSize(14);
            IndexAxisValueFormatter formatter=new IndexAxisValueFormatter(horizontalList.get(position).getUsers());
            holder.mChart.getAxisRight().setAxisMinimum(0);
            holder.mChart.getAxisLeft().setAxisMinimum(0);
            xAxis.setValueFormatter(formatter);
            holder.mChart.setVisibleXRangeMaximum(6);
            holder.mChart.setDrawValueAboveBar(true);
            holder.mChart.moveViewTo(0,(horizontalList.get(position).getInfo().size())-1, YAxis.AxisDependency.LEFT);
            holder.mChart.getAxisRight().setEnabled(false);
            holder.mChart.getAxisLeft().setEnabled(false);
            holder.mChart.getDescription().setEnabled(false);
            holder.mChart.getLegend().setEnabled(false);
            holder.mChart.setDoubleTapToZoomEnabled(false);
            holder.mChart.setFitBars(true);
            holder.mChart.setPinchZoom(false);
            holder.mChart.setScaleEnabled(false);
            holder.mChart.invalidate();
            //holder.mChart.animateXY(10000, 10000);


            holder.menu.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (holder.relativeExpandable.isShown()){

                        holder.relativeExpandable.setVisibility(View.GONE);

                    } else {

                        holder.relativeExpandable.setVisibility(View.VISIBLE);
                    }



                }
            });



            holder.iconAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ExcerciseTrackActivity.class);
                    context.startActivity(intent);



                }
            });




            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }



        @Override
        public int getItemCount() {
            return mlist.size();
        }
    }


    private void populateList() {

        MissionListDetails one = new MissionListDetails("67% completed","6 days to go",entries,getUsers(),"Run 50 km in 12 days",nList);
        MissionListDetails two = new MissionListDetails("87% Completed", "8 days to go",  getList(),getUsers(),"Run 100 km in eight days",nList);
        MissionListDetails three = new MissionListDetails("43% Completed", "14 days to go", getData(), getUsers(),"Run 200 km in two weeks",nList);
        MissionListDetails four = new MissionListDetails("55% Completed", "30 days to go", getData(), getUsers(),"Run 500 km in one month",nList);
        MissionListDetails five = new MissionListDetails("22% Completed", "21 days to go", getData(), getUsers(), "Run 200 km in three weeks",nList);
        mlist.add(one);
        mlist.add(two);
        mlist.add(three);
        mlist.add(four);
        mlist.add(five);
    }

   /* private void populateListHorizontal(){
        InvitePopupPojo one = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        InvitePopupPojo two = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        InvitePopupPojo three = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        InvitePopupPojo four = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        InvitePopupPojo five = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        InvitePopupPojo six = new InvitePopupPojo(R.drawable.harryw,"Harry P");
        nList.add(one);
        nList.add(two);
        nList.add(three);
        nList.add(four);
        nList.add(five);
        nList.add(six);


    }*/



    private ArrayList<BarEntry> getData() {
        entries.add(new BarEntry(0f, 1));
        entries.add(new BarEntry(1f, 4));
        entries.add(new BarEntry(2f, 7));
        entries.add(new BarEntry(3f, 5));
        entries.add(new BarEntry(4f, 6));
        entries.add(new BarEntry(5f, 9));
        entries.add(new BarEntry(6f, 6));
        entries.add(new BarEntry(7f, 4));
        entries.add(new BarEntry(8f, 7));
        entries.add(new BarEntry(9f, 5));
        entries.add(new BarEntry(10f, 6));
        entries.add(new BarEntry(11f, 9));
        entries.add(new BarEntry(12f, 9));
        return entries;


    }

    private ArrayList<BarEntry> getList(){


        list.add(new BarEntry(0f, 10));
        list.add(new BarEntry(1f, 6));
        list.add(new BarEntry(2f, 3));
        list.add(new BarEntry(3f, 7));
        list.add(new BarEntry(4f, 9));
        list.add(new BarEntry(5f, 10));
        list.add(new BarEntry(6f, 10));
        list.add(new BarEntry(7f, 10));
        list.add(new BarEntry(8f, 10));
        list.add(new BarEntry(8f, 10));
        list.add(new BarEntry(10f, 10));
        list.add(new BarEntry(11f, 10));
        list.add(new BarEntry(12f, 10));
        return list;

    }






    private ArrayList<String> getUsers(){

        ArrayList<String> users = new ArrayList<>();
        users.add("Tony Padilla");
        users.add("Hannah Baker");
        users.add("Clay Jensen");
        users.add("Alex Standall");
        users.add("Jessica Davis");
        users.add("Justin Foley");
        users.add("Tyler Down");
        users.add("Bryce Walker");
        users.add("Zach Dempsey");
        users.add("Courtney Crimsen");
        users.add("Ani Achola");
        users.add("Caleb");
        users.add(" Marcus Cole");
        return users;

    }

    private ArrayList<String> getValues() {
        ArrayList<String> values = new ArrayList<>();
        for (int i = 0; i <100; i ++){

            values.add(i + "km");
        }
        return values;

    }





}

