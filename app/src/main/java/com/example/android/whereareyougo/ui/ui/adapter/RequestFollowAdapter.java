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
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class RequestFollowAdapter extends UltimateViewAdapter<RequestFollowAdapter.RequestFollowViewHolder> {
    private Context context;
    private ArrayList<User> requestFollows;
    private MyClickItemListener myClickItemListener;


    public RequestFollowAdapter(Context context, ArrayList<User> requestFollows, MyClickItemListener myClickItemListener) {
        this.context = context;
        this.requestFollows = requestFollows;
        this.myClickItemListener = myClickItemListener;

    }

    public interface MyClickItemListener{
        void onItemClick(View v, int position);
    }

    @Override
    public RequestFollowViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public RequestFollowViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public RequestFollowViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_request_follow_row,parent,false);

        return new RequestFollowViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return requestFollows.size();
    }

    public void removeItem(int position){
        requestFollows.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RequestFollowViewHolder holder, int position) {
        User currentRequest = requestFollows.get(position);
        if (currentRequest != null){
            holder.textSenderName.setText(currentRequest.getName());
            holder.textMessage.setText(context.getString(R.string.text_request_follow));
            if (currentRequest.getImageUrl() == null){
                Glide.with(context)
                     .load(R.drawable.ic_user_default)
                     .into(holder.imageSender);
            }else{
                Glide.with(context)
                     .load(currentRequest.getImageUrl())
                     .into(holder.imageSender);
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

    public class RequestFollowViewHolder extends UltimateRecyclerviewViewHolder implements View.OnClickListener{
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
