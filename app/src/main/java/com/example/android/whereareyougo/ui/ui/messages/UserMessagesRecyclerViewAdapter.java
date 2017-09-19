package com.example.android.whereareyougo.ui.ui.messages;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 28/07/2017.
 */

public class UserMessagesRecyclerViewAdapter extends BaseRecyclerViewAdapter<UserMessage, RecyclerView.ViewHolder> {
    private Context context;
    private HashMap<String, Boolean> messageNotificationMap;
    private MyClickItemListener myClickItemListener;

    public UserMessagesRecyclerViewAdapter(Context context, ArrayList<UserMessage> userMessages, HashMap<String, Boolean> messageNotificationMap,
                                           MyClickItemListener myClickItemListener) {
        super(userMessages);
        this.context = context;
        this.messageNotificationMap = messageNotificationMap;
        this.myClickItemListener = myClickItemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserMessagesViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_messages_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserMessage userMessage = getItem(position);
        if (userMessage != null) {
            if (userMessage.getFriend() != null) {
                UserMessagesViewHolder viewHolder = (UserMessagesViewHolder) holder;
                viewHolder.textFriendName.setText(userMessage.getFriend().getName());
                //
                if (userMessage.getFriend().getImageUrl() == null) {
                    Glide.with(context)
                            .load(R.drawable.ic_user_default)
                            .into(viewHolder.imageFriend);
                } else {
                    Glide.with(context)
                            .load(userMessage.getFriend().getImageUrl())
                            .into(viewHolder.imageFriend);
                }
                //
                if (userMessage.getMetaDataChats() != null) {
                    viewHolder.textMessage.setText(userMessage.getMetaDataChats().getLastMessage());
                    viewHolder.textTimestamp.setText(userMessage.getMetaDataChats().getTimeStamp());

                }
                //
                if (messageNotificationMap != null) {
                    Log.d(MyKey.MESSAGES_FRAGMENT_TAG, "FriendId = " + userMessage.getFriend().getUserID());
                    if (messageNotificationMap.containsKey(userMessage.getFriend().getUserID())) {
                        viewHolder.textMessageStatus.setText(R.string.text_not_read_message);
                        viewHolder.textMessageStatus.setTextColor(ContextCompat.getColor(context, R.color.primary));
                    } else {
                        viewHolder.textMessageStatus.setText(R.string.text_already_read_message);
                        viewHolder.textMessageStatus.setTextColor(ContextCompat.getColor(context, R.color.colorSecondaryText));

                    }
                }
            }
        }
    }


    public interface MyClickItemListener {
        void onClickItem(View v, int position);
    }

    public class UserMessagesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            myClickItemListener.onClickItem(v, getAdapterPosition());
        }
    }

}
