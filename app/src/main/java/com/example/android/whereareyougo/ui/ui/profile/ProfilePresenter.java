package com.example.android.whereareyougo.ui.ui.profile;

import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 10/07/2017.
 */

public class ProfilePresenter<V extends ProfileView> extends BasePresenter<V> implements ProfileMvpPresenter<V> {
    @Inject
    public ProfilePresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void onClickButtonCloseDialog() {
        getMvpView().dismissDialog();
    }

    public void getNumberOfFriends(String userId) {
        getDataManager().getFriendsRef(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        getMvpView().showNumberOfFriends(String.valueOf(dataSnapshot.getChildrenCount()));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
