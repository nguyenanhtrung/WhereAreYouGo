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
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 28/07/2017.
 */

public class UserMessagesRecyclerViewAdapter extends UltimateViewAdapter<UserMessagesRecyclerViewAdapter.UserMessagesViewHolder> {
    private Context context;
    private ArrayList<UserMessage> userMessages;

    public UserMessagesRecyclerViewAdapter(Context context, ArrayList<UserMessage> userMessages) {
        this.context = context;
        this.userMessages = userMessages;
    }

    @Override
    public UserMessagesViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public UserMessagesViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public UserMessagesViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_messages_row,parent,false);
        return new UserMessagesViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return userMessages.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(UserMessagesViewHolder holder, int position) {
        UserMessage userMessage = userMessages.get(position);
        if (userMessage != null){
            if (userMessage.getFriend() != null){
                holder.textFriendName.setText(userMessage.getFriend().getName());
                //
                if (userMessage.getFriend().getImageUrl() == null){
                    Glide.with(context)
                         .load(R.drawable.ic_user_default)
                         .into(holder.imageFriend);
                }else{
                    Glide.with(context)
                         .load(userMessage.getFriend().getImageUrl())
                         .into(holder.imageFriend);
                }
                //
                if (userMessage.getMetaDataChats() != null){
                    holder.textMessage.setText(userMessage.getMetaDataChats().getLastMessage());
                    holder.textTimestamp.setText(userMessage.getMetaDataChats().getTimeStamp());

                }
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

    public static class  UserMessagesViewHolder extends UltimateRecyclerviewViewHolder{
        CircleImageView imageFriend;
        TextView textFriendName;
        TextView textMessage;
        TextView textTimestamp;
        TextView textMessageStatus;
        ImageButton imageButtonDelete;

        public UserMessagesViewHolder(View itemView) {
            super(itemView);
            imageFriend = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textFriendName = (TextView) itemView.findViewById(R.id.text_friend_name);
            textMessage = (TextView) itemView.findViewById(R.id.text_message);
            textTimestamp = (TextView) itemView.findViewById(R.id.text_message_date);
            textMessageStatus = (TextView) itemView.findViewById(R.id.text_message_status);
            imageButtonDelete = (ImageButton) itemView.findViewById(R.id.image_button_delete);

        }
    }
}
