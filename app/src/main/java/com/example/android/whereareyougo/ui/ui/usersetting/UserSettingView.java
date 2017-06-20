package com.example.android.whereareyougo.ui.ui.usersetting;

import com.example.android.whereareyougo.ui.ui.base.MvpView;


/**
 * Created by nguyenanhtrung on 13/04/2017.
 */

public interface UserSettingView extends MvpView {
  void setButtonEdiVisibility(boolean isVisible);

  void setButtonSaveVisibility(boolean isVisible);

  void setEnabledForEditTexts(boolean isEnable);

  void showErrorOnEditTextEmail(String error);

  void showErrorOnEditTextName(String error);

  void showErrorOnEditTextPassword(String error);

  void showErrorOnEditTextPhone(String error);

  String getStringFromStringResource(int stringId);

  void setEnableForButtonSelectImage(boolean isEnable);

  void pickImageFromGallery();
}
