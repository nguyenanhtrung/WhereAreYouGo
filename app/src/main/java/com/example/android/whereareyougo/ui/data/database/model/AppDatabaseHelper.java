package com.example.android.whereareyougo.ui.data.database.model;

import android.support.annotation.NonNull;


import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
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
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.Query;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


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

    public DatabaseReference getFavoriteVenuesRef() {
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

    public void sendRequestAddFriend(String receiverId) {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        RequestAddFriend requestAddFriend = new RequestAddFriend(currentUserId, false);
        DatabaseReference requestFriendRef = databaseReference.getRef().child("requestfriend").child(receiverId);

        requestFriendRef.child(currentUserId).setValue(requestAddFriend);
    }

    public DatabaseReference getListRequestAddFriend() {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        return databaseReference.getRef().child("requestfriend").child(currentUserId);
    }

    public void writeNewUser(String userId, String email, String password, String name) {
        DatabaseReference userRef = databaseReference.getRoot();
        User user = new User(name, email, password);
        user.setUserID(firebaseAuth.getCurrentUser().getUid());
        userRef.child("users").push();

        userRef.child("users").child(userId).setValue(user);

    }


    public void saveFavoriteVenue(FavoriteVenue favoriteVenue) {
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

    public void removeRequestAddFriend(String requestId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            DatabaseReference requestRef = databaseReference.getRef().child("requestfriend").child(userId)
                    .child(requestId);
            requestRef.removeValue();
        }
    }

    public DatabaseReference getUserInfo(String userId){
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

    public Task<Void> saveUserFriend(String friendId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            DatabaseReference friendsRef = databaseReference.getRef().child("friends").child(userId);
            Friend friend = new Friend(friendId,false);
            return friendsRef.child(friendId).setValue(friend);
        }

        return null;
    }

    public void saveUserToListOfUserFriend(String friendId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            DatabaseReference friendsRef = databaseReference.getRef().child("friends").child(friendId);
            Friend friend = new Friend(userId,false);
            friendsRef.child(userId).setValue(friend);
        }
    }

    public Query getUserFriendById(String friendId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            DatabaseReference friendsRef = databaseReference.getRef().child("friends")
                    .child(userId);
            return friendsRef.orderByChild("id").equalTo(friendId);
        }

        return null;
    }

    public DatabaseReference getCurrentUserFriends(){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            return databaseReference.getRef().child("friends").child(userId);
        }

        return null;
    }

    public Query getUserRequestAddFriendById(String receiverId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null){
            DatabaseReference requestRef = databaseReference.getRef().child("requestfriend").child(receiverId);
            return requestRef.orderByChild("senderId").equalTo(userId);
        }

        return null;
    }



    public StorageReference getUserPhotoReference() {
        StorageReference userPhotosRef = firebaseStorage.getReference().child("userphotos");
        return userPhotosRef;
    }



    public Query getUsersByName(String name) {
        DatabaseReference userRef = databaseReference.getRef().child("users");
        return userRef.orderByChild("name")
                .startAt(name)
                .endAt(name = "\uf8ff");
    }

    public Query getUsersByPhoneNumber(String phoneNumber) {
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
