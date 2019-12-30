package com.kryptgames.health.fitwithfriends.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.kryptgames.health.fitwithfriends.activity.InvitePopup;
import com.kryptgames.health.fitwithfriends.models.FriendsInvitation;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.activity.HomeScreenActivity;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;
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

public class InviteFriendsFragment extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ArrayList<String> contacts = new ArrayList<>();
    private List<FriendsInvitation> mlist = new ArrayList<>();
    private TextView selected,total;
    private Button button;
    private int count=0,totalparticipants;
    private String userNumber,missionTitle;
    private ArrayList<InvitePopupPojo> participantsList=new ArrayList<>();
    private ArrayList<String> deviceIds=new ArrayList<>();
    RecyclerViewAdapter adapter;

    public static Fragment newInstance() {

        return new NewMissionsRecyclerFragment();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                readContacts();
            } else {
                Toast.makeText(getContext(), "We cannot display the list of your friends until you grant us the permission", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "please navigate to settings,under app permissions please grant FIT WITH FRIENDS a permission to read your contacts", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        total.setText(""+totalparticipants);
        readContacts();
        for(String number:contacts){
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
            DatabaseReference ref=reference.child("Profile").child(number);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String token = dataSnapshot.child("token").getValue(String.class);
                        String fName = dataSnapshot.child("name").getValue(String.class);
                        String lName = dataSnapshot.child("lastName").getValue(String.class);
                        String image = dataSnapshot.child("imageRef").getValue(String.class);
                        mlist.add(new FriendsInvitation(image, token, fName + " " + lName));
                        adapter.notifyDataSetChanged();

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeScreenActivity)getActivity()).selectTab(0);

                for(FriendsInvitation model : mlist){
                    if(model.isSelected()){

                        participantsList.add(new InvitePopupPojo(model.getUserImageRef(),model.getUserName()));
                        deviceIds.add(model.getTokenId());
                    }
                }

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        adapter=new RecyclerViewAdapter(mlist, getContext());
        recyclerView.setAdapter(adapter);
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
            Picasso.get().load(horizontalList.get(position).getUserImageRef()).into(holder.userImage);
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

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
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
                String userImage=participantsList.get(i).userImageRef;
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
    private void readContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            contacts = getContactNames();

            for (int i = 0; i < contacts.size(); i++) {
                Log.i("contacts", contacts.get(i));
            }

        }
    }

    private ArrayList<String> getContactNames() {
        ArrayList<String> numbers = new ArrayList<>();

        ContentResolver resolver = getContext().getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

            Log.i("My Info", id + "=" + name);

            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneNumber=phoneNumber+"";

                if (phoneNumber.length() > 10) {
                    phoneNumber = phoneNumber.replaceAll("\\s", "");
                    phoneNumber = phoneNumber.replaceAll("-", "");
                    phoneNumber = phoneNumber.replace("+91", "");
                    phoneNumber = phoneNumber.replace("(", "");
                    phoneNumber = phoneNumber.replace(")", "");
                    if (phoneNumber.length() > 10)
                        phoneNumber = phoneNumber.substring(1, 11);

                    else if(phoneNumber.length()==10){
                            if(!numbers.contains(phoneNumber))
                            numbers.add(phoneNumber);}

                }
                else if(phoneNumber.length()==10) {
                    if(!numbers.contains(phoneNumber))
                    numbers.add(phoneNumber);

                    Log.i("My info", phoneNumber);

                }
            }
        }
        return numbers;
    }
}
