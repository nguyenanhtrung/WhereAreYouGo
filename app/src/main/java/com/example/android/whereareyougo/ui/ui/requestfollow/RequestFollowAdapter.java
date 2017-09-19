package com.example.android.whereareyougo.ui.ui.requestfollow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class RequestFollowAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;
    private MyClickItemListener myClickItemListener;


    public RequestFollowAdapter(Context context, ArrayList<User> requestFollows, MyClickItemListener myClickItemListener) {
        super(requestFollows);
        this.context = context;
        this.myClickItemListener = myClickItemListener;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RequestFollowViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_request_follow_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentRequest = getItem(position);
        if (currentRequest != null) {
            RequestFollowViewHolder requestHolder = (RequestFollowViewHolder) holder;
            requestHolder.textSenderName.setText(currentRequest.getName());
            requestHolder.textMessage.setText(context.getString(R.string.text_request_follow));
            if (currentRequest.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(requestHolder.imageSender);
            } else {
                Glide.with(context)
                        .load(currentRequest.getImageUrl())
                        .into(requestHolder.imageSender);
            }
        }
    }

    public interface MyClickItemListener {
        void onItemClick(View v, int position);
    }

    public class RequestFollowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textSenderName;
        TextView textMessage;
        CircleImageView imageSender;
        ImageButton buttonAccept;
        ImageButton buttonCancel;

        public RequestFollowViewHolder(View itemView) {
            super(itemView);
            textMessage = (TextView) itemView.findViewById(R.id.text_message);
            textSenderName = (TextView) itemView.findViewById(R.id.text_sender_name);
            imageSender = (CircleImageView) itemView.findViewById(R.id.cicle_image_sender);
            buttonAccept = (ImageButton) itemView.findViewById(R.id.button_accept);
            buttonCancel = (ImageButton) itemView.findViewById(R.id.button_cancel);
            //
            buttonCancel.setOnClickListener(this);
            buttonAccept.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myClickItemListener.onItemClick(v, getAdapterPosition());
        }
    }

}
