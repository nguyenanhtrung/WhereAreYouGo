package com.example.android.whereareyougo.ui.ui.listfriend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.utils.BuilderManager;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class FriendsRecyclerViewAdapter extends BaseRecyclerViewAdapter<User, RecyclerView.ViewHolder> {
    private Context context;
    private List<User> friendsCopy;
    private OnClickBoomButtonListener onClickBoomButtonListener;


    public FriendsRecyclerViewAdapter(Context context, List<User> friends, OnClickBoomButtonListener onClickBoomButtonListener) {
        super(friends);
        this.context = context;
        friendsCopy = new ArrayList<>();
        friendsCopy.addAll(friends);
        this.onClickBoomButtonListener = onClickBoomButtonListener;

    }

    public void filterByName(String name) {
        clear();
        if (name.isEmpty()) {
            addAllItem(friendsCopy);
        } else {
            name = name.toLowerCase();
            for (User user : friendsCopy) {
                if (user.getName().toLowerCase().contains(name)) {
                    addItem(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_friends_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User currentFriend = getItem(position);
        if (currentFriend != null) {
            FriendViewHolder viewHolder = (FriendViewHolder) holder;
            viewHolder.textFriendName.setText(currentFriend.getName());
            if (currentFriend.getStatus() != null) {
                if (currentFriend.getStatus().equals("OFFLINE")) {
                    viewHolder.imageFriendStatus.setImageResource(R.drawable.ic_offline);
                } else if (currentFriend.getStatus().equals("ONLINE")) {
                    viewHolder.imageFriendStatus.setImageResource(R.drawable.ic_online);
                }
            }

            if (currentFriend.getImageUrl() == null) {
                Glide.with(context)
                        .load(R.drawable.ic_user_default)
                        .into(viewHolder.imageFriend);
            } else {
                Glide.with(context)
                        .load(currentFriend.getImageUrl())
                        .into(viewHolder.imageFriend);
            }


            viewHolder.buttonChoose.clearBuilders();
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.follow, R.drawable.ic_follow));
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_unfriend, R.drawable.ic_unfriend_24dp));
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_chat, R.drawable.ic_chat));
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.title_profile_dialog, R.drawable.ic_see_profile));
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.text_current_location, R.drawable.ic_location));
            viewHolder.buttonChoose.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder(R.string.button_call, R.drawable.ic_call_white_24dp));
        }
    }

    public interface OnClickBoomButtonListener {
        void onButtonClick(int index, BoomButton boomButton, int position);
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder implements OnBoomListener {
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
            onClickBoomButtonListener.onButtonClick(index, boomButton, getAdapterPosition());
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
