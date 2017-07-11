package com.example.android.whereareyougo.ui.ui.main;

import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements
        MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void updateUserInfo() {
        getDataManager().getUserInfo().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                getMvpView().updateUserInfo(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void updaterUserStatus(){
        getDataManager().getConnectionRef()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean connected = dataSnapshot.getValue(Boolean.class);
                        if (connected) {
                            getDataManager().updateUserStatus("ONLINE");
                        }

                        getDataManager().getUserStatusRef().onDisconnect().setValue("OFFLINE");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public void onSelectSearchVenueTab() {
        getMvpView().openSearchVenueFragment();
    }


    public void updateListRequestAddFriend() {
        getDataManager().getListRequestAddFriend()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            final ArrayList<User> userRequests = new ArrayList<>();
                            final long count = dataSnapshot.getChildrenCount();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                RequestAddFriend request = snapshot.getValue(RequestAddFriend.class);
                                //
                                getDataManager().getUserInfo(request.getSenderId())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot != null) {
                                                    User user = dataSnapshot.getValue(User.class);
                                                    userRequests.add(user);
                                                    if (userRequests.size() == count) {
                                                        getMvpView().setRequestAddFriends(userRequests);
                                                        getMvpView().updateBadgeNotification(userRequests.size());
                                                    }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    public void onClickUserSettingButton() {
        getMvpView().openUserSettingDrawer();
    }

    @Override
    public void onCLickUserSettingDrawerItem() {
        getMvpView().closeUserSettingDrawer();
        getMvpView().openUserSettingFragment();

    }

    @Override
    public void onClickUserFavoriteVenueItem() {
        getMvpView().closeUserSettingDrawer();
        getMvpView().openUserListFavoriteVenueFragment();
    }

    @Override
    public void onSelectListFriendTab() {
        getMvpView().openListFriendFragment();
    }

    @Override
    public void onSelectNotificationsTab() {
        getMvpView().resetBadgeNotification();
        getMvpView().openNotificationsFragment();
    }
}
