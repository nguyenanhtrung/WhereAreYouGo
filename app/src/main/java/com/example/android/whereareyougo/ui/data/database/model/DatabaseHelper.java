package com.example.android.whereareyougo.ui.data.database.model;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public interface DatabaseHelper {

  Task<ProviderQueryResult> getProviderForEmail(String email);

  Task<AuthResult> createUserWithEmailAndPassword(String email, String password);

  void writeNewUser(String userId, String email, String password, String name);

  Task<AuthResult> signInWithEmailAndPassworđ(String email, String password);

  DatabaseReference getUserInfo();

  DatabaseReference getUserReference();

  StorageReference getUserPhotoReference();

  void changeUserPassword(final String email, final String oldPassword,
      final String newPassword);

  void saveFavoriteVenue(FavoriteVenue favoriteVenue);

  DatabaseReference getFavoriteVenuesRef();


}
