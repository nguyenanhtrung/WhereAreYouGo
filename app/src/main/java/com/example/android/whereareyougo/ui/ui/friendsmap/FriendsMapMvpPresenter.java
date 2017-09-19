package com.example.android.whereareyougo.ui.ui.friendsmap;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public interface FriendsMapMvpPresenter<V extends FriendsMapView> extends MvpPresenter<V> {
    void getUserFollowings();

    void removeFollowingsChildEvent();

    void onClickButtonAddFollowings();

    void onClickButtonDeleteFollowingSelected(String userId, int position);

    void onClickButtonAgreeDeleteFollowingDialog(String userId, int position);

    void onClickButtonShowRecyclerViewFriendsMap();

    void onClickButtonUserLocation();

    void setupUpdateFollowingRealTime(User following);

    void onClickFollowingSelectedItem(int position);
}
