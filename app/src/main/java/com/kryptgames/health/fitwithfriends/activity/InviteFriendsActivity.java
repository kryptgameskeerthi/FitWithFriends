package com.kryptgames.health.fitwithfriends.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kryptgames.health.fitwithfriends.InviteFriendsPojo;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.InviteFriendsAdapter;

import java.util.ArrayList;

public class InviteFriendsActivity extends AppCompatActivity implements InviteFriendsAdapter.ItemClickListener {
    int i=0,MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private RecyclerView detailsRecyclerView;
    private InviteFriendsAdapter detailsAdapter;
    ImageView imageView;
    TextView selected,total;
    private Button button;
    ArrayList<InviteFriendsPojo> gridList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.l2r_slide_in, R.anim.l2r_slide_out);
    }
    @Override
    public void onItemClick(int a) {

        selected=findViewById(R.id.fwf_textview_selectedcount);
        selected.setText(""+a);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.r2l_slide_in, R.anim.r2l_slide_out);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},MY_PERMISSIONS_REQUEST_READ_CONTACTS
                );
            }
        } else {
            // Permission has already been granted
        }

        setContentView(R.layout.new_missions_invite_friends);
        detailsRecyclerView = findViewById(R.id.fwf_layout_recyclerview);
        detailsAdapter = new InviteFriendsAdapter(gridList,getApplicationContext(),this);
        detailsRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        detailsRecyclerView.setAdapter(detailsAdapter);
        populatedetailsList();

        button = findViewById(R.id.fwf_button_createmission);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"mission created and added to the list",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populatedetailsList() {
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userone"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usertwo"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userthree"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfour"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "userfive"));
        gridList.add(new InviteFriendsPojo(R.drawable.homepageimage, "usersix"));
    }
}
