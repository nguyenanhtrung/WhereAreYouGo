package com.example.android.whereareyougo.ui.ui.listfriend;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public interface ListFriendMvpPresenter<V extends ListFriendView> extends MvpPresenter<V> {
    void onClickButtonAddFriend();
}
