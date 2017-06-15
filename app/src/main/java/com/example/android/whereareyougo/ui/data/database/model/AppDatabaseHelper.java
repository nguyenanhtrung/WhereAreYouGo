package com.example.android.whereareyougo.ui.data.database.model;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

  public Task<AuthResult> createUserWithEmailAndPassword(String email, String password){
    return firebaseAuth.createUserWithEmailAndPassword(email,password);
  }

  public void writeNewUser(String userId, String email, String password, String name){
    DatabaseReference userRef = databaseReference.getRoot();
    User user = new User(name, email,password);
    userRef.child("users").push();

    userRef.child("users").child(userId).setValue(user);

  }

  public Task<AuthResult> signInWithEmailAndPassworđ(String email, String password){
    return firebaseAuth.signInWithEmailAndPassword(email,password);
  }

  public DatabaseReference getUserInfo(){
    String userId = firebaseAuth.getCurrentUser().getUid();
    if (userId != null){
      DatabaseReference userRef = databaseReference.child("users").child(userId);
      return userRef;
    }

    return null;
  }

}
