package com.example.android.whereareyougo.ui.ui.main;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MainView extends MvpView {

  void updateUserInfo(User user);

  void closeUserSettingDrawer();

  void openUserSettingDrawer();

  void openUserSettingFragment();
}
