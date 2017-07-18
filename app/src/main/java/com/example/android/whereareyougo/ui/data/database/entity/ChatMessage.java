package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 17/07/2017.
 */

public class ChatMessage {
    private String senderId;
    private String senderName;
    private String senderImageUrl;
    private String message;
    private String typeMessage;
    private String timeStamp;

    public ChatMessage(String senderId, String message, String timeStamp) {
        this.senderId = senderId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public ChatMessage() {
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImageUrl() {
        return senderImageUrl;
    }

    public void setSenderImageUrl(String senderImageUrl) {
        this.senderImageUrl = senderImageUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
