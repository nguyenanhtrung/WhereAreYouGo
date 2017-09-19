package com.example.android.whereareyougo.ui.ui.followingselection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapSelectionAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;
    private FriendsMapClickListener friendsMapClickListener;

    public FriendsMapSelectionAdapter(Context context, ArrayList<User> followings, FriendsMapClickListener friendsMapClickListener) {
        super(followings);
        this.context = context;
        this.friendsMapClickListener = friendsMapClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendMapSelectionViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_friendsmap_selection_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentUser = getItem(position);
        if (currentUser != null) {
            FriendMapSelectionViewHolder viewHolder = (FriendMapSelectionViewHolder) holder;
            viewHolder.textUserName.setText(currentUser.getName());
            //
            if (currentUser.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageUser);
            } else {
                Glide.with(context)
                        .load(currentUser.getImageUrl())
                        .into(viewHolder.imageUser);
            }//

            if (currentUser.getStatus().equals(MyKey.USER_STATUS_ONLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_online)
                        .into(viewHolder.imageUserStatus);
            } else if (currentUser.getStatus().equals(MyKey.USER_STATUS_OFFLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_offline)
                        .into(viewHolder.imageUserStatus);
            }
        }
    }

    public interface FriendsMapClickListener {
        void onClickItem(View view, int position);
    }

    public class FriendMapSelectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            friendsMapClickListener.onClickItem(v, getAdapterPosition());
        }
    }

}
