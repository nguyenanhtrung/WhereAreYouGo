package com.example.android.whereareyougo.ui.ui.base;

import android.app.Activity;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface MvpView {
  void showLoading();

  void hideLoading();

  boolean isNetworkConnected();

  void onError(String message, Activity activity);

  void hideKeyboard();
}
