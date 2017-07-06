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
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class RequestAddFriendAdapter extends UltimateViewAdapter<RequestAddFriendAdapter.RequestViewHolder> {
    private ArrayList<User> users;
    private Context context;
    private OnClickListener onClickListener;

    public RequestAddFriendAdapter(ArrayList<User> users, Context context,OnClickListener onClickListener) {
        this.users = users;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClickItem(View view, int position);
    }

    @Override
    public RequestViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RequestViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_invitations_row,parent,false);

        return new RequestViewHolder(view);
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
    public void onBindViewHolder(RequestViewHolder holder, int position) {
        User currentUserSender = users.get(position);
        if (currentUserSender != null){
            holder.textSenderName.setText(currentUserSender.getName());
            holder.textMessage.setText(context.getString(R.string.text_message_request_add_friend));
            if (currentUserSender.getImageUrl() == null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageSender);
            }else{
                Glide.with(context)
                     .load(currentUserSender.getImageUrl())
                     .into(holder.imageSender);
            }
        }
    }

    public void removeElement(int position){
        if (users != null){
            users.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class RequestViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        TextView textSenderName;
        TextView textMessage;
        CircleImageView imageSender;
        ImageButton buttonAccept;
        ImageButton buttonCancel;

        public RequestViewHolder(View itemView) {
            super(itemView);
            textSenderName = (TextView) itemView.findViewById(R.id.text_sender_name);
            textMessage = (TextView) itemView.findViewById(R.id.text_message);
            imageSender = (CircleImageView) itemView.findViewById(R.id.cicle_image_sender);
            buttonAccept = (ImageButton) itemView.findViewById(R.id.button_accept);
            buttonCancel = (ImageButton) itemView.findViewById(R.id.button_cancel);
            //
            buttonAccept.setOnClickListener(this);
            buttonCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onClickItem(v,getAdapterPosition());
        }
    }
}
