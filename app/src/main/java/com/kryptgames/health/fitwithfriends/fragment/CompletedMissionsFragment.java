package com.kryptgames.health.fitwithfriends.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.adapters.RecyclerViewHorizontalListAdapter;
import com.kryptgames.health.fitwithfriends.models.CompletedMissionsVerticalPojo;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

import java.util.ArrayList;
import java.util.List;


public class CompletedMissionsFragment extends Fragment {

private ArrayList<CompletedMissionsVerticalPojo> mlist = new ArrayList<>();
private ArrayList<InvitePopupPojo> nlist =new ArrayList<>();
private ArrayList<BarEntry> entries=new ArrayList<>();
private ArrayList<String > labels=new ArrayList<>();
private TextView title;
private int checkedPosition=-1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.new_missions_recycler_view,container,false);
        title=view.findViewById(R.id.fwf_textview_title);
        title.setText(R.string.completedmissions_title);
        RecyclerView recyclerView=view.findViewById(R.id.fwf_layout_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(mlist,getContext()));
        populateListHorizontal();
        getData();
        getLabels();
        populateList();
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView title,goal,contribution;
        private ProgressBar pGoal,pContribution;
        private RecyclerView hrecyclerView;
        private RelativeLayout expandableLayout;
        private BarChart chart;
        private ImageView rewardImage;
        private ImageButton more;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.completed_missions_cardview,container,false));
            cardView = itemView.findViewById(R.id.fwf_cardview_completedmissions);
            title = itemView.findViewById(R.id.fwf_textview_missiontitle);
            goal = itemView.findViewById(R.id.fwf_textview_percentageone);
            contribution = itemView.findViewById(R.id.fwf_textview_percentagetwo);
            hrecyclerView=itemView.findViewById(R.id.fwf_recyclerview_completedmissionscontributors);
            expandableLayout=itemView.findViewById(R.id.fwf_layout_relative_expandable);
            chart=itemView.findViewById(R.id.fwf_barchart);
            rewardImage=itemView.findViewById(R.id.fwf_imageview_rewardimage);
            more=itemView.findViewById(R.id.fwf_imagebutton_cardexpand);
            pGoal=itemView.findViewById(R.id.fwf_progressbar_goalcompleted);
            pContribution=itemView.findViewById(R.id.fwf_progressbar_mycontribution);
        }
    }

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<CompletedMissionsVerticalPojo> detailsList;
    Context context;

    public RecyclerViewAdapter(List<CompletedMissionsVerticalPojo> detailsList, Context context) {
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
        final CompletedMissionsVerticalPojo completedMissionsVerticalPojo=detailsList.get(position);
        holder.title.setText(detailsList.get(position).getTitle());
        holder.goal.setText(""+detailsList.get(position).getGoal());
        holder.pGoal.setProgress(detailsList.get(position).getGoal());
        holder.contribution.setText(""+detailsList.get(position).getContribution());
        holder.pContribution.setProgress(detailsList.get(position).getContribution());
        RecyclerViewHorizontalListAdapter recyclerViewHorizontalListAdapter=new RecyclerViewHorizontalListAdapter(detailsList.get(position).getList(),context);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        holder.hrecyclerView.setLayoutManager(horizontalLayoutManager);
        holder.hrecyclerView.setAdapter(recyclerViewHorizontalListAdapter);
        holder.expandableLayout.setVisibility(completedMissionsVerticalPojo.isSelected()? View.VISIBLE : View.GONE);
        holder.rewardImage.setImageResource(detailsList.get(position).getReward());

           BarDataSet barDataSet=new BarDataSet(detailsList.get(position).getTeamMissionInfo(),"");
           barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
           IndexAxisValueFormatter formatter1=new IndexAxisValueFormatter(getValues());
           barDataSet.setValueTextSize(12);
           barDataSet.setValueFormatter(formatter1);
           BarData barData=new BarData(barDataSet);
           barData.setBarWidth(0.95f);
           holder.chart.setData(barData);
           XAxis xAxis=holder.chart.getXAxis();
           xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
           xAxis.setEnabled(true);
           xAxis.setXOffset(48);
           xAxis.setDrawGridLines(false);
           xAxis.setDrawAxisLine(false);
           xAxis.setTextColor(getResources().getColor(R.color.snow));
           xAxis.setTextSize(14);
           IndexAxisValueFormatter formatter=new IndexAxisValueFormatter(detailsList.get(position).getUsers());
           holder.chart.getAxisRight().setAxisMinimum(0);
           holder.chart.getAxisLeft().setAxisMinimum(0);
           xAxis.setValueFormatter(formatter);
           holder.chart.setVisibleXRangeMaximum(6);
           holder.chart.setDrawValueAboveBar(true);
           holder.chart.moveViewTo(0,(detailsList.get(position).getTeamMissionInfo().size())-1, YAxis.AxisDependency.LEFT);
           holder.chart.getAxisRight().setEnabled(false);
           holder.chart.getAxisLeft().setEnabled(false);
           holder.chart.getDescription().setEnabled(false);
           holder.chart.getLegend().setEnabled(false);
           holder.chart.setDoubleTapToZoomEnabled(false);
           holder.chart.setFitBars(true);
           holder.chart.invalidate();

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completedMissionsVerticalPojo.setSelected(!completedMissionsVerticalPojo.isSelected());
                holder.expandableLayout.setVisibility(completedMissionsVerticalPojo.isSelected()? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public int getItemCount(){
        return detailsList.size();
    }
    }

    public void populateList(){
        CompletedMissionsVerticalPojo one=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",81,50,nlist,getData(),labels,R.drawable.homepageimage);
        CompletedMissionsVerticalPojo two=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",74,54,nlist,entries,labels,R.drawable.homepageimage);
        CompletedMissionsVerticalPojo three=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",96,23,nlist,entries,labels,R.drawable.homepageimage);
        CompletedMissionsVerticalPojo four=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",65,57,nlist,entries,labels,R.drawable.homepageimage);
        CompletedMissionsVerticalPojo five=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",75,67,nlist,entries,labels,R.drawable.homepageimage);
        CompletedMissionsVerticalPojo six=new CompletedMissionsVerticalPojo("Run 100 km in 1 week",85,86,nlist,entries,labels,R.drawable.homepageimage);
        mlist.add(one);
        mlist.add(two);
        mlist.add(three);
        mlist.add(four);
        mlist.add(five);
        mlist.add(six);
    }

    public void populateListHorizontal(){
        InvitePopupPojo one = new InvitePopupPojo(R.drawable.homepageimage, "userone");
        InvitePopupPojo two = new InvitePopupPojo(R.drawable.homepageimage, "usertwo");
        InvitePopupPojo three = new InvitePopupPojo(R.drawable.homepageimage, "userthree");
        InvitePopupPojo four = new InvitePopupPojo(R.drawable.homepageimage, "userfour");
        nlist.add(one);
        nlist.add(two);
        nlist.add(three);
        nlist.add(four);
        nlist.add(one);
        nlist.add(two);
        nlist.add(three);
        nlist.add(four);
    }

    private ArrayList getData(){
        entries.add(new BarEntry(0f, 35));
        entries.add(new BarEntry(1f, 25));
        entries.add(new BarEntry(2f, 12));
        entries.add(new BarEntry(3f, 32));
        entries.add(new BarEntry(4f, 15));
        entries.add(new BarEntry(5f, 24));
        entries.add(new BarEntry(6f, 11));
        entries.add(new BarEntry(7f, 23));
        entries.add(new BarEntry(8f, 37));
        entries.add(new BarEntry(9f, 22));
        entries.add(new BarEntry(10f, 16));
        entries.add(new BarEntry(11f, 35));
        return entries;
    }
    private ArrayList getLabels(){
        labels.add("Virat");
        labels.add("Haley");
        labels.add("Scott");
        labels.add("Kate");
        labels.add("Angel");
        labels.add("Helina");
        labels.add("Jack");
        labels.add("Turner");
        labels.add("Stone");
        labels.add("Kate");
        labels.add("Willson");
        labels.add("Me");
        return labels;
    }

    private ArrayList getValues(){
        ArrayList<String> values=new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            values.add(i+"km");
        }
        return values;
    }
}
