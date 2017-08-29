package com.example.android.whereareyougo.ui.ui.friendsmap;

import android.util.Log;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Commons;
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

    public void onClickFollowingSelectedItem() {

    }

    public void onClickButtonAddFollowings() {
        //check if followingSelecteds = MAX(5), if MAX then show message to user and not open following selection dialog
        if (checkFollowingSelectedValid()) {
            getMvpView().openFollowingsSelectionDialog();
        }

    }

    private void updateFollowingInfo(User following) {
        int followingIndex = getMvpView().getFollowingSelectedIndexs().get(following.getUserID());
        getMvpView().getFollowingsSelected().get(followingIndex).setAllData(following);
        getMvpView().notifyFollowingSelectedAdapterChange();
        getMvpView().setFollowingMarker(followingIndex, Commons.convertStringToLocation(following.getCurrentLocation()));
    }

    public void setupUpdateFollowingRealTime(User following) {
        //  createFollowingLocationReferences(followingsSelected);
        //  setLocationEventListenerForReferences(followingReferences);
        if (following != null){
            createFollowingLocationReference(following);
        }

    }

   /* private void createFollowingLocationReferences(ArrayList<User> followingsSelected) {
        if (followingsSelected != null) {
            if (followingReferences == null) {
                followingReferences = new ArrayList<>();
            }
            for (User currentFollowing : followingsSelected) {
                DatabaseReference followingLocationRef = getDataManager().getUserInfo(currentFollowing.getUserID());
                if (followingLocationRef != null) {
                    followingReferences.add(followingLocationRef);
                }
            }
        }
    }*/

    private void createFollowingLocationReference(User following) {
        if (following != null) {
            DatabaseReference followingLocationRef = getDataManager().getUserInfo(following.getUserID());
            if (followingLocationRef != null) {
                if (followingReferences == null) {
                    followingReferences = new ArrayList<>();
                }
                followingReferences.add(followingLocationRef);
                setLocationEventListenerForReference(followingLocationRef);
            }
        }
    }

    private void setLocationEventListenerForReference(DatabaseReference followingRef) {
        if (followingRef != null) {
            if (locationEventListener == null) {
                locationEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        if (user != null) {
                            updateFollowingInfo(user);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
            }
        }
    }

 /*   private void setLocationEventListenerForReferences(ArrayList<DatabaseReference> followingReferences) {
        if (followingReferences != null) {
            locationEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //1.get index by following id
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        updateFollowingInfo(user);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
            for (DatabaseReference followingRef : followingReferences) {
                followingRef.addValueEventListener(locationEventListener);
            }
        }
        //
    } */

    private void removeFollowingReference(int position) {
        if (followingReferences != null && !followingReferences.isEmpty()) {
            followingReferences.get(position).removeEventListener(locationEventListener);
            followingReferences.remove(position);
        }
    }

    private boolean checkFollowingSelectedValid() {
        if (getMvpView().getFollowingsSelected() != null) {
            if (getMvpView().getFollowingsSelected().size() == MyKey.MAX_FOLLOWING_SELECT) {
                getMvpView().showMaxFollowingSelectedDialog();
                return false;
            } else if (getMvpView().getFollowings() == null) {
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
        //3.Delete following marker in arraylist
        getMvpView().getFollowingMarkers().get(position).remove();
        getMvpView().getFollowingMarkers().remove(position);
        getMvpView().getFollowingSelectedIndexs().remove(userId);
        //remove following reference firebase database
        removeFollowingReference(position);
        //4.Add following Id to followings
        getMvpView().getFollowings().add(userId);
    }

    public void onClickButtonUserLocation() {
        if (getMvpView().isFollowCurrentUser()) {
            getMvpView().setButtonUserLocationIcon(R.drawable.ic_follow);
            getMvpView().setFollowCurrentUser(false);
        } else {
            getMvpView().setButtonUserLocationIcon(R.drawable.ic_unfollow);
            getMvpView().setFollowCurrentUser(true);
        }

    }

    public void onClickButtonShowRecyclerViewFriendsMap() {
        getMvpView().setVisibleRecyclerViewFriendsMap();
    }
}
