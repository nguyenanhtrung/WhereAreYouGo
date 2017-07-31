package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 28/07/2017.
 */

public class UserMessagesRecyclerViewAdapter extends UltimateViewAdapter<UserMessagesRecyclerViewAdapter.UserMessagesViewHolder> {
    private Context context;
    private ArrayList<UserMessage> userMessages;
    private HashMap<String, Boolean> messageNotificationMap;
    private MyClickItemListener myClickItemListener;

    public UserMessagesRecyclerViewAdapter(Context context, ArrayList<UserMessage> userMessages, HashMap<String,Boolean> messageNotificationMap,
                                           MyClickItemListener myClickItemListener) {
        this.context = context;
        this.userMessages = userMessages;
        this.messageNotificationMap = messageNotificationMap;
        this.myClickItemListener = myClickItemListener;
    }

    public interface MyClickItemListener{
        void onClickItem(View v, int position);
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
                //
                if (messageNotificationMap != null){
                    Log.d(MyKey.MESSAGES_FRAGMENT_TAG,"FriendId = " + userMessage.getFriend().getUserID());
                    if (messageNotificationMap.containsKey(userMessage.getFriend().getUserID())){
                        holder.textMessageStatus.setText(R.string.text_not_read_message);
                        holder.textMessageStatus.setTextColor(ContextCompat.getColor(context,R.color.primary));
                    }else{
                        holder.textMessageStatus.setText(R.string.text_already_read_message);
                        holder.textMessageStatus.setTextColor(ContextCompat.getColor(context,R.color.colorSecondaryText));

                    }
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

    public class  UserMessagesViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
        CircleImageView imageFriend;
        TextView textFriendName;
        TextView textMessage;
        TextView textTimestamp;
        TextView textMessageStatus;

        public UserMessagesViewHolder(View itemView) {
            super(itemView);
            imageFriend = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textFriendName = (TextView) itemView.findViewById(R.id.text_friend_name);
            textMessage = (TextView) itemView.findViewById(R.id.text_message);
            textTimestamp = (TextView) itemView.findViewById(R.id.text_message_date);
            textMessageStatus = (TextView) itemView.findViewById(R.id.text_message_status);
            //
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myClickItemListener.onClickItem(v,getAdapterPosition());
        }
    }
}
