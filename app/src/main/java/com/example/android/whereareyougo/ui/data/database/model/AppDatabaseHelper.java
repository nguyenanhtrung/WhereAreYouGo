package com.example.android.whereareyougo.ui.data.database.model;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class AppDatabaseHelper implements DatabaseHelper {
  FirebaseAuth firebaseAuth;
  DatabaseReference databaseReference;
  FirebaseStorage firebaseStorage;

  @Inject
  public AppDatabaseHelper(FirebaseAuth firebaseAuth,
      DatabaseReference databaseReference,
      FirebaseStorage firebaseStorage) {
    this.firebaseAuth = firebaseAuth;
    this.databaseReference = databaseReference;
    this.firebaseStorage = firebaseStorage;
  }

  public Task<ProviderQueryResult> getProviderForEmail(String email){
    if (email != null){
      return firebaseAuth.fetchProvidersForEmail(email);
    }
    return null;
  }

}
