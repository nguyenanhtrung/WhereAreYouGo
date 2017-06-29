package com.example.android.whereareyougo.ui.data.pref;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.example.android.whereareyougo.ui.di.PreferencesInfo;
import com.example.android.whereareyougo.ui.utils.MyKey;
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
    sharedPreferences.edit().putString(MyKey.PREF_SAVE_EMAIL,email).apply();
  }

  public void saveFavoriteVenueId(String key, String venueId){
    sharedPreferences.edit().putString(key,venueId).apply();
  }

  public String getFavoriteVenueId(String key){
    return sharedPreferences.getString(key,"");
  }

  @Override
  public void saveUserPassword(String password) {
    sharedPreferences.edit().putString(MyKey.PREF_SAVE_PASSWORD,password).apply();
  }

  @Override
  public String getUserEmail() {
    return sharedPreferences.getString(MyKey.PREF_SAVE_EMAIL,"");
  }

  @Override
  public String getUserPassword() {
    return sharedPreferences.getString(MyKey.PREF_SAVE_PASSWORD,"");
  }

  @Override
  public void saveCheckRememberLogin(boolean isCheck) {
    sharedPreferences.edit().putBoolean(MyKey.PREF_LOGIN_MODE,isCheck).apply();
  }

  @Override
  public boolean getCheckRememberLogin() {
    return sharedPreferences.getBoolean(MyKey.PREF_LOGIN_MODE,false);
  }
}
