package com.example.android.whereareyougo.ui.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class BaseFragment extends Fragment implements MvpView {

  private BaseActivity activity;
  private MaterialDialog loadingDialog;

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
    if (loadingDialog == null){
      loadingDialog = new MaterialDialog.Builder(getActivity())
              .progress(true,4)
              .build();
    }
    loadingDialog.show();
  }

  @Override
  public void hideLoading() {
    if (loadingDialog.isShowing()){
      loadingDialog.dismiss();
    }
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
  public void onError(String message) {
    if (activity != null){
      activity.onError(message);
    }
  }

  @Override
  public void hideKeyboard() {
    if (activity != null){
      activity.hideKeyboard();
    }
  }

  @Override
  public void onError(int messageId) {
    if (activity != null){
      activity.onError(messageId);
    }
  }

  public interface Callback{
    void onFragmentAttached();

    void onFragmentDetached(String tag);

    ActivityComponent getActivityComponent();

    void setTitleToolbar(String title);
  }
}