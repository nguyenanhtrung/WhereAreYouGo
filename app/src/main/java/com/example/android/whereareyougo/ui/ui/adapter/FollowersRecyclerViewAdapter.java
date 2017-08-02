package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public class FollowersRecyclerViewAdapter extends UltimateViewAdapter<FollowersRecyclerViewAdapter.FollowersViewHolder> {
    private Context context;
    private ArrayList<User> followers;
    private MyItemClickListener myItemClickListener;


    public FollowersRecyclerViewAdapter(Context context, ArrayList<User> followers, MyItemClickListener myItemClickListener) {
        this.context = context;
        this.followers = followers;
        this.myItemClickListener = myItemClickListener;
    }

    public interface MyItemClickListener{
        void onItemClick(View v, int position);
    }

    @Override
    public FollowersViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FollowersViewHolder newHeaderHolder(View view) {
        return null;
    }

    public void removeItem(int position){
        if (!followers.isEmpty()){
            followers.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public FollowersViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_followers_row,parent,false);

        return new FollowersViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return followers.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(FollowersViewHolder holder, int position) {
        User follower = followers.get(position);
        if (follower != null){
            holder.textFollowerName.setText(follower.getName());
            //
            if (follower.getImageUrl() == null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageFollower);
            }else{
                Glide.with(context)
                        .load(follower.getImageUrl())
                        .into(holder.imageFollower);
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

    public class FollowersViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        CircleImageView imageFollower;
        TextView textFollowerName;
        ImageButton buttonUnfollow;

        public FollowersViewHolder(View itemView) {
            super(itemView);
            imageFollower = (CircleImageView) itemView.findViewById(R.id.image_follower);
            textFollowerName = (TextView) itemView.findViewById(R.id.text_follower_name);
            buttonUnfollow = (ImageButton) itemView.findViewById(R.id.button_unfollow);
            //
            buttonUnfollow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
