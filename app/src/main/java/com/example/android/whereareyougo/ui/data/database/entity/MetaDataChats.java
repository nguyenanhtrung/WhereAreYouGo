package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 17/07/2017.
 */

public class MetaDataChats {
    private String lastSenderId;
    private String lastMessage;
    private String timeStamp;

    public MetaDataChats(String lastSenderId, String lastMessage, String timeStamp) {
        this.lastSenderId = lastSenderId;
        this.lastMessage = lastMessage;
        this.timeStamp = timeStamp;
    }

    public MetaDataChats() {
    }

    public String getLastSenderId() {
        return lastSenderId;
    }

    public void setLastSenderId(String lastSenderId) {
        this.lastSenderId = lastSenderId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
