package com.example.android.whereareyougo.ui.ui.main;

import android.app.Activity;
import android.location.Location;

import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
//import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MainView extends MvpView {

    void updateUserInfo(User user);

    void closeUserSettingDrawer();

    void openUserSettingDrawer();

    void openUserSettingFragment();

    void openUserListFavoriteVenueFragment();

    void openListFriendFragment();

    void openNotificationsFragment();

    void setRequestAddFriends(ArrayList<User> userRequests);

    void updateBadgeNotification(int badge);

    void resetBadgeNotification();

    void openSearchVenueFragment();

    void setRequestFollows(ArrayList<User> requestFollows);

    void setRequestFollowBadge(int requestFollowBadge);

    void setRequestAddFriendBadge(int requestAddFriendBadge);

    void openFollowersFragment();

    void openAppSettingFragment();

    ArrayList<String> messageNotifications();

    Activity getActivity();

    void setCurrentUserLocation(Location currentUserLocation);

}
