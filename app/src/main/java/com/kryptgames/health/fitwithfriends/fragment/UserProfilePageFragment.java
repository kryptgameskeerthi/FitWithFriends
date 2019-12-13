package com.kryptgames.health.fitwithfriends.fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.activity.CreateProfileActivity;
import com.kryptgames.health.fitwithfriends.models.Profile;


import static com.kryptgames.health.fitwithfriends.activity.PhoneAuthenticationActivity.getNumber;


public class UserProfilePageFragment extends Fragment  {

    BlurImageView blurimage;
    ImageView userImage,edit;
    TextView userName,userPhoneNumber,userEmail;
    ImageButton rewardsEarned,missionsCompleted,settings;
    String numberKey=getNumber();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.user_profile_page,container,false);
        blurimage=view.findViewById(R.id.fwf_imageview_background);
        userImage=view.findViewById(R.id.fwf_circleimageview_userimage);
        edit=view.findViewById(R.id.fwf_circleimageview_edit);
        userName=view.findViewById(R.id.fwf_textview_username);
        userPhoneNumber=view.findViewById(R.id.fwf_textview_usercontactnumber);
        userEmail=view.findViewById(R.id.fwf_textview_useremail);
        rewardsEarned=view.findViewById(R.id.fwf_imagebutton_rewards_earned);
        missionsCompleted=view.findViewById(R.id.fwf_imagebutton_missions_completed);
        settings=view.findViewById(R.id.fwf_imagebutton_settings);

        DatabaseReference database=FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref=database.child("Profile").child(numberKey);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    userName.setText((profile.getName()) + " " + (profile.getLastName()));
                    //userEmail.setText(profile.getEmail());
                    //Picasso.with(getContext()).load((profile.getimageRef())).into(userImage);
                    //Picasso.with(getContext()).load((profile.getimageRef())).into(blurimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        blurimage.setBlur(7);
        userPhoneNumber.setText(numberKey);
        userEmail.setText("cody.brown@gmail.com");

        missionsCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompletedMissionsFragment fragment=new CompletedMissionsFragment();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.r2l_slide_in, R.anim.r2l_slide_out, R.anim.l2r_slide_in, R.anim.l2r_slide_out);
                fragmentTransaction.replace(R.id.main_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createProfileIntent=new Intent(getContext(), CreateProfileActivity.class);
                createProfileIntent.putExtra("number",numberKey);
                startActivity(createProfileIntent);
            }
        });

    }
}
