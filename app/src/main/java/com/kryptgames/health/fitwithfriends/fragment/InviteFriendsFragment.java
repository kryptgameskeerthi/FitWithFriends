package com.kryptgames.health.fitwithfriends.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
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

import com.kryptgames.health.fitwithfriends.models.FriendsInvitation;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.activity.HomeScreenActivity;

import java.util.ArrayList;
import java.util.List;

public class InviteFriendsFragment extends Fragment {
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private List<FriendsInvitation> mlist = new ArrayList<>();
    private TextView selected,total;
    private Button button;
    private int count=0,totalparticipants;

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
}
