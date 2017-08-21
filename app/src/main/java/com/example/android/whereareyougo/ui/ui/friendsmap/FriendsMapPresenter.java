package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.util.Log;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapPresenter<V extends FriendsMapView> extends BasePresenter<V> implements FriendsMapMvpPresenter<V> {
    private DatabaseReference followingsRef;
    private ChildEventListener followingsChildEvent;

    @Inject
    public FriendsMapPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getUserFollowings() {
        followingsChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String followingId = dataSnapshot.child("followingid").getValue(String.class);
                Log.d(MyKey.FRIENDS_MAP_FRAGMENT_TAG, "FollowingId = " + followingId);
                if (followingId != null) {
                    getMvpView().addFollowing(followingId);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
        followingsRef = getDataManager().getFollowingsRefById(getDataManager().getCurrentUserId());
        if (followingsRef != null) {
            followingsRef.addChildEventListener(followingsChildEvent);
        }
    }

    public void removeFollowingsChildEvent() {
        if (followingsChildEvent != null && followingsRef != null) {
            followingsRef.removeEventListener(followingsChildEvent);
        }
    }

    public void onClickButtonAddFollowings() {
        //check if followingSelecteds = MAX(5), if MAX then show message to user and not open following selection dialog
        if (!checkMaxFollowingSelected()) {
            getMvpView().openFollowingsSelectionDialog();
        }

    }

    private boolean checkMaxFollowingSelected() {
        if (getMvpView().getFollowingsSelected() != null) {
            if (getMvpView().getFollowingsSelected().size() == MyKey.MAX_FOLLOWING_SELECT) {
                getMvpView().showMaxFollowingSelectedDialog();
                return true;
            }
        }
        return false;
    }

}
