package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public class FollowingsRecyclerViewAdapter extends UltimateViewAdapter<FollowingsRecyclerViewAdapter.FollowingsViewHolder> {
    private Context context;
    private ArrayList<User> followings;

    public FollowingsRecyclerViewAdapter(Context context, ArrayList<User> followings) {
        this.context = context;
        this.followings = followings;
    }

    @Override
    public FollowingsViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FollowingsViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FollowingsViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_followings_row, parent, false);

        return new FollowingsViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return followings.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(FollowingsViewHolder holder, int position) {
        User currentFollowing = followings.get(position);
        if (currentFollowing != null){
            holder.textUserName.setText(currentFollowing.getName());
            if (currentFollowing.getImageUrl() == null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageUser);
            }else{
                Glide.with(context)
                     .load(currentFollowing.getImageUrl())
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .into(holder.imageUser);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class FollowingsViewHolder extends UltimateRecyclerviewViewHolder {
        CircleImageView imageUser;
        TextView textUserName;

        public FollowingsViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.image_following);
            textUserName = (TextView) itemView.findViewById(R.id.text_following_name);
        }
    }
}
