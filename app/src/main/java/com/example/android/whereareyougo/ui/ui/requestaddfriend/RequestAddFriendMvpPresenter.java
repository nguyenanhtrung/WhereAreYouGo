package com.example.android.whereareyougo.ui.ui.requestaddfriend;

import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public interface RequestAddFriendMvpPresenter<V extends RequestAddFriendView> extends MvpPresenter<V> {
    void onClickButtonAccept(String friendId, int position);
}
