package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 01/08/2017.
 */

public class ChatUser {
    private User userInfo;
    private int messageNotification;
    private boolean isCurrentUser = false;

    public ChatUser(User userInfo, int messageNotification, boolean isCurrentUser) {
        this.userInfo = userInfo;
        this.messageNotification = messageNotification;
        this.isCurrentUser = isCurrentUser;
    }

    public ChatUser() {
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        isCurrentUser = currentUser;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public int getMessageNotification() {
        return messageNotification;
    }

    public void setMessageNotification(int messageNotification) {
        this.messageNotification = messageNotification;
    }
}
