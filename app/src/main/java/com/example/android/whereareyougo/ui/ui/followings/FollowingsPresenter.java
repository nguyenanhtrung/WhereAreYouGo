package com.example.android.whereareyougo.ui.ui.followings;

import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public class FollowingsPresenter<V extends FollowingsView> extends BasePresenter<V> implements FollowingsMvpPresenter<V> {

    @Inject
    public FollowingsPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void showUserFollowings() {
        getDataManager().getFollowingsOfUser(getDataManager().getCurrentUserId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() != 0) {
                            ArrayList<String> followingIds = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                followingIds.add(snapshot.getKey());
                            }
                            showFollowingInfo(followingIds);

                        } else {
                            getMvpView().hideLoadingDialog();
                            getMvpView().setDataForFollowingsRecyclerViewAdapter(new ArrayList<User>());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void showFollowingInfo(final ArrayList<String> followingIds) {
        if (followingIds != null) {
            getDataManager().getUsersRef()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<User> followings = new ArrayList<>();
                            for (String followingId : followingIds) {
                                User user = dataSnapshot.child(followingId).getValue(User.class);
                                if (user != null) {
                                    followings.add(user);
                                }
                            }
                            getMvpView().setDataForFollowingsRecyclerViewAdapter(followings);
                            getMvpView().hideLoadingDialog();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }


}
