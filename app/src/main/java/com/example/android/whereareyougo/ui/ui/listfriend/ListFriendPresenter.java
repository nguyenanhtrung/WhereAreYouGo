package com.example.android.whereareyougo.ui.ui.listfriend;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class ListFriendPresenter<V extends ListFriendView> extends BasePresenter<V> implements ListFriendMvpPresenter<V> {

    @Inject
    public ListFriendPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void onClickButtonAddFriend() {
        getMvpView().openAddFriendDialogFragment();
    }

    public void onClickButtonCall(User friend) {
        getMvpView().callPhone(friend.getPhoneNumber());
    }

    @Override
    public void getUserListFriend() {
        getDataManager().getCurrentUserFriends()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Friend> friends = new ArrayList<>();
                        if (dataSnapshot.getChildrenCount() != 0) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Friend friend = snapshot.getValue(Friend.class);
                                friends.add(friend);
                            }
                            getMvpView().setFriends(friends);
                            getListFriendByIds(friends);
                        } else {
                            getMvpView().setupFriendsRecyclerViewAdapter(new ArrayList<User>());
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onClickButtonSeeProfile(User user) {
        if (user != null) {
            getMvpView().openFriendProfile(user);
        }
    }

    public void onClickButtonFollow(final User user) {
        //check if user has permission follow
        //if user has then click button follow --> show message already follow
        getDataManager().getFriendsRefByFriendId(user.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Friend friend = dataSnapshot.getValue(Friend.class);
                    if (friend.isPermissionFollow()) {
                        //show message already follow
                        getMvpView().showMessage(R.string.text_already_follow);

                    } else {
                        getDataManager().sendRequestFollow(user.getUserID())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            getMvpView().showMessage(R.string.text_send_request_successfull);
                                        }
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClickButtonUnfriend(String friendId, int friendPosition) {
        //test showing dialog
        getMvpView().showAskUnfriendDialog(friendId, friendPosition);

    }

    public void onClickButtonAgreeUnfriendDialog(String friendId, int friendPosition) {
        removeFriend(friendId, friendPosition);
    }

    private void removeFriend(String friendId, int friendPostition) {
        removeMembersByFriendId(friendId);
        removeFriendOnServer(friendId);
        removeFriendInRecyclerView(friendPostition);

    }

    private void removeFriendInRecyclerView(int position) {
        getMvpView().removeFriendInRecyclerView(position);
    }

    private void removeFriendOnServer(String friendId) {
        if (friendId != null) {
            getDataManager().removeFriendByFriendId(friendId);
        }
    }

    private void removeMembersByFriendId(String friendId) {
        //get conversationId between userId and friendId
        String conversationId = getConversationId(friendId);
        if (conversationId != null) {
            getDataManager().removeMembersDataByConversationId(conversationId);
        }
    }

    private String getConversationId(String friendId) {
        String userId = getDataManager().getCurrentUserId();
        //create conversationId between user and friend
        StringBuilder builder = new StringBuilder();
        builder.append(userId).append(friendId);

        char[] conversationIdChar = builder.toString().toCharArray();
        Arrays.sort(conversationIdChar);
        return String.valueOf(conversationIdChar);
    }

    @Override
    public void onClickButtonChat(User friend) {
        getMvpView().openChatDialogFragment(friend);
    }

    private void getListFriendByIds(List<Friend> friends) {
        if (friends != null && !friends.isEmpty()) {
            final ArrayList<User> users = new ArrayList<>();
            final int count = friends.size();
            for (Friend friend : friends) {
                getDataManager().getUserInfo(friend.getId())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
                                users.add(user);
                                if (users.size() == count) {
                                    getMvpView().setupFriendsRecyclerViewAdapter(users);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        }
    }


}
