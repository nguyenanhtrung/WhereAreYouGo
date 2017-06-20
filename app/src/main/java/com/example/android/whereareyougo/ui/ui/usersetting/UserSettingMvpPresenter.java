package com.example.android.whereareyougo.ui.ui.usersetting;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;


/**
 * Created by nguyenanhtrung on 13/04/2017.
 */

public interface UserSettingMvpPresenter<V extends UserSettingView> extends MvpPresenter<V> {
  void onClickButtonEdit();

  void onClickButtonSave(User user,String oldPassword);

  void onClickButtonSelectImage();
}
