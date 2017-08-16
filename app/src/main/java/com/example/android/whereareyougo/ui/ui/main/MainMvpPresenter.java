package com.example.android.whereareyougo.ui.ui.main;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MainMvpPresenter<V extends MainView> extends MvpPresenter<V> {
  void updateUserInfo();

  void onClickUserSettingButton();

  void onCLickUserSettingDrawerItem();

  void onClickUserFavoriteVenueItem();

  void onClickFollowersItem();

  void onSelectListFriendTab();

  void onSelectNotificationsTab();

  void updateListRequestAddFriend();

  void updaterUserStatus();

  void onSelectSearchVenueTab();

  void updateListRequestFollow();

  void updateMessageNotification();

  void removeMessageNotificationChildEvent();

  void onClickAppSettingItemUserDrawer();

  void onUserLocationChange(String userLocation);

  void onSelectMapTab();
}
