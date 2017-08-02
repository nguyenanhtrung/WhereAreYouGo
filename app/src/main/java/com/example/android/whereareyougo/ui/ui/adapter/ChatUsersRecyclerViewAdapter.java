package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 01/08/2017.
 */

public class ChatUsersRecyclerViewAdapter extends UltimateViewAdapter<ChatUsersRecyclerViewAdapter.ChatUsersViewHolder> {
    private Context context;
    private ArrayList<ChatUser> chatUsers;
    private ChatUserClickListener chatUserClickListener;


    public ChatUsersRecyclerViewAdapter(Context context, ArrayList<ChatUser> chatUsers, ChatUserClickListener chatUserClickListener) {
        this.context = context;
        this.chatUsers = chatUsers;
        this.chatUserClickListener = chatUserClickListener;
    }

    public interface ChatUserClickListener{
        void onClickChatUserItem(View view, int position);
    }

    @Override
    public ChatUsersViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public ChatUsersViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public ChatUsersViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_chatuser_row,parent,false);

        return new ChatUsersViewHolder(view);
    }
    @Override
    public int getAdapterItemCount() {
        return chatUsers.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(ChatUsersViewHolder holder, int position) {
        ChatUser chatUser = chatUsers.get(position);
        if (chatUser != null){
            if(chatUser.getMessageNotification() == 0){
                holder.textBadgeNumber.setVisibility(View.INVISIBLE);
            }else{
                holder.textBadgeNumber.setVisibility(View.VISIBLE);
                holder.textBadgeNumber.setText(String.valueOf(chatUser.getMessageNotification()));
            }



            //
            if (chatUser.getUserInfo().getImageUrl() ==  null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageUser);
            }else{
                Glide.with(context)
                     .load(chatUser.getUserInfo().getImageUrl())
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .into(holder.imageUser);
            }

            if (chatUser.isCurrentUser()){
                holder.imageUser.setBorderColor(ContextCompat.getColor(context,R.color.primary));
            }else{
                holder.imageUser.setBorderColor(ContextCompat.getColor(context,R.color.md_white_1000));
            }
        }
    }

    public void addChatUser(ChatUser chatUser){
        if (chatUser != null){
            chatUsers.add(chatUser);
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

    public class ChatUsersViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
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
            chatUserClickListener.onClickChatUserItem(v,getAdapterPosition());

        }
    }
}
