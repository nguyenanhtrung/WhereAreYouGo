package com.example.android.whereareyougo.ui.ui.followers;

import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public class FollowersPresenter<V extends FollowersView> extends BasePresenter<V> implements FollowersMvpPresenter<V> {

    @Inject
    public FollowersPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void showUserFollowers() {
        getDataManager().getUserFriendsHasPermissionFollow()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<Friend> friends = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Friend friend = snapshot.getValue(Friend.class);
                            if (friend != null) {
                                friends.add(friend);
                            }
                        }//
                        if (!friends.isEmpty()) {
                            //get info of followers
                            getUserFollowersForRecyclerViewFollowers(friends);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void getUserFollowersForRecyclerViewFollowers(final ArrayList<Friend> friends) {
        getDataManager().getUsersRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<User> users = new ArrayList<>();
                        for (Friend friend : friends) {
                            User user = dataSnapshot.child(friend.getId()).getValue(User.class);
                            if (user != null) {
                                users.add(user);
                            }
                        }
                        //
                        if (!friends.isEmpty()) {
                            //setup data for recyclerview
                            getMvpView().dismissLoadingDialog();
                            getMvpView().setupDatasForRecyclerViewFollowers(users);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
