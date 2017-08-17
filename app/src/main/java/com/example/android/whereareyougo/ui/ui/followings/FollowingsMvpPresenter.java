package com.example.android.whereareyougo.ui.ui.followings;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public interface FollowingsMvpPresenter<V extends FollowingsView> extends MvpPresenter<V> {
    void showUserFollowings();
}
