package com.example.android.whereareyougo.ui.ui.login;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class LoginPresenter<V extends LoginView> extends BasePresenter<V> implements
    LoginMvpPresenter<V> {

  @Inject
  public LoginPresenter(DataManager dataManager) {
    super(dataManager);
  }


  @Override
  public void onClickButtonSignup() {
    getMvpView().openSignupFragment();
  }
}
