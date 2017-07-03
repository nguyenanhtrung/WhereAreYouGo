package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 03/07/2017.
 */

public class UsersRecyclerViewAdapter extends UltimateViewAdapter<UsersRecyclerViewAdapter.UserViewHolder> {
    private Context context;
    private List<User> users;
    private OnItemClickListener onItemClickListener;

    public UsersRecyclerViewAdapter(Context context, List<User> users, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.users = users;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onButtonClick(View view);
    }

    @Override
    public UserViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public UserViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_add_friends_row,parent,false);
        return new UserViewHolder(view);
    }

    public void swapDatas(List<User> newDatas){
        users.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemCount() {
        return users.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        if(currentUser != null){
            holder.textUserName.setText(currentUser.getName());
            //
            if (currentUser.getImageUrl() == null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageUser);
            }else{
                Glide.with(context)
                     .load(currentUser.getImageUrl())
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

    public class UserViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        CircleImageView imageUser;
        TextView textUserName;
        BootstrapButton buttonAdd;
        public UserViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textUserName = (TextView) itemView.findViewById(R.id.text_friend_name);
            buttonAdd = (BootstrapButton) itemView.findViewById(R.id.button_add_friend);
            //itemView.setOnClickListener(this);
            buttonAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onButtonClick(buttonAdd);
        }
    }
}
