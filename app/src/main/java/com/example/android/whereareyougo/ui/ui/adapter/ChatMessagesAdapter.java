package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by nguyenanhtrung on 17/07/2017.
 */

public class ChatMessagesAdapter extends UltimateViewAdapter<ChatMessagesAdapter.ChatMessagesViewHolder> {
    private Context context;
    private List<ChatMessage> chatMessages;
    private String currentUserId;

    public ChatMessagesAdapter(Context context, String currentUserId, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
        this.currentUserId = currentUserId;
    }

    @Override
    public ChatMessagesViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ChatMessagesViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ChatMessagesViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_messages_left,parent,false);
        return new ChatMessagesViewHolder(view);
    }


    @Override
    public ChatMessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(viewType,parent,false);
        return new ChatMessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (chatMessages.get(position).getSenderId().equals(currentUserId)){
            return R.layout.recyclerview_messages_left;
        }else{
            return  R.layout.recyclerview_messages_right;
        }

    }

    public void addItem(ChatMessage chatMessage){
        chatMessages.add(chatMessage);
        notifyDataSetChanged();
    }

    @Override
    public int getAdapterItemCount() {
        return chatMessages.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ChatMessagesViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (chatMessage != null){
            //set user image
            if (chatMessage.getSenderImageUrl() == null){
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(holder.imageUser);
            }else{
                Glide.with(context)
                     .load(chatMessage.getSenderImageUrl())
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .into(holder.imageUser);
            }
            //set user  message
            if (chatMessage.getTypeMessage().equals(MyKey.TEXT_TYPE_MESSAGE)){
                holder.textUserMessage.setVisibility(View.VISIBLE);
                holder.imageUserMessage.setVisibility(View.GONE);
                holder.textUserMessage.setText(chatMessage.getMessage());

            }else if (chatMessage.getTypeMessage().equals(MyKey.IMAGE_TYPE_MESSAGE)){
                holder.textUserMessage.setVisibility(View.GONE);
                holder.imageUserMessage.setVisibility(View.VISIBLE);
                Glide.with(context)
                     .load(chatMessage.getMessage())
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .override(250,150)
                     .into(holder.imageUserMessage);
            }

            //set timestamp (set later)
            holder.textTimeStamp.setVisibility(View.VISIBLE);
            holder.textTimeStamp.setText(chatMessage.getTimeStamp());

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class  ChatMessagesViewHolder extends UltimateRecyclerviewViewHolder{
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
