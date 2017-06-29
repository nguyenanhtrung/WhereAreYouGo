package com.example.android.whereareyougo.ui.data.manager;

import android.content.Context;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.data.pref.PreferencesHelper;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class AppDataManager implements DataManager {

  private Context context;
  private DatabaseHelper databaseHelper;
  private PreferencesHelper preferencesHelper;

  @Inject
  public AppDataManager(@ApplicationContext Context context, DatabaseHelper databaseHelper,
      PreferencesHelper preferencesHelper) {
    this.context = context;
    this.databaseHelper = databaseHelper;
    this.preferencesHelper = preferencesHelper;
  }

  @Override
  public Task<ProviderQueryResult> getProviderForEmail(String email) {
    return databaseHelper.getProviderForEmail(email);
  }

  @Override
  public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
    return databaseHelper.createUserWithEmailAndPassword(email,password);
  }

  @Override
  public void writeNewUser(String userId, String email, String password, String name) {
    databaseHelper.writeNewUser(userId,email,password,name);
  }

  @Override
  public Task<AuthResult> signInWithEmailAndPassworđ(String email, String password) {
    return databaseHelper.signInWithEmailAndPassworđ(email,password);
  }

  @Override
  public DatabaseReference getUserInfo() {
    return databaseHelper.getUserInfo();
  }

  @Override
  public DatabaseReference getUserReference() {
    return databaseHelper.getUserReference();
  }

  @Override
  public StorageReference getUserPhotoReference() {
    return databaseHelper.getUserPhotoReference();
  }

  @Override
  public void changeUserPassword(String email, String oldPassword,
      String newPassword) {
    databaseHelper.changeUserPassword(email,oldPassword,newPassword);
  }

  @Override
  public void saveFavoriteVenue(FavoriteVenue favoriteVenue) {
    databaseHelper.saveFavoriteVenue(favoriteVenue);
  }


  @Override
  public void saveUserEmail(String email) {
    preferencesHelper.saveUserEmail(email);
  }

  @Override
  public void saveUserPassword(String password) {
    preferencesHelper.saveUserPassword(password);
  }

  @Override
  public String getUserEmail() {
    return preferencesHelper.getUserEmail();
  }

  @Override
  public String getUserPassword() {
    return preferencesHelper.getUserPassword();
  }

  @Override
  public void saveCheckRememberLogin(boolean isCheck) {
    preferencesHelper.saveCheckRememberLogin(isCheck);
  }

  @Override
  public boolean getCheckRememberLogin() {
    return preferencesHelper.getCheckRememberLogin();
  }

  @Override
  public void saveFavoriteVenueId(String key, String venueId) {
    preferencesHelper.saveFavoriteVenueId(key,venueId);
  }

  @Override
  public String getFavoriteVenueId(String key) {
    return preferencesHelper.getFavoriteVenueId(key);
  }
}
