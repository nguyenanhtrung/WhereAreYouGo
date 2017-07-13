package com.example.android.whereareyougo.ui.ui.listfriend;

import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public interface ListFriendView extends MvpView{
    void openAddFriendDialogFragment();

    void setupFriendsRecyclerViewAdapter(ArrayList<User> datas);

    void setFriends(List<Friend> friends);

    void openFriendProfile(User user);

    void showMessage(int messageId);
}
