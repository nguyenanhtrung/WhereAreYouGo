package com.example.android.whereareyougo.ui.ui.signup;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface SignupMvpPresenter<V extends SignupView> extends MvpPresenter<V> {
  void onClickButtonSignup(String email, String password, String name);


}
