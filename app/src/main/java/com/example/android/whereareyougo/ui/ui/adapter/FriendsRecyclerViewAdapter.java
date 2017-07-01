package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class FriendsRecyclerViewAdapter extends UltimateViewAdapter<FriendsRecyclerViewAdapter.FriendViewHolder> {
    private Context context;
    private List<User> friends;

    public FriendsRecyclerViewAdapter(Context context, List<User> friends) {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public FriendViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FriendViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_friends_row,parent);

        return new FriendViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return friends.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }



    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        User currentFriend = friends.get(position);
        if (currentFriend != null){
            holder.textFriendName.setText(currentFriend.getName());
            holder.textFriendStatus.setText(currentFriend.getStatus());
            Glide.with(context)
                 .load(currentFriend.getImageUrl())
                 .into(holder.imageFriend);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class FriendViewHolder extends UltimateRecyclerviewViewHolder{
        CircleImageView imageFriend;
        TextView textFriendName;
        TextView textFriendStatus;
        public FriendViewHolder(View itemView) {
            super(itemView);
            imageFriend = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textFriendName = (TextView) itemView.findViewById(R.id.text_friend_name);
            textFriendStatus = (TextView) itemView.findViewById(R.id.text_friend_status);
        }
    }
}
