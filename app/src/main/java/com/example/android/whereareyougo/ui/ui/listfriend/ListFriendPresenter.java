package com.example.android.whereareyougo.ui.ui.listfriend;

import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
