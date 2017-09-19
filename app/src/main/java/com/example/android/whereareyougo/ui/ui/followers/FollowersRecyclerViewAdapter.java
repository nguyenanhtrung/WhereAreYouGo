package com.example.android.whereareyougo.ui.ui.followers;

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
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public class FollowersRecyclerViewAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;

    private MyItemClickListener myItemClickListener;


    public FollowersRecyclerViewAdapter(Context context, List<User> followers, MyItemClickListener myItemClickListener) {
        super(followers);
        this.context = context;
        this.myItemClickListener = myItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowersViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_followers_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User follower = getItem(position);
        if (follower != null) {
            FollowersViewHolder viewHolder = (FollowersViewHolder) holder;
            viewHolder.textFollowerName.setText(follower.getName());
            //
            if (follower.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageFollower);
            } else {
                Glide.with(context)
                        .load(follower.getImageUrl())
                        .into(viewHolder.imageFollower);
            }
        }
    }


    public interface MyItemClickListener {
        void onItemClick(View v, int position);
    }

    public class FollowersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            myItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
