package com.example.android.whereareyougo.ui.ui.chat;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

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
}
