package com.example.android.whereareyougo.ui.ui.chat;

import android.support.annotation.NonNull;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 16/07/2017.
 */

public class ChatDialogPresenter<V extends ChatDialogView> extends BasePresenter<V> implements ChatDialogMvpPresenter<V> {

    private DatabaseReference messagesRef;

    @Inject
    public ChatDialogPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onClickButtonSendMessage(String message) {
        //check message empty, if empty --> not send, return instantly
        if (message.isEmpty()) {
            return;
        }

        //format message
        message = formatMessage(message);

        //push message object --> firebase database
        //create new chat message entity
        ChatMessage newMessage = createNewMessage(message, MyKey.TEXT_TYPE_MESSAGE, getMvpView().getCurrentUserId());
        //get conversationId
        String conversationId = getMvpView().getConversationId();
        //send message to firebase database
        sendMessage(newMessage, conversationId);


    }

    public void onClickButtonSelectPhoto() {
        getMvpView().pickImageFromGallery();
    }

    public void updateFriendStatus(String friendId) {
        getDataManager().getUserStatusRefById(friendId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String status = (String) dataSnapshot.getValue();
                        if (status != null) {
                            getMvpView().setImageUserStatsus(status);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void setMessagesReferenceChildEvent(ChildEventListener childEvent, String conversationId) {
        DatabaseReference messagesRef = getDataManager().getMessagesReferenceByConversationId(conversationId);
        messagesRef.addChildEventListener(childEvent);
    }

    public void removeChilEventOnMessagesRef(ChildEventListener childEventListener) {
        if (messagesRef != null) {
            messagesRef.removeEventListener(childEventListener);
            messagesRef = null;
        }
    }

    private ChatMessage createNewMessage(String message, String typeMessage, String senderId) {
        ChatMessage newMessage = new ChatMessage();
        newMessage.setMessage(message);
        newMessage.setTypeMessage(typeMessage);
        newMessage.setSenderId(senderId);
        newMessage.setTimeStamp(Commons.getCurrentDate());
        //
        return newMessage;
    }

    private void sendMessage(ChatMessage message, String conversationId) {
        //send message to firebase database
        getDataManager().sendChatMessage(message, conversationId)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //set text input message = ""
                            getMvpView().setTextInputMessage(null);
                            //show message send message successful

                            //update last message of conversation

                        } else {
                            //show message send message have some problem
                        }
                    }
                });

    }

    public void sendMessagePhoto(String photoUri, String photoName, final String conversationId) {
        if (photoUri != null) {
            StorageReference messagePhotosRef = getDataManager().getUserMessagePhotosReference()
                    .child(photoName);
            messagePhotosRef.putFile(Commons.convertStringToUri(photoUri))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //get photoUri and send message to firebase database
                            @SuppressWarnings("VisibleForTests")String photoUri = Commons.convertUriToString(taskSnapshot.getDownloadUrl());
                            if (photoUri != null){
                                ChatMessage chatMessage = createNewMessage(photoUri,MyKey.IMAGE_TYPE_MESSAGE,getMvpView().getCurrentUserId());
                                //send to firebase database
                                sendMessage(chatMessage,conversationId);
                            }
                        }
                    });

        }
    }

    public void onMessagesRefChildEvent(ChatMessage chatMessage) {
        if (chatMessage.getSenderId().equals(getMvpView().getFriendId())) {
            String friendImageUrl = getMvpView().getFriendImageUrl();
            if (friendImageUrl != null) {
                chatMessage.setSenderImageUrl(friendImageUrl);
            }

        } else {
            String userImageUrl = getMvpView().getCurrentUserImageUrl();
            if (userImageUrl != null) {
                chatMessage.setSenderImageUrl(userImageUrl);
            }
        }
        getMvpView().addChatMessagesToAdapter(chatMessage);
        getMvpView().dismissLoadingDialog();
        //update last message of conversation on firebase database
        // updateLastMessageOfConversation(getMvpView().getConversationId(),chatMessage);

    }

    private void updateLastMessageOfConversation(String conversationId, ChatMessage chatMessage) {
        //create new meta data chat
        MetaDataChats metaDataChats = new MetaDataChats();
        metaDataChats.setLastSenderId(chatMessage.getSenderId());
        metaDataChats.setTimeStamp(chatMessage.getTimeStamp());
        metaDataChats.setLastMessage(chatMessage.getMessage());
        //
        getDataManager().updateLastMessageConversation(metaDataChats, conversationId);
    }

    private String formatMessage(String message) {
        return message.trim();
    }


    public void createConversationId(final String conversationId) {

        //
        getDataManager().getChatsReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isConversationIdExists = dataSnapshot.hasChild(conversationId);
                        if (isConversationIdExists) {
                            //if conversation id exists, set conversationId and get List Messages From Database
                            //check if list messages empty or not
                            getMvpView().setupDatasForConvesationAdapter(new ArrayList<ChatMessage>());

                        } else {
                            //create new conversation id between user and friend
                            // String conversationId = getConversationId(f)
                            getDataManager().createConversationId(conversationId);

                            //show recyclerview empty
                            getMvpView().setupDatasForConvesationAdapter(new ArrayList<ChatMessage>());
                        }

                        getMvpView().setConversationId(conversationId);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public String getConversationId(String friendId) {
        String userId = getMvpView().getCurrentUserId();
        //create conversationId between user and friend
        StringBuilder builder = new StringBuilder();
        builder.append(userId).append(friendId);

        char[] conversationIdChar = builder.toString().toCharArray();
        Arrays.sort(conversationIdChar);
        return String.valueOf(conversationIdChar);
    }

    public void onClickButtonSelectEmoj() {
        getMvpView().showEmojKeyboard();
    }

}
