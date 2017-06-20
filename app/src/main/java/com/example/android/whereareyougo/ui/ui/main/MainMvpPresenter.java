package com.example.android.whereareyougo.ui.ui.main;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MainMvpPresenter<V extends MainView> extends MvpPresenter<V> {
  void updateUserInfo();

  void onClickUserSettingButton();

  void onCLickUserSettingDrawerItem();
}