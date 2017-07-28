package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 28/07/2017.
 */

public class UserMessage {
    private String conversationId;
    private MetaDataChats metaDataChats;
    private User friend;

    public UserMessage(String conversationId, MetaDataChats metaDataChats, User friend) {
        this.conversationId = conversationId;
        this.metaDataChats = metaDataChats;
        this.friend = friend;
    }

    public UserMessage() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public MetaDataChats getMetaDataChats() {
        return metaDataChats;
    }

    public void setMetaDataChats(MetaDataChats metaDataChats) {
        this.metaDataChats = metaDataChats;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
