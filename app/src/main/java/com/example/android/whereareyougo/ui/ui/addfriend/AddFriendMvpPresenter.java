package com.example.android.whereareyougo.ui.ui.addfriend;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 02/07/2017.
 */

public interface AddFriendMvpPresenter<V extends AddFriendView> extends MvpPresenter<V> {
    void onClickButtonSearch(String content);
    void onClickButtonCloseDialog();
}
