package com.example.android.whereareyougo.ui.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.messages.MessagesFragment;
import com.example.android.whereareyougo.ui.ui.notify.NotifyFragment;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendFragment;
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowFragment;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotificationsFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private Context context;
    private ArrayList<User> userRequests;
    private ArrayList<User> requestFollows;
    public NotificationsFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<User>
                                             userRequests, ArrayList<User> requestFollows) {
        super(fm);
        this.context = context;
        this.userRequests = userRequests;
        this.requestFollows = requestFollows;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RequestAddFriendFragment.newInstance(userRequests);
            case 1:
                return MessagesFragment.newInstance();
            case 2:
                return NotifyFragment.newInstance();
            case 3:
                return ListRequestFollowFragment.newInstance(requestFollows);
            default:
                break;
        }

        return null;
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
