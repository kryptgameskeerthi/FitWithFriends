package com.kryptgames.health.fitwithfriends;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InviteFriendsAdapter extends RecyclerView.Adapter<InviteFriendsAdapter.DetailsViewHolder>{
    private List<InviteFriendsPojo> horizontalList;
    Context context;
    private ItemClickListener mClickListener;
    TextView selected,total;

    public InviteFriendsAdapter(List<InviteFriendsPojo> horizontalList, Context context){
        this.horizontalList= horizontalList;
        this.context = context;
    }

    @Override
    public DetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_popup_users_details, parent, false);
        DetailsViewHolder dvh = new DetailsViewHolder(productView);
        return dvh;
    }

    @Override
    public void onBindViewHolder(DetailsViewHolder holder, final int position) {
        final InviteFriendsPojo inviteFriendsPojo=horizontalList.get(position);
        holder.imageView.setImageResource(horizontalList.get(position).getUserImage());
        holder.txtview.setText(horizontalList.get(position).getUserName());
        holder.imageView.setAlpha(inviteFriendsPojo.isSelected() ? 0.6f : 1);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteFriendsPojo.setSelected(!inviteFriendsPojo.isSelected());
                holder.imageView.setAlpha(inviteFriendsPojo.isSelected() ? 0.6f : 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView txtview;
        public DetailsViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.fwf_circleimageview_userimage);
            txtview=view.findViewById(R.id.fwf_textview_username);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}