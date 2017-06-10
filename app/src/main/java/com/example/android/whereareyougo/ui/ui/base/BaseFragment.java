package com.example.android.whereareyougo.ui.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class BaseFragment extends android.support.v4.app.Fragment implements MvpView {

  private BaseActivity activity;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof BaseActivity) {
      BaseActivity activity = (BaseActivity) context;
      this.activity = activity;
      activity.onFragmentAttached();
    }
  }

  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public void onDetach() {
    super.onDetach();
    activity = null;
  }

  @Override
  public boolean isNetworkConnected() {
    if (activity != null) {
      return activity.isNetworkConnected();
    }
    return false;
  }

  @Override
  public void onError(String message,Activity activity) {

  }

  @Override
  public void hideKeyboard() {

  }

  public interface Callback{
    void onFragmentAttached();

    void onFragmentDetached(String tag);

    ActivityComponent getActivityComponent();

    void setTitleToolbar(String title);
  }
}