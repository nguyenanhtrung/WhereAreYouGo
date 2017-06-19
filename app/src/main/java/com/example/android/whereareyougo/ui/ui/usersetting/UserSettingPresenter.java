package com.example.android.whereareyougo.ui.ui.usersetting;


import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/04/2017.
 */

public class UserSettingPresenter<V extends UserSettingView> extends BasePresenter<V> implements
    UserSettingMvpPresenter<V> {




  @Inject
  public UserSettingPresenter(
      DataManager dataManager)
       {
    super(dataManager);

  }



}
