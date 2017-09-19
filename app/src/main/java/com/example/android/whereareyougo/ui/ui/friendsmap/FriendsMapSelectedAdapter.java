package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

public class FriendsMapSelectedAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;
    private FriendMapSelectedOnClickListener onClickListener;

    public FriendsMapSelectedAdapter(Context context, ArrayList<User> followings, FriendMapSelectedOnClickListener onClickListener) {
        super(followings);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendsMapSelectedHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_followings_selected_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentUser = getItem(position);
        if (currentUser != null) {
            FriendsMapSelectedHolder viewHolder = (FriendsMapSelectedHolder) holder;
            if (currentUser.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageUser);
            } else {
                Glide.with(context)
                        .load(currentUser.getImageUrl())
                        .into(viewHolder.imageUser);
            }

            //
            if (currentUser.getStatus().equals(MyKey.USER_STATUS_OFFLINE)) {
                Glide.with(context)
                        .load(R.drawable.ic_offline)
                        .into(viewHolder.imageUserStatus);
            } else {
                Glide.with(context)
                        .load(R.drawable.ic_online)
                        .into(viewHolder.imageUserStatus);
            }
        }
    }

    public interface FriendMapSelectedOnClickListener {
        void onItemClick(View v, int position);
    }

    public class FriendsMapSelectedHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            onClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
