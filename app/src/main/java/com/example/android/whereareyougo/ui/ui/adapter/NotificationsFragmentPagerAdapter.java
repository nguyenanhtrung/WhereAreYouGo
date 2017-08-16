package com.example.android.whereareyougo.ui.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.messages.MessagesFragment;
import com.example.android.whereareyougo.ui.ui.notify.NotifyFragment;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendFragment;
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotificationsFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private Context context;
    private ArrayList<User> userRequests;
    private ArrayList<User> requestFollows;
    private ArrayList<String> messageNotifications;
    public NotificationsFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<User>
                                             userRequests, ArrayList<User> requestFollows,
                                             ArrayList<String> messageNotifications) {
        super(fm);
        this.context = context;
        this.userRequests = userRequests;
        this.requestFollows = requestFollows;
        this.messageNotifications = messageNotifications;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RequestAddFriendFragment.newInstance(userRequests);
            case 1:
                return MessagesFragment.newInstance(messageNotifications);
            case 2:
                return NotifyFragment.newInstance();
            case 3:
                return ListRequestFollowFragment.newInstance(requestFollows);
            default:
                break;
        }

        return null;
    }

    public View getTabView(int position, int badge) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_notification, null);
        TextView textTabName = (TextView) v.findViewById(R.id.text_tab_name);
        textTabName.setText(getPageTitle(position));
        TextView badgeNumber = (TextView) v.findViewById(R.id.text_badge_number);
        if (badge == 0){
            badgeNumber.setVisibility(View.INVISIBLE);
        }else{
            badgeNumber.setVisibility(View.VISIBLE);
            badgeNumber.setText(String.valueOf(badge));
        }

        return v;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.text_tab_invite);
            case 1:
                return context.getString(R.string.text_tab_message);
            case 2:
                return context.getString(R.string.text_tab_notification);
            case 3:
                return context.getString(R.string.text_tab_request_follows);
            default:
                break;
        }

        return null;
    }
}
