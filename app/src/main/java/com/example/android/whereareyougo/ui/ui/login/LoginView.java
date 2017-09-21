package com.example.android.whereareyougo.ui.ui.login;

import com.example.android.whereareyougo.ui.ui.base.MvpView;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface LoginView extends MvpView {
  void openSignupFragment();

  void openMainActivity();

  void showNotification(int messageId);

  boolean getValueFrormCheckRemember();

  void showDisconnectNetworkDialog(String email, String password);

  void dismissDisconnectNetworkDialog();

  void setVisibilityForComponents(int visibility);

  String getUserPassword();

  void setEditTextPassword(String password);

  void setImageShowPassword(int imageId);

  void showUserPassword();

  void hideUserPassword();
}
