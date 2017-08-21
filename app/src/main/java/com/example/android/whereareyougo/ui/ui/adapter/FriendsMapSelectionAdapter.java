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
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapSelectionAdapter extends UltimateViewAdapter<FriendsMapSelectionAdapter.FriendMapSelectionViewHolder> {
    private Context context;
    private ArrayList<User> followings;
    private FriendsMapClickListener friendsMapClickListener;

    public FriendsMapSelectionAdapter(Context context, ArrayList<User> followings, FriendsMapClickListener friendsMapClickListener) {
        this.context = context;
        this.followings = followings;
        this.friendsMapClickListener = friendsMapClickListener;
    }

    public interface FriendsMapClickListener{
        void onClickItem(View view, int position);
    }

    @Override
    public FriendMapSelectionViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FriendMapSelectionViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FriendMapSelectionViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_friendsmap_selection_row, parent, false);

        return new FriendMapSelectionViewHolder(view);
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
    public void onBindViewHolder(FriendMapSelectionViewHolder holder, int position) {
        User currentUser = followings.get(position);
        if (currentUser != null) {
            holder.textUserName.setText(currentUser.getName());
            //
            if (currentUser.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .fitCenter()
                        .into(holder.imageUser);
            } else {
                Glide.with(context)
                        .load(currentUser.getImageUrl())
                        .fitCenter()
                        .into(holder.imageUser);
            }//

            if (currentUser.getStatus().equals(MyKey.USER_STATUS_ONLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_online)
                        .override(36,36)
                        .into(holder.imageUserStatus);
            } else if (currentUser.getStatus().equals(MyKey.USER_STATUS_OFFLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_offline)
                        .override(36,36)
                        .into(holder.imageUserStatus);
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

    public class FriendMapSelectionViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener {
        CircleImageView imageUser;
        TextView textUserName;
        CircleImageView imageUserStatus;

        public FriendMapSelectionViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.image_following);
            textUserName = (TextView) itemView.findViewById(R.id.text_following_name);
            imageUserStatus = (CircleImageView) itemView.findViewById(R.id.image_user_status);
            //
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            friendsMapClickListener.onClickItem(v,getAdapterPosition());
        }
    }
}
