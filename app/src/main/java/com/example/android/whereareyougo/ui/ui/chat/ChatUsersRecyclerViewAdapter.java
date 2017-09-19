package com.example.android.whereareyougo.ui.ui.chat;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 01/08/2017.
 */

public class ChatUsersRecyclerViewAdapter extends BaseRecyclerViewAdapter<ChatUser, RecyclerView.ViewHolder> {
    private Context context;
    private ChatUserClickListener chatUserClickListener;


    public ChatUsersRecyclerViewAdapter(Context context, ArrayList<ChatUser> chatUsers, ChatUserClickListener chatUserClickListener) {
        super(chatUsers);
        this.context = context;
        this.chatUserClickListener = chatUserClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatUsersViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_chatuser_row, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatUser chatUser = getItem(position);
        if (chatUser != null) {
            ChatUsersViewHolder viewHolder = (ChatUsersViewHolder) holder;
            if (chatUser.getMessageNotification() == 0) {
                viewHolder.textBadgeNumber.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.textBadgeNumber.setVisibility(View.VISIBLE);
                viewHolder.textBadgeNumber.setText(String.valueOf(chatUser.getMessageNotification()));
            }

            //
            if (chatUser.getUserInfo().getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageUser);
            } else {
                Glide.with(context)
                        .load(chatUser.getUserInfo().getImageUrl())
                        .into(viewHolder.imageUser);
            }

            if (chatUser.isCurrentUser()) {
                viewHolder.imageUser.setBorderColor(ContextCompat.getColor(context, R.color.primary));
            } else {
                viewHolder.imageUser.setBorderColor(ContextCompat.getColor(context, R.color.md_white_1000));
            }
        }
    }

    public interface ChatUserClickListener {
        void onClickChatUserItem(View view, int position);
    }

    public class ChatUsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageUser;
        TextView textBadgeNumber;

        public ChatUsersViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.circle_user_image);
            textBadgeNumber = (TextView) itemView.findViewById(R.id.text_badge);
            //
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            chatUserClickListener.onClickChatUserItem(v, getAdapterPosition());

        }
    }

}
