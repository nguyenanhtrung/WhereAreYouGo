package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 04/07/2017.
 */

public class RequestAddFriend {
    private String senderId;
    private boolean isAccept = false;

    public RequestAddFriend(String senderId, boolean isAccept) {
        this.senderId = senderId;
        this.isAccept = isAccept;
    }

    public RequestAddFriend() {

    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }
}
