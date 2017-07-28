package com.example.android.whereareyougo.ui.ui.messages;

import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.Members;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.entity.UserMessage;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class MessagesPresenter<V extends MessagesView> extends BasePresenter<V> implements MessagesMvpPresenter<V> {
    @Inject
    public MessagesPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getUserConversations() {
        getDataManager().getConversationsOfCurrentUser()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // ArrayList<String> userConversationIds = new ArrayList<>();
                        ArrayList<Members> members = new ArrayList<>();
                        String currentUserId = getDataManager().getCurrentUserId();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Members member = new Members();
                            member.setConversationId(snapshot.getKey());

                            for (DataSnapshot s : snapshot.getChildren()) {
                                if (s.getKey().equals(currentUserId)) {
                                    member.setCurrentUserId(s.getKey());
                                } else {
                                    member.setFriendId(s.getKey());
                                }
                            }

                            members.add(member);
                        }
                        //get user lastmessages by keys
                        getMessagesOfCurrentUser(members);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getMessagesOfCurrentUser(final ArrayList<Members> members) {
        if (members == null || members.isEmpty()) {
            return;
        }
        getDataManager().getChatsReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<MetaDataChats> metaDataChats = new ArrayList<>();
                        for (int i = 0; i < members.size(); i++) {
                            MetaDataChats metaData = dataSnapshot.child(members.get(i).getConversationId()).getValue(MetaDataChats.class);
                            if (metaData != null) {
                                metaDataChats.add(metaData);
                            }
                        }
                        //show list metadatachats
                        Log.d(MyKey.MESSAGES_FRAGMENT_TAG, "Size Metadatachats = " + metaDataChats.size());
                        //get friends info: name, image by friend id in members object
                        setupUserMessages(members, metaDataChats);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void setupUserMessages(final ArrayList<Members> members, final ArrayList<MetaDataChats> metaDataChats) {
        getDataManager().getUsersRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<User> friends = new ArrayList<>();
                        //get friend info by friend id
                        for (int i = 0; i < members.size(); i++) {
                            User friend = dataSnapshot.child(members.get(i).getFriendId()).getValue(User.class);
                            friends.add(friend);
                        }
                        //if get friends info success, then create datas for recyclerview user messages
                        if (!friends.isEmpty()) {
                            setupMessagesForRecyclerView(friends, members, metaDataChats);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private ArrayList<UserMessage> getUserMessagesForRecyclerView(ArrayList<User> friends,
                                                                  ArrayList<Members> members,
                                                                  ArrayList<MetaDataChats> metaDataChats) {
        ArrayList<UserMessage> userMessages = new ArrayList<>();
        //
        for (int i = 0; i < friends.size(); i++) {
            UserMessage userMessage = new UserMessage();
            userMessage.setFriend(friends.get(i));
            userMessage.setConversationId(members.get(i).getConversationId());
            userMessage.setMetaDataChats(metaDataChats.get(i));
            //
            userMessages.add(userMessage);
        }
        //
        return userMessages;
    }

    private void setupMessagesForRecyclerView(ArrayList<User> friends,
                                              ArrayList<Members> members,
                                              ArrayList<MetaDataChats> metaDataChats) {
        ArrayList<UserMessage> userMessages = getUserMessagesForRecyclerView(friends, members, metaDataChats);
        if (userMessages != null) {
            getMvpView().setDatasForUserMessagesAdapter(userMessages);
        }

    }
}


