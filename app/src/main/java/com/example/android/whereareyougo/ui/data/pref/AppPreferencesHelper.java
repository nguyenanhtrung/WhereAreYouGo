package com.example.android.whereareyougo.ui.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.example.android.whereareyougo.ui.di.PreferencesInfo;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class AppPreferencesHelper implements PreferencesHelper {
  private final SharedPreferences sharedPreferences;

  @Inject
  public AppPreferencesHelper(@ApplicationContext Context context, @PreferencesInfo  String preferencesName) {
    sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
  }


  @Override
  public void saveUserEmail(String email) {

  }

  @Override
  public void saveUserPassword(String password) {

  }

  @Override
  public String getUserEmail() {
    return null;
  }

  @Override
  public String getUserPassword() {
    return null;
  }

  @Override
  public void saveCheckRememberLogin(boolean isCheck) {

  }

  @Override
  public boolean getCheckRememberLogin() {
    return false;
  }
}
