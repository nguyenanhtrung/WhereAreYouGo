package com.example.android.whereareyougo.ui.ui.profile;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 10/07/2017.
 */

public interface ProfileMvpPresenter<V extends ProfileView> extends MvpPresenter<V> {
    void onClickButtonCloseDialog();

    void getNumberOfFriends(String userId);
}
