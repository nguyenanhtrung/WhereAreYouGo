package com.example.android.whereareyougo.ui.ui.requestaddfriend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class RequestAddFriendAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {

    private Context context;
    private OnClickListener onClickListener;

    public RequestAddFriendAdapter(ArrayList<User> users, Context context, OnClickListener onClickListener) {
        super(users);
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RequestViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_add_friends_row, parent, false));

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemCount() == 0) {
            Log.d(MyKey.NOTIFICATIONS_FRAGMENT_TAG, "Empty");
            RequestViewHolder requestHolder = (RequestViewHolder) holder;
            requestHolder.textSenderName.setText("Empty");
        }
        User currentUserSender = getItem(position);
        if (currentUserSender != null) {
            RequestViewHolder requestHolder = (RequestViewHolder) holder;
            requestHolder.textSenderName.setText(currentUserSender.getName());
            requestHolder.textMessage.setText(context.getString(R.string.text_message_request_add_friend));
            if (currentUserSender.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(requestHolder.imageSender);
            } else {
                Glide.with(context)
                        .load(currentUserSender.getImageUrl())
                        .into(requestHolder.imageSender);
            }
        }
    }


    public interface OnClickListener {
        void onClickItem(View view, int position);
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            onClickListener.onClickItem(v, getAdapterPosition());
        }
    }


}
