package com.example.android.whereareyougo.ui.data.pref;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface PreferencesHelper {
  void saveUserEmail(String email);
  void saveUserPassword(String password);
  String getUserEmail();
  String getUserPassword();
  void saveCheckRememberLogin(boolean isCheck);
  boolean getCheckRememberLogin();
  void saveFavoriteVenueId(String key, String venueId);
  String getFavoriteVenueId(String key);
}
