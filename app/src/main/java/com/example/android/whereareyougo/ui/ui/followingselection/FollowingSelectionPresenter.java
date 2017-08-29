package com.example.android.whereareyougo.ui.ui.followingselection;

import android.view.View;

import com.example.android.whereareyougo.R;
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
 * Created by nguyenanhtrung on 20/08/2017.
 */

public class FollowingSelectionPresenter<V extends FollowingSelectionView> extends BasePresenter<V> implements FollowingSelectionMvpPresenter<V> {

    @Inject
    public FollowingSelectionPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void getFollowingsInfo(final ArrayList<String> followingIds) {
        if (followingIds != null && !followingIds.isEmpty()) {
            getDataManager().getUsersRef()
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<User> followings = new ArrayList<>();
                            for (String followingId : followingIds) {
                                User currentUser = dataSnapshot.child(followingId).getValue(User.class);
                                if (currentUser != null) {
                                    followings.add(currentUser);
                                }
                            }
                            getMvpView().setupDatasForRecyclerViewFollowings(followings);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    public void onClickFollowingsRecyclerViewItem(User user, View view) {
        if (getMvpView().getFollowingsSelected().size() == getMvpView().getMaxSelectFollowing()) {
            //show message to user: max select following = 5
            if (!getMvpView().getFollowingsSelected().containsKey(user.getUserID())){
                return;
            }

        }

        if (!getMvpView().getFollowingsSelected().containsKey(user.getUserID())) {
            view.setBackgroundResource(R.drawable.background_text_search);
            getMvpView().addFollowingSelected(user);
            getMvpView().setTextNumOfFollowing(String.valueOf(getMvpView().getFollowingsSelected().size()) + "/" + getMvpView().getMaxSelectFollowing());

        } else {
            view.setBackgroundResource(0);
            getMvpView().removeFollowingSelected(user.getUserID());
           // int numOfFollowing = getMvpView().getFollowingsSelected().size() - 1;
            getMvpView().setTextNumOfFollowing(String.valueOf(getMvpView().getFollowingsSelected().size()) + "/" + getMvpView().getMaxSelectFollowing());
        }
    }

    public void onClickButtonCloseDialog() {
        getMvpView().dismissDialog();
    }

    public void onClickButtonAddFollowings(int numOfFollowings) {
        if (numOfFollowings == 0) {
            //show message to user: followings is empty
        } else {
            //send datas to friendmapfragment
            getMvpView().sendFollowingsToFriendMapFragment();
            getMvpView().dismissDialog();
        }
    }
}
