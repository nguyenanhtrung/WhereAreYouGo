package com.example.android.whereareyougo.ui.data.database.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask.TaskSnapshot;
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

  public DatabaseReference getFavoriteVenuesRef(){
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    String userId = currentUser.getUid();
    //
    DatabaseReference favoriteVenuesRef = databaseReference.getRef().child("favoritevenues").child(userId);

    return favoriteVenuesRef;
  }

  public Task<ProviderQueryResult> getProviderForEmail(String email) {
    if (email != null) {
      return firebaseAuth.fetchProvidersForEmail(email);
    }
    return null;
  }

  public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
    return firebaseAuth.createUserWithEmailAndPassword(email, password);
  }

  public void writeNewUser(String userId, String email, String password, String name) {
    DatabaseReference userRef = databaseReference.getRoot();
    User user = new User(name, email, password);
    userRef.child("users").push();

    userRef.child("users").child(userId).setValue(user);

  }

  public void saveFavoriteVenue(FavoriteVenue favoriteVenue){
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    String userId = currentUser.getUid();

    DatabaseReference favoriteVenueRef = databaseReference.getRef()
            .child("favoritevenues")
            .child(userId)
            .child(favoriteVenue.getVenueId());

    favoriteVenueRef.setValue(favoriteVenue);
  }

  public Task<AuthResult> signInWithEmailAndPassworđ(String email, String password) {
    return firebaseAuth.signInWithEmailAndPassword(email, password);
  }

  public DatabaseReference getUserInfo() {
    String userId = firebaseAuth.getCurrentUser().getUid();
    if (userId != null) {
      DatabaseReference userRef = databaseReference.getRef().child("users").child(userId);
      return userRef;
    }

    return null;
  }


  public DatabaseReference getUserReference() {
    String userId = firebaseAuth.getCurrentUser().getUid();
    DatabaseReference userRef = databaseReference.child("users").child(userId);

    return userRef;
  }

  public StorageReference getUserPhotoReference() {
    StorageReference userPhotosRef = firebaseStorage.getReference().child("userphotos");
    return userPhotosRef;
  }

  public Query getUsersByName(String name){
    DatabaseReference userRef = databaseReference.getRef().child("users");
    return userRef.orderByChild("name")
                  .startAt(name)
                  .endAt(name = "\uf8ff");
  }

  public Query getUsersByPhoneNumber(String phoneNumber){
    DatabaseReference userRef = databaseReference.getRef().child("users");
    return userRef.orderByChild("phoneNumber").equalTo(phoneNumber);
  }

  public void changeUserPassword(final String email, final String oldPassword,
      final String newPassword) {
    final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    final AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

    if (currentUser != null && credential != null) {

      currentUser.reauthenticate(credential)
          .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if (task.isSuccessful()) {
                currentUser.updatePassword(newPassword);
              }

            }
          }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          //

        }
      });

    }

  }


}
