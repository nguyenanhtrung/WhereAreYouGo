package com.example.android.whereareyougo.ui.ui.listfriend;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public interface ListFriendMvpPresenter<V extends ListFriendView> extends MvpPresenter<V> {
    void onClickButtonAddFriend();

    void getUserListFriend();

    void onClickButtonSeeProfile(User user);

    void onClickButtonFollow(User user);

    void onClickButtonChat(User friend);

    void onClickButtonUnfriend(String friendId, int friendPosition);

    void onClickButtonAgreeUnfriendDialog(String friendId, int friendPosition);

    void onClickButtonCall(User friend);

}
