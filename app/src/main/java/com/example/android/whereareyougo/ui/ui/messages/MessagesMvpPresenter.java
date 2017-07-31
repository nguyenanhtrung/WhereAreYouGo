package com.example.android.whereareyougo.ui.ui.messages;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

import java.util.HashMap;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public interface MessagesMvpPresenter<V extends MessagesView> extends MvpPresenter<V> {
    void getUserConversations();

    void onClickCardMessage(UserMessage userMessage, HashMap<String,Boolean> messagenNotifications);


}
