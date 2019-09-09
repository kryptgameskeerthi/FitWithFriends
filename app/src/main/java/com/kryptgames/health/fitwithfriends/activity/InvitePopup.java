package com.kryptgames.health.fitwithfriends.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.InvitePopupPojo;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.RecyclerViewHorizontalListAdapter;

import java.util.ArrayList;
import java.util.List;

public class InvitePopup extends AppCompatDialogFragment {

    private List<InvitePopupPojo> detailsList = new ArrayList<>();
    private RecyclerView detailsRecyclerView;
    private RecyclerViewHorizontalListAdapter detailsAdapter;
    private ImageView hostPicture,participantImage;
    private TextView hostName,activityTitle,participantName;
    private Button positive,negative;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_invite_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        detailsRecyclerView = dialog.findViewById(R.id.fwf_layout_recycler_view);
        detailsAdapter = new RecyclerViewHorizontalListAdapter(detailsList,getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        detailsRecyclerView.setLayoutManager(horizontalLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);
        populatedetailsList();

        positive = dialog.findViewById(R.id.fwf_button_accept);
        negative=dialog.findViewById(R.id.fwf_button_reject);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //should increase the notification count on the mission creator homepage, mission needs to be added to the user list
                Toast.makeText(getContext(),"You have approved the request",Toast.LENGTH_SHORT).show();

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creator of the mission has to be notified
                Toast.makeText(getContext(),"You have rejected the request",Toast.LENGTH_SHORT).show();
            }
        });
       return dialog;
    }

    private void populatedetailsList() {
        InvitePopupPojo one = new InvitePopupPojo(R.drawable.homepageimage, "userone");
        InvitePopupPojo two = new InvitePopupPojo(R.drawable.homepageimage, "usertwo");
        InvitePopupPojo three = new InvitePopupPojo(R.drawable.homepageimage, "userthree");
        InvitePopupPojo four = new InvitePopupPojo(R.drawable.homepageimage, "userfour");
        detailsList.add(one);
        detailsList.add(two);
        detailsList.add(three);
        detailsList.add(four);
        detailsList.add(one);
        detailsList.add(two);
        detailsList.add(three);
        detailsList.add(four);
    }

}
