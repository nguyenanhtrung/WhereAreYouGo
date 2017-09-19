package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 03/07/2017.
 */

public class UsersRecyclerViewAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;

    public UsersRecyclerViewAdapter(Context context, List<User> users, OnItemClickListener onItemClickListener) {
        super(users);
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_add_friends_row, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentUser = getItem(position);
        if (currentUser != null) {
            UserViewHolder viewHolder = (UserViewHolder) holder;
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
            }

        }
    }

    public interface OnItemClickListener {
        void onButtonClick(View view, int position);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageUser;
        TextView textUserName;
        Button buttonAdd;

        public UserViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textUserName = (TextView) itemView.findViewById(R.id.text_friend_name);
            buttonAdd = (Button) itemView.findViewById(R.id.button_add_friend);
            //itemView.setOnClickListener(this);
            buttonAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onButtonClick(buttonAdd, getAdapterPosition());
        }
    }

}
