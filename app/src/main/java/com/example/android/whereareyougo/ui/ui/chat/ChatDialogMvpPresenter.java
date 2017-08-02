package com.example.android.whereareyougo.ui.ui.chat;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;
import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 16/07/2017.
 */

public interface ChatDialogMvpPresenter<V extends ChatDialogView> extends MvpPresenter<V> {
    void onClickButtonSendMessage(String message);

    void createConversationId(String conversationId);

    void setMessagesReferenceChildEvent(ChildEventListener childEvent, String conversationId);

    void removeChilEventOnMessagesRef(ChildEventListener childEventListener);

    void onMessagesRefChildEvent(ChatMessage chatMessage);

    String getConversationId(String friendId);

    void updateFriendStatus(String friendId);

    void onClickButtonSelectEmoj();

    void onClickButtonSelectPhoto();

    void sendMessagePhoto(String photoUri, String photoName, final String conversationId);

    void onClickButtonCloseChatDialog();

    void updateMessageNotifications(final ArrayList<ChatUser> chatUsers);

    void onClickChatUser(ChatUser chatUser, ChatUser previousChatUser);
}
