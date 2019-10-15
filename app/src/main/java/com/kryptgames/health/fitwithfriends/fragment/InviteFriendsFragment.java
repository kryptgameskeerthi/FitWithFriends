package com.kryptgames.health.fitwithfriends.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.kryptgames.health.fitwithfriends.activity.InvitePopup;
import com.kryptgames.health.fitwithfriends.models.FriendsInvitation;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.activity.HomeScreenActivity;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

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

public class InviteFriendsFragment extends Fragment {
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private List<FriendsInvitation> mlist = new ArrayList<>();
    private TextView selected,total;
    private Button button;
    private int count=0,totalparticipants;
    private String userNumber,missionTitle;
    private ArrayList<InvitePopupPojo> participantsList=new ArrayList<>();
    private ArrayList<String> deviceIds=new ArrayList<>();
    private String DEVICE_TOKEN="dN7bDO-37_E:APA91bFC41FHHEM93NER76E_p3IQTVFPdG__0OYyLx91Fz5gxRVQ_8scyN98bMVfQEIOLUaMy79sEhUWUUwwGRE6dCHd5JeoJIidQnJsJDkIWY_GKq7Iti4Auvqj2Ofd-y6wgJDXA9wu";
    private String two="eJZqOwoJUAY:APA91bFfEkzRfL0i-oD1uD2adYbr6n6BmBNRvIuFiR38wfDxmtmNNSY4n-bnK3WBu_oZLMYDLE1QMbVyDip-g5RyZr5t075oinijpXsPzONZL3O5GpIWvwnhAO_Y0Xqjd0CyhOuYK6R_";

    public static Fragment newInstance() {

        return new NewMissionsRecyclerFragment();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, Do the contacts-related task you need to do.
        } else {
            // permission denied
        }
        return;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS
                );
            }
        } else {
            // Permission has already been granted
        }
        total.setText(""+totalparticipants);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeScreenActivity)getActivity()).selectTab(0);

                for(FriendsInvitation model : mlist){
                    if(model.isSelected()){
                        participantsList.add(new InvitePopupPojo(model.getUserImage(),model.getUserName()));
                    }
                }
                deviceIds.add(DEVICE_TOKEN);
                deviceIds.add(two);

                sendNotification(userNumber,missionTitle,participantsList,deviceIds);

                NewMissionsRecyclerFragment newMissionsRecyclerFragment=new NewMissionsRecyclerFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.r2l_slide_in, R.anim.r2l_slide_out, R.anim.l2r_slide_in, R.anim.l2r_slide_out);
                fragmentTransaction.remove(new InviteFriendsFragment());
                fragmentTransaction.replace(R.id.fwf_layout_fragmentcontainer,newMissionsRecyclerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                Toast.makeText(getContext(),"Mission has been created and added to the list",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button=view.findViewById(R.id.fwf_button_createmission);
        selected=view.findViewById(R.id.fwf_textview_selectedcount);
        total=view.findViewById(R.id.fwf_textview_totalcount);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle=this.getArguments();
        totalparticipants=bundle.getInt("group");
        missionTitle=bundle.getString("title");
        userNumber=getNumber();

        View view = inflater.inflate(R.layout.new_missions_invite_friends, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.fwf_layout_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
        recyclerView.setAdapter(new RecyclerViewAdapter(mlist, getContext()));
        populateList();
        return view;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView userImage;
        private TextView userName;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public RecyclerViewHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.invite_popup_users_details, container, false));
            userImage = itemView.findViewById(R.id.fwf_circleimageview_userimage);
            userName = itemView.findViewById(R.id.fwf_textview_username);
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private List<FriendsInvitation> horizontalList;
        Context context;

        public RecyclerViewAdapter(List<FriendsInvitation> horizontalList, Context context) {
            this.horizontalList = horizontalList;
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
            final FriendsInvitation friendsInvitation = horizontalList.get(position);
            holder.userImage.setImageResource(horizontalList.get(position).getUserImage());
            holder.userName.setText(horizontalList.get(position).getUserName());
            holder.userImage.setForeground(friendsInvitation.isSelected() ? getResources().getDrawable(R.drawable.user_selected_foreground) : null);
            holder.userImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (friendsInvitation.isSelected())
                        count--;
                    else
                        count++;
                    if(count==totalparticipants)
                    {
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        button.setEnabled(true);
                        button.setBackgroundResource(R.drawable.create_mission_button_active);
                    }
                    else {button.setEnabled(false); button.setBackgroundResource(R.drawable.create_mission_button);}
                    selected.setText("" + count);
                    friendsInvitation.setSelected(!friendsInvitation.isSelected());
                    holder.userImage.setForeground(friendsInvitation.isSelected() ? getResources().getDrawable(R.drawable.user_selected_foreground) : null);
                }
            });
        }
        @Override
        public int getItemCount() {
            return horizontalList.size();
        }

    }
        private void populateList() {
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userone"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usertwo"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userthree"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfour"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "userfive"));
            mlist.add(new FriendsInvitation(R.drawable.homepageimage, "usersix"));
    }

    public void sendNotification(String senderNumber, String missionTitle, ArrayList<InvitePopupPojo> participantsList, ArrayList<String> deviceIds){

        final String serverKey="AAAA_EeEzM4:APA91bH_YEU0_plNvWth4WcaOHNSOKCOme5yAz7Hs4QM8578IUt6TQ8Fa14H6ZAOCKT1RDA7TMcxSVMISG1BI0ruzt0SrTpFLUtaOBsxv-gV_iF23NnVkIbKAOFnR1vAuW3oIFo_gu-R";
        JSONObject json1 = new JSONObject();
        JSONArray idsArray = new JSONArray();
        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();
        JSONArray array=new JSONArray();
        String title="Mission Invite";
        String message="You have got a new mission invite";

        try {
            for(int i=0;i<(deviceIds.size());i++) {

                idsArray.put(deviceIds.get(i));
            }

            notification.put("title", title);
            notification.put("body", message);
            notification.put("click_action","HomeScreenActivity");

            data.put("senderNumber",senderNumber);
            data.put("missionTitle",missionTitle);

            json1.put("content_available", true);
            json1.put("priority", "high");
            json1.put("registration_ids",idsArray);
            json1.put("notification", notification);

            for(int i=0;i<(participantsList.size());i++)
            {
                int userImage=participantsList.get(i).userImage;
                String userName=participantsList.get(i).userName;

                JSONObject info=new JSONObject();
                info.put("userImage",userImage);
                info.put("userName",userName);

                array.put(info);

            }

            data.put("userinfo",array);
            json1.put("data", data);

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
