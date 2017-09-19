package com.example.android.whereareyougo.ui.ui.followingselection;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.DialogMvpView;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nguyenanhtrung on 20/08/2017.
 */

public interface FollowingSelectionView extends DialogMvpView{
    void setupDatasForRecyclerViewFollowings(ArrayList<User> followings);

    void addFollowingSelected(User user);

    HashMap<String, User> getFollowingsSelected();

    void removeFollowingSelected(String userId);

    void setTextNumOfFollowing(String numOfFollowing);

    void dismissDialog();

    void sendFollowingsToFriendMapFragment();

    ArrayList<String> getFollowingIds();

    int getMaxSelectFollowing();
}
