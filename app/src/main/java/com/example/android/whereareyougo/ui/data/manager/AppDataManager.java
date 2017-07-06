package com.example.android.whereareyougo.ui.data.manager;

import android.content.Context;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.model.DatabaseHelper;
import com.example.android.whereareyougo.ui.data.pref.PreferencesHelper;
import com.example.android.whereareyougo.ui.di.ApplicationContext;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
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
  public DatabaseReference getFavoriteVenuesRef() {
    return databaseHelper.getFavoriteVenuesRef();
  }

  @Override
  public Query getUsersByName(String name) {
    return databaseHelper.getUsersByName(name);
  }

  @Override
  public Query getUsersByPhoneNumber(String phoneNumber) {
    return databaseHelper.getUsersByPhoneNumber(phoneNumber);
  }

  @Override
  public void sendRequestAddFriend(String receiverId) {
    databaseHelper.sendRequestAddFriend(receiverId);
  }

  @Override
  public DatabaseReference getListRequestAddFriend() {
    return databaseHelper.getListRequestAddFriend();
  }

  @Override
  public DatabaseReference getUserInfo(String userId) {
    return databaseHelper.getUserInfo(userId);
  }

  @Override
  public Task<Void> saveUserFriend(String friendId) {
    return databaseHelper.saveUserFriend(friendId);
  }

  @Override
  public void removeRequestAddFriend(String requestId) {
    databaseHelper.removeRequestAddFriend(requestId);
  }

  @Override
  public Query getUserFriendById(String friendId) {
    return databaseHelper.getUserFriendById(friendId);
  }

  @Override
  public Query getUserRequestAddFriendById(String receiverId) {
    return databaseHelper.getUserRequestAddFriendById(receiverId);
  }

  @Override
  public void saveUserToListOfUserFriend(String friendId) {
    databaseHelper.saveUserToListOfUserFriend(friendId);
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
