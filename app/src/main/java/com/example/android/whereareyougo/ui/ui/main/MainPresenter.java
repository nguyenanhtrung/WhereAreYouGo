package com.example.android.whereareyougo.ui.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.ChatUser;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements
        MainMvpPresenter<V> {

    private ChildEventListener messageChildEvent;
    private DatabaseReference messageNotificationRef;

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





    public void updaterUserStatus() {
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

    public void onClickChatUser(ChatUser chatUser) {

    }

    @Override
    public void onSelectSearchVenueTab() {
        getMvpView().openSearchVenueFragment();
    }

    @Override
    public void updateListRequestFollow() {
        getDataManager().getListRequestFollow()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            final ArrayList<User> requestFollows = new ArrayList<>();
                            final int count = (int) dataSnapshot.getChildrenCount();
                            getMvpView().updateBadgeNotification(count);
                            getMvpView().setRequestFollowBadge(count);

                            //
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                RequestFollow request = snapshot.getValue(RequestFollow.class);
                                //
                                getDataManager().getUserInfo(request.getSenderId())
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if (dataSnapshot != null) {
                                                    User user = dataSnapshot.getValue(User.class);
                                                    requestFollows.add(user);
                                                    if (requestFollows.size() == count) {
                                                        getMvpView().setRequestFollows(requestFollows);
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

    public void updateMessageNotification() {
        messageChildEvent = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String senderId = (String) dataSnapshot.getValue();

                Log.d(MyKey.MAIN_ACTIVITY_TAG, "senderId = " + senderId);
                getMvpView().messageNotifications().add(senderId);
                getMvpView().updateBadgeNotification(1);
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
        messageNotificationRef = getDataManager().getMessageNotificationRef();
        messageNotificationRef.addChildEventListener(messageChildEvent);
    }

    public void removeMessageNotificationChildEvent() {
        if (messageNotificationRef != null) {
            messageNotificationRef.removeEventListener(messageChildEvent);
        }
    }


    public void updateListRequestAddFriend() {
        getDataManager().getListRequestAddFriend()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            final ArrayList<User> userRequests = new ArrayList<>();
                            final int count = (int) dataSnapshot.getChildrenCount();
                            getMvpView().updateBadgeNotification(count);
                            getMvpView().setRequestAddFriendBadge(count);

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
    public void onClickFollowersItem() {
        getMvpView().openFollowersFragment();
        getMvpView().closeUserSettingDrawer();
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
