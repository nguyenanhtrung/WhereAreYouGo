package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 18/08/2017.
 */

public class FriendsMapPresenter<V extends FriendsMapView> extends BasePresenter<V> implements FriendsMapMvpPresenter<V> {
    private DatabaseReference followingsRef;
    private ChildEventListener followingsChildEvent;
    private ArrayList<DatabaseReference> followingReferences;
    private ValueEventListener locationEventListener;

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
        if (checkFollowingSelectedValid()) {
            getMvpView().openFollowingsSelectionDialog();
        }

    }

    private void createFollowingLocationReferences(ArrayList<User> followingsSelected) {
        if (followingsSelected != null) {
            if (followingReferences == null) {
                followingReferences = new ArrayList<>();
                for (User currentFollowing : followingsSelected) {
                    DatabaseReference followingLocationRef = getDataManager().getUserLocationRef(currentFollowing.getUserID());
                    if (followingLocationRef != null) {
                        followingReferences.add(followingLocationRef);
                    }
                }
            }
        }
    }

    private void setLocationEventListenerForReferences(ArrayList<DatabaseReference> followingReferences){
        if (followingReferences != null){
            if (locationEventListener == null){
                locationEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                for(DatabaseReference followingRef : followingReferences){
                    followingRef.addValueEventListener(locationEventListener);
                }
            }
            //
        }
    }

    public void removeFollowingReference(int position){
        if (followingReferences != null && !followingReferences.isEmpty()){
            followingReferences.get(position).removeEventListener(locationEventListener);
            followingReferences.remove(position);
        }
    }

    private boolean checkFollowingSelectedValid() {
        if (getMvpView().getFollowingsSelected() != null) {
            if (getMvpView().getFollowingsSelected().size() == MyKey.MAX_FOLLOWING_SELECT) {
                getMvpView().showMaxFollowingSelectedDialog();
                return false;
            } else if (getMvpView().getFollowings().size() == 0) {
                //show dialog to user: not exists following to select
                return false;
            }
        }
        return true;
    }


    public void onClickButtonDeleteFollowingSelected(String userId, int position) {
        //1. Show Dialog ask if user delete following from followingSeleccted RecyclerView
        getMvpView().showDeleteFollowingDialog(userId, position);
    }

    public void onClickButtonAgreeDeleteFollowingDialog(String userId, int position) {
        //2.Delete followng in recycler view
        getMvpView().removeFollowingSelected(position);
        //3.Add following Id to followings
        getMvpView().getFollowings().add(userId);
    }

    public void onClickButtonShowRecyclerViewFriendsMap() {
        getMvpView().setVisibleRecyclerViewFriendsMap();
    }
}
