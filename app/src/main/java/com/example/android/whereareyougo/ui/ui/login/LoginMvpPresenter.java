package com.example.android.whereareyougo.ui.ui.login;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface LoginMvpPresenter<V extends LoginView> extends MvpPresenter<V> {
  void onClickButtonSignup();

  void onCLickButtonSignin(String email, String password);

  void loginWithLoginRemember();
}
