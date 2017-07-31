package com.example.android.whereareyougo.ui.ui.messages;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public interface MessagesView extends MvpView {
    void setDatasForUserMessagesAdapter(ArrayList<UserMessage> userMessages);

    void openChatDialogFragment(User friend);

    void setUserMessages(ArrayList<UserMessage> userMessages);

    void setTextMessageStatus(int messageId);
}
