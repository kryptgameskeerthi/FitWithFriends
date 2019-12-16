package com.kryptgames.health.fitwithfriends.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.adapters.RecyclerViewHorizontalListAdapter;
import com.kryptgames.health.fitwithfriends.models.Profile;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.kryptgames.health.fitwithfriends.activity.PhoneAuthenticationActivity.getNumber;

public class InvitePopup extends AppCompatDialogFragment {

    private ArrayList<InvitePopupPojo> detailsList = new ArrayList<>();
    private RecyclerView detailsRecyclerView;
    private RecyclerViewHorizontalListAdapter detailsAdapter;
    private ImageView hostImage,participantImage;
    private TextView hostName,activityTitle,participantName;
    private Button positive,negative;
    private String userNumber,missionTitle,tokenId,currentUserName;
    private String currentUserNumber=getNumber();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_invite_popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);


        hostName=dialog.findViewById(R.id.fwf_textview_username);
        hostImage=dialog.findViewById(R.id.fwf_imageview_userimage);
        activityTitle=dialog.findViewById(R.id.fwf_textview_missiontitle);

        Bundle bundle=getArguments();
        userNumber=bundle.getString("userNumber");
        missionTitle=bundle.getString("missionTitle");
        detailsList=bundle.getParcelableArrayList("userinfo");


        detailsRecyclerView = dialog.findViewById(R.id.fwf_layout_recycler_view);
        detailsAdapter = new RecyclerViewHorizontalListAdapter(detailsList,getContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        detailsRecyclerView.setLayoutManager(horizontalLayoutManager);
        detailsRecyclerView.setAdapter(detailsAdapter);

        activityTitle.setText(missionTitle);

        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref=database.child("Profile").child(userNumber);
        DatabaseReference ref1=database.child("Profile").child(currentUserNumber);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserName=dataSnapshot.child("name").getValue(String.class)+" "+dataSnapshot.child("lastName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                hostName.setText((profile.getName()) + " " + (profile.getLastName()));
                Picasso.get().load((dataSnapshot.child("imageRef").getValue(String.class))).into(hostImage);
                tokenId=dataSnapshot.child("token").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        positive = dialog.findViewById(R.id.fwf_button_accept);
        negative=dialog.findViewById(R.id.fwf_button_reject);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //should increase the notification count on the mission creator homepage, mission needs to be added to the user list
                sendNotification(currentUserName,"accepted",tokenId);
                dialog.dismiss();

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creator of the mission has to be notified
                sendNotification(currentUserName,"rejected",tokenId);
                dialog.dismiss();
            }
        });
       return dialog;
    }



    public void sendNotification(String name,String response,String token){

        final String serverKey="AAAA_EeEzM4:APA91bH_YEU0_plNvWth4WcaOHNSOKCOme5yAz7Hs4QM8578IUt6TQ8Fa14H6ZAOCKT1RDA7TMcxSVMISG1BI0ruzt0SrTpFLUtaOBsxv-gV_iF23NnVkIbKAOFnR1vAuW3oIFo_gu-R";
        JSONObject json1 = new JSONObject();
        JSONObject notification = new JSONObject();
        String title="Mission Invite Response";
        String message=name+" "+response+" your mission invite";

        try {

            notification.put("title", title);
            notification.put("body", message);
            notification.put("click_action","HomeScreenActivity");

            json1.put("content_available", true);
            json1.put("priority", "high");
            json1.put("to",token);
            json1.put("notification", notification);


        }catch (JSONException e){
            System.out.println(e.getMessage());
        }
        String pushMessage=json1.toString();

        String url = "https://fcm.googleapis.com/fcm/send";
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, pushMessage);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "key="+serverKey)
                .post(body)
                .build();
        Callback responseCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.v("Fail Message", "fail");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("response", response.toString());
            }
        };
        okhttp3.Call call = client.newCall(request);
        call.enqueue(responseCallBack);
    }

}
