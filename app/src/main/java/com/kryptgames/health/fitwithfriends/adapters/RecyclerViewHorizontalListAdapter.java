package com.kryptgames.health.fitwithfriends.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kryptgames.health.fitwithfriends.R;
import com.kryptgames.health.fitwithfriends.models.InvitePopupPojo;

import java.util.List;

public class RecyclerViewHorizontalListAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapter.DetailsViewHolder>{
    private List<InvitePopupPojo> horizontalList;
    Context context;

    public RecyclerViewHorizontalListAdapter(List<InvitePopupPojo> horizontalList, Context context){
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
        holder.imageView.setImageResource(horizontalList.get(position).getUserImage());
        holder.txtview.setText(horizontalList.get(position).getUserName());



    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        public DetailsViewHolder(View view) {
            super(view);
            imageView=view.findViewById(R.id.fwf_circleimageview_userimage);
            txtview=view.findViewById(R.id.fwf_textview_username);
        }
    }
}
