package com.example.android.whereareyougo.ui.ui.requestaddfriend;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public interface RequestAddFriendView extends MvpView{
    void setUserRequests(ArrayList<User> userRequests);
    void showMessage(int messageId);
    void removeRequestInRecyclerView(int position);
}
