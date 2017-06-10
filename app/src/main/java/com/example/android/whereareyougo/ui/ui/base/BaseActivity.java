package com.example.android.whereareyougo.ui.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.example.android.whereareyougo.ui.MyApplication;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.di.component.DaggerActivityComponent;
import com.example.android.whereareyougo.ui.di.module.ActivityModule;
import com.example.android.whereareyougo.ui.utils.NetworkUtil;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView,BaseFragment.Callback {

  private ActivityComponent activityComponent;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityComponent = DaggerActivityComponent.builder()
        .activityModule(new ActivityModule(this))
        .applicationComponent(((MyApplication) getApplication()).getComponent())
        .build();


  }

  public ActivityComponent getActivityComponent(){
    return activityComponent;
  }




  @Override
  public void showLoading() {

  }

  @Override
  public void hideLoading() {

  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtil.isNetworkConnected(getApplicationContext());
  }

  @Override
  public void onError(String message,Activity activity) {
    //Commons.showErrorOnSnackBar(message,activity);
  }

  @Override
  public void hideKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager imm = (InputMethodManager)
          getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }

  @Override
  public void onFragmentAttached() {

  }

  @Override
  public void onFragmentDetached(String tag) {

  }


}

