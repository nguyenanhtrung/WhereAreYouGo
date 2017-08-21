package com.example.android.whereareyougo.ui.ui.addfriend;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.List;

/**
 * Created by nguyenanhtrung on 02/07/2017.
 */

public interface AddFriendView extends MvpView {
    void showMessage(int messageId);

    void setupUsersRecyclerViewAdapter(List<User> userList);

    void clearDataForFriendsRecyclerView();

    void clearEditTextName();

    void clearEditTextPhone();

    boolean checkSearchByName();

    void closeDialog();

    void setButtonAddFriendEnable(int idContent, boolean isEnable);

    void setButtonAddFriendVisibility(boolean isVisible);
}
