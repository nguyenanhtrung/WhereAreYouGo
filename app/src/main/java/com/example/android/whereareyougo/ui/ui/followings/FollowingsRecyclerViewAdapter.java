package com.example.android.whereareyougo.ui.ui.followings;

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
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public class FollowingsRecyclerViewAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;

    public FollowingsRecyclerViewAdapter(Context context, ArrayList<User> followings) {
        super(followings);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new FollowingsViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.recyclerview_followings_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentFollowing = getItem(position);
        if (currentFollowing != null) {
            FollowingsViewHolder viewHolder = (FollowingsViewHolder) holder;
            viewHolder.textUserName.setText(currentFollowing.getName());
            if (currentFollowing.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageUser);
            } else {
                Glide.with(context)
                        .load(currentFollowing.getImageUrl())
                        .into(viewHolder.imageUser);
            }
        }
    }

    public class FollowingsViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageUser;
        TextView textUserName;

        public FollowingsViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.image_following);
            textUserName = (TextView) itemView.findViewById(R.id.text_following_name);
        }
    }

}
