package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 28/07/2017.
 */

public class Members {
    private String conversationId;
    private String currentUserId;
    private String friendId;

    public Members(String conversationId, String currentUserId, String friendId) {
        this.conversationId = conversationId;
        this.currentUserId = currentUserId;
        this.friendId = friendId;
    }

    public Members() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
