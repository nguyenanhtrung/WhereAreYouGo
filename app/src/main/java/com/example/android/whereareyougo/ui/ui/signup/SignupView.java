package com.example.android.whereareyougo.ui.ui.signup;

import com.example.android.whereareyougo.ui.ui.base.MvpView;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface SignupView extends MvpView {
  void showErrorOnEditTextEmail(String error);

  void showErrorOnEditTextPassword(String error);

  void showErrorOnEditTextName(String error);

  String getStringFromStringResource(int stringId);

  void showNotification(int messageId);

  void closeDialog();

  void updateUserInfoForLoginActivity(String email, String password);
}
