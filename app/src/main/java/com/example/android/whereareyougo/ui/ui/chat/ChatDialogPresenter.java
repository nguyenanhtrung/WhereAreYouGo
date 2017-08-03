package com.example.android.whereareyougo.ui.ui.chat;

import android.support.annotation.NonNull;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
import com.example.android.whereareyougo.ui.data.database.entity.User;
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
    private ChildEventListener messageChildEvent;
    private DatabaseReference messageNotificationRef;
    private DatabaseReference friendStatusRef;
    private ValueEventListener friendStatusEvent;


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

    public void updateMessageNotifications(final ArrayList<ChatUser> chatUsers) {
        messageChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String senderId = (String) dataSnapshot.getValue();
                //get sender info by senderId
                if (senderId != null) {
                    updateChatUsersRecyclerView(senderId, chatUsers);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String senderId = (String) dataSnapshot.getValue();
                //get sender info by senderId
                if (senderId != null) {
                    updateChatUsersRecyclerView(senderId, chatUsers);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        messageNotificationRef = getDataManager().getMessageNotificationRef();
        messageNotificationRef.addChildEventListener(messageChildEvent);
    }

    @Override
    public void onClickChatUser(ChatUser chatUser, ChatUser previousChatUser) {
        if (chatUser.getUserInfo().getUserID().equals(getMvpView().getFriendId())) {
            return;
        }
        if (chatUser != null) {
            previousChatUser.setCurrentUser(false);
            chatUser.setCurrentUser(true);
            getMvpView().notifyDataChatUsersChange();
            //
            //1.Remove message child event listener, get new message
            removeMessageRef();
            getMvpView().clearDataChatMessagesAdapter();
            //2.Create new message Ref to conversation between user and chat user
            createNewMessageRef(chatUser);
            getMvpView().setConversationId(getConversationId(chatUser.getUserInfo().getUserID()));
            //3.Set Chat User Info for friend object, text Friend name
            getMvpView().setTextFriendName(chatUser.getUserInfo().getName());
            getMvpView().setFriend(chatUser.getUserInfo());
            //4.Update Friend Status
            removeFriendStatusRef();
            updateFriendStatus(chatUser.getUserInfo().getUserID());
            //5.Check if chat user has badge number != 0 --> set badge number = 0
            if (chatUser.getMessageNotification() != 0) {
                chatUser.setMessageNotification(0);
                getMvpView().notifyDataChatUsersChange();
                //6.Delete message notification on firebase database
                getDataManager().removeUserMessageNotification(getMvpView().getConversationId());
            }

        }
    }

    private void removeMessageRef() {
        ChildEventListener messageChildEvent = getMvpView().getMessageChildEvent();
        if (messagesRef != null && messageChildEvent != null) {
            messagesRef.removeEventListener(messageChildEvent);
            messagesRef = null;
            //getMvpView().setMessageChildEvent(null);
        }
    }

    private void createNewMessageRef(ChatUser chatUser) {
        //get  conversation id
        String conversationId = getConversationId(chatUser.getUserInfo().getUserID());
        if (conversationId != null) {
            messagesRef = getDataManager().getMessagesReferenceByConversationId(conversationId);
            messagesRef.addChildEventListener(getMvpView().getMessageChildEvent());
        }
    }

    private void updateChatUsersRecyclerView(final String senderId, ArrayList<ChatUser> chatUsers) {
        //check if senderId exists in recyclerview
        int chatUserPosition = isChatUserExitsInRecyclerView(senderId, chatUsers);
        if (chatUserPosition != -1) {
            // set text badge number of sender in recyclerview, set notifydatasetchange
            if (!senderId.equals(getMvpView().getFriendId())){
                getMvpView().setUserBadgeNotification(1, chatUserPosition);
            }

        } else {
            getDataManager().getUserInfo(senderId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User senderInfo = dataSnapshot.getValue(User.class);
                            if (senderInfo != null) {
                                //create new chat user object and add to chatusers in recyclerview
                                ChatUser chatUser = new ChatUser();
                                chatUser.setMessageNotification(1);
                                chatUser.setUserInfo(senderInfo);
                                //add chatUser into list in recyclerView
                                getMvpView().addChatUserToRecyclerView(chatUser);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }


    }

    private int isChatUserExitsInRecyclerView(String senderId, ArrayList<ChatUser> chatUsers) {
        for (int index = 0; index < chatUsers.size(); index++) {
            if (senderId.equals(chatUsers.get(index).getUserInfo().getUserID())) {
                return index;
            }
        }
        return -1;
    }

    public void updateFriendStatus(String friendId) {
        if (friendId != null) {
            friendStatusRef = getDataManager().getUserStatusRefById(friendId);
            friendStatusEvent = new ValueEventListener() {
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
            };
            friendStatusRef.addValueEventListener(friendStatusEvent);

        }


    }

    public void removeFriendStatusRef() {
        if (friendStatusRef != null) {
            friendStatusRef.removeEventListener(friendStatusEvent);
            friendStatusEvent = null;
            friendStatusRef = null;
        }
    }

    public void setMessagesReferenceChildEvent(ChildEventListener childEvent, String conversationId) {
        messagesRef = getDataManager().getMessagesReferenceByConversationId(conversationId);
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

    private void sendMessage(final ChatMessage message, final String conversationId) {
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
                            updateLastMessageOfConversation(conversationId, message);
                            //send message notification include: conversationId, friendId, senderId to Firebase database
                            getDataManager().sendMessageNotification(conversationId, getMvpView().getFriendId());
                        } else {
                            //show message: send message have some problem
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
                            @SuppressWarnings("VisibleForTests") String photoUri = Commons.convertUriToString(taskSnapshot.getDownloadUrl());
                            if (photoUri != null) {
                                ChatMessage chatMessage = createNewMessage(photoUri, MyKey.IMAGE_TYPE_MESSAGE, getMvpView().getCurrentUserId());
                                //send to firebase database
                                sendMessage(chatMessage, conversationId);
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
                        if (!isConversationIdExists) {
                            //create new conversation id between user and friend
                            // String conversationId = getConversationId(f)
                            getDataManager().createConversationId(conversationId);

                            //create members node by conversationID
                            getDataManager().createMembers(conversationId, getMvpView().getFriendId());

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

    public void onClickButtonCloseChatDialog() {
        getMvpView().dismissChatDialog();
        if (messagesRef != null) {
            getMvpView().removeChildEventListener();
            messagesRef = null;
        }
    }


}
