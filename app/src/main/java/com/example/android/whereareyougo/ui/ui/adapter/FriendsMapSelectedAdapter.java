package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

public class FriendsMapSelectedAdapter extends UltimateViewAdapter<FriendsMapSelectedAdapter.FriendsMapSelectedHolder> {
    private Context context;
    private ArrayList<User> followings;
    private FriendMapSelectedOnClickListener onClickListener;

    public FriendsMapSelectedAdapter(Context context, ArrayList<User> followings,FriendMapSelectedOnClickListener onClickListener) {
        this.context = context;
        this.followings = followings;
        this.onClickListener = onClickListener;
    }

    public interface FriendMapSelectedOnClickListener{
        void onItemClick(View v, int position);
    }

    @Override
    public FriendsMapSelectedHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FriendsMapSelectedHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FriendsMapSelectedHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_followings_selected_row, parent, false);
        return new FriendsMapSelectedHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return followings.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    public void removeItem(int position){
        followings.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(FriendsMapSelectedHolder holder, int position) {
        User currentUser = followings.get(position);
        if (currentUser != null) {
            if (currentUser.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(holder.imageUser);
            } else {
                Glide.with(context)
                        .load(currentUser.getImageUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(holder.imageUser);
            }

            //
            if (currentUser.getStatus().equals(MyKey.USER_STATUS_OFFLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_offline)
                        .override(36,36)
                        .into(holder.imageUserStatus);
            } else {
                Glide.with(context)
                        .load(R.drawable.ic_online)
                        .override(36,36)
                        .into(holder.imageUserStatus);
            }
        }
    }

    public void addFollowing(User user){
        followings.add(user);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class FriendsMapSelectedHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        CircleImageView imageUser;
        CircleImageView imageUserStatus;
        ImageButton buttonDelete;
        ImageButton buttonFindLocation;
        public FriendsMapSelectedHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.image_user);
            imageUserStatus = (CircleImageView) itemView.findViewById(R.id.image_user_status);
            buttonDelete = (ImageButton) itemView.findViewById(R.id.button_delete);
            buttonFindLocation = (ImageButton) itemView.findViewById(R.id.button_find_location);
            //
            buttonDelete.setOnClickListener(this);
            buttonFindLocation.setOnClickListener(this);
            imageUser.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
