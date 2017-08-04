package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.utils.BuilderManager;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class FriendsRecyclerViewAdapter extends UltimateViewAdapter<FriendsRecyclerViewAdapter.FriendViewHolder> {
    private Context context;
    private List<User> friends;
    private List<User> friendsCopy;
    private OnClickBoomButtonListener onClickBoomButtonListener;


    public interface OnClickBoomButtonListener {
        void onButtonClick(int index, BoomButton boomButton, int position);
    }

    public FriendsRecyclerViewAdapter(Context context, List<User> friends, OnClickBoomButtonListener onClickBoomButtonListener) {
        this.context = context;
        this.friends = friends;
        friendsCopy = new ArrayList<>();
        friendsCopy.addAll(friends);
        this.onClickBoomButtonListener = onClickBoomButtonListener;

    }

    public void filterByName(String name){
        friends.clear();
        if (name.isEmpty()){
            friends.addAll(friendsCopy);
        }else{
            name = name.toLowerCase();
            for(User user : friendsCopy){
                if (user.getName().toLowerCase().contains(name)){
                    friends.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public FriendViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public FriendViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_friends_row, parent, false);

        return new FriendViewHolder(view);
    }

    @Override
    public int getAdapterItemCount() {
        return friends.size();
    }

    public void removeItem(int position){
        if (!friends.isEmpty()){
            friends.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }


    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        User currentFriend = friends.get(position);
        if (currentFriend != null) {
            holder.textFriendName.setText(currentFriend.getName());
            if (currentFriend.getStatus() != null) {
                if (currentFriend.getStatus().equals("OFFLINE")) {
                    holder.imageFriendStatus.setImageResource(R.drawable.ic_offline);
                } else if (currentFriend.getStatus().equals("ONLINE")) {
                    holder.imageFriendStatus.setImageResource(R.drawable.ic_online);
                }
            }

            if (currentFriend.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(holder.imageFriend);
            } else {
                Glide.with(context)
                        .load(currentFriend.getImageUrl())
                        .into(holder.imageFriend);
            }



            holder.buttonChoose.clearBuilders();
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.follow, R.drawable.ic_follow));
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_unfriend, R.drawable.ic_unfriend_24dp));
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_chat, R.drawable.ic_chat));
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.title_profile_dialog, R.drawable.ic_see_profile));
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_current_location, R.drawable.ic_location));
            holder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.button_call, R.drawable.ic_call_white_24dp));
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class FriendViewHolder extends UltimateRecyclerviewViewHolder implements OnBoomListener {
        CircleImageView imageFriend;
        TextView textFriendName;
        CircleImageView imageFriendStatus;
        BoomMenuButton buttonChoose;

        public FriendViewHolder(View itemView) {
            super(itemView);
            imageFriend = (CircleImageView) itemView.findViewById(R.id.circle_image_friend);
            textFriendName = (TextView) itemView.findViewById(R.id.text_friend_name);
            imageFriendStatus = (CircleImageView) itemView.findViewById(R.id.image_friend_status);
            buttonChoose = (BoomMenuButton) itemView.findViewById(R.id.boom_button_choose);
            buttonChoose.setOnBoomListener(this);



        }


        @Override
        public void onClicked(int index, BoomButton boomButton) {
            onClickBoomButtonListener.onButtonClick(index,boomButton,getAdapterPosition());
        }

        @Override
        public void onBackgroundClick() {

        }

        @Override
        public void onBoomWillHide() {

        }

        @Override
        public void onBoomDidHide() {

        }

        @Override
        public void onBoomWillShow() {

        }

        @Override
        public void onBoomDidShow() {

        }


    }
}
