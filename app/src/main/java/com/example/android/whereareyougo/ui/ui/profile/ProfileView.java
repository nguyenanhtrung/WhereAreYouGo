package com.example.android.whereareyougo.ui.ui.profile;

import com.example.android.whereareyougo.ui.ui.base.MvpView;

/**
 * Created by nguyenanhtrung on 10/07/2017.
 */

public interface ProfileView extends MvpView {
    void dismissDialog();

    void showNumberOfFriends(String numberOfFriends);
}
