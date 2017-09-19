package com.example.android.whereareyougo.ui.ui.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by nguyenanhtrung on 17/07/2017.
 */

public class ChatMessagesAdapter extends BaseRecyclerViewAdapter<ChatMessage, RecyclerView.ViewHolder> {
    private Context context;
    private String currentUserId;

    public ChatMessagesAdapter(Context context, String currentUserId, List<ChatMessage> chatMessages) {
        super(chatMessages);
        this.context = context;
        this.currentUserId = currentUserId;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChatMessagesViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_messages_left, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = getItem(position);
        if (chatMessage != null) {
            ChatMessagesViewHolder viewHolder = (ChatMessagesViewHolder) holder;
            //set user image
            if (chatMessage.getSenderImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageUser);
            } else {
                Glide.with(context)
                        .load(chatMessage.getSenderImageUrl())
                        .into(viewHolder.imageUser);
            }
            //set user  message
            if (chatMessage.getTypeMessage().equals(MyKey.TEXT_TYPE_MESSAGE)) {
                viewHolder.textUserMessage.setVisibility(View.VISIBLE);
                viewHolder.imageUserMessage.setVisibility(View.GONE);
                viewHolder.textUserMessage.setText(chatMessage.getMessage());

            } else if (chatMessage.getTypeMessage().equals(MyKey.IMAGE_TYPE_MESSAGE)) {
                viewHolder.textUserMessage.setVisibility(View.GONE);
                viewHolder.imageUserMessage.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(chatMessage.getMessage())
                        .into(viewHolder.imageUserMessage);
            }

            //set timestamp (set later)
            viewHolder.textTimeStamp.setVisibility(View.VISIBLE);
            viewHolder.textTimeStamp.setText(chatMessage.getTimeStamp());

        }
    }

    public class ChatMessagesViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageUser;
        EmojiconTextView textUserMessage;
        ImageView imageUserMessage;
        TextView textTimeStamp;

        public ChatMessagesViewHolder(View itemView) {
            super(itemView);
            imageUser = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textUserMessage = (EmojiconTextView) itemView.findViewById(R.id.text_user_message);
            imageUserMessage = (ImageView) itemView.findViewById(R.id.image_user_message);
            textTimeStamp = (TextView) itemView.findViewById(R.id.text_message_timestamp);
        }
    }
}
