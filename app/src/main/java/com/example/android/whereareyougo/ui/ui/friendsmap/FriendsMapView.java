package com.example.android.whereareyougo.ui.ui.friendsmap;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public interface FriendsMapView extends MvpView {
    void addFollowing(String followingId);

    void openFollowingsSelectionDialog();

    ArrayList<User> getFollowingsSelected();

    void showMaxFollowingSelectedDialog();

    void showDeleteFollowingDialog(String userId, int position);

    void removeFollowingSelected(int position);

    ArrayList<String> getFollowings();

    void setVisibleRecyclerViewFriendsMap();
}
