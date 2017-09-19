package com.example.android.whereareyougo.ui.ui.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;
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
  private MaterialDialog loadingDialog;

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


  @TargetApi(Build.VERSION_CODES.M)
  public void requestPermissionSafely(String[] permission, int requestCode){
    requestPermissions(permission,requestCode);
  }

  @TargetApi(Build.VERSION_CODES.M)
  public boolean hasPermission(String permission) {
    return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
            checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
  }

  @Override
  public void showLoading() {
      loadingDialog = new MaterialDialog.Builder(this)
              .progress(true,10)
              .build();
      loadingDialog.show();
  }

  @Override
  public void hideLoading() {
    if (loadingDialog != null && loadingDialog.isShowing()){
      loadingDialog.cancel();
    }
  }

  private void showSnackBar(String message){
    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT);
    View sbView = snackbar.getView();
    TextView textView = (TextView) sbView
            .findViewById(android.support.design.R.id.snackbar_text);
    textView.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
    snackbar.show();
  }

  @Override
  public boolean isNetworkConnected() {
    return NetworkUtil.isNetworkConnected(getApplicationContext());
  }

  @Override
  public void onError(String message) {
    //Commons.showErrorOnSnackBar(message,activity);
    if (message != null){
      showSnackBar(message);
    }
  }

  public void onError(int messageId){
    showSnackBar(getResources().getString(messageId));
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

