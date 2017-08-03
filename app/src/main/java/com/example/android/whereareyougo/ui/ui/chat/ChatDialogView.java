package com.example.android.whereareyougo.ui.ui.chat;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 16/07/2017.
 */

public interface ChatDialogView extends MvpView {
    void showLoadingDialog(int titleId, int contentId);

    String getCurrentUserId();

    void setupDatasForConvesationAdapter(ArrayList<ChatMessage> datas);

    void dismissLoadingDialog();

    String getConversationId();

    void setConversationId(String conversationId);

    String getCurrentUserImageUrl();

    String getFriendImageUrl();

    void addChatMessagesToAdapter(ChatMessage chatMessage);

    String getFriendId();

    void setImageUserStatsus(String status);

    void showEmojKeyboard();

    void pickImageFromGallery();

    void setTextInputMessage(String content);

    void dismissChatDialog();

    void removeChildEventListener();

    void setUserBadgeNotification(int badgeNotification, int userPosition);

    void addChatUserToRecyclerView(ChatUser chatUser);

    void notifyDataChatUsersChange();

    ChildEventListener getMessageChildEvent();

    void setMessageChildEvent(ChildEventListener childEvent);

    void clearDataChatMessagesAdapter();

    void setTextFriendName(String friendName);

    void setFriend(User friend);
}
