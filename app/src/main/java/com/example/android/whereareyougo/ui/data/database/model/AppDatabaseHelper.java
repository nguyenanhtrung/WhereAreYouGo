package com.example.android.whereareyougo.ui.data.database.model;

import android.preference.Preference;
import android.support.annotation.NonNull;


import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
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

import com.google.firebase.database.FirebaseDatabase;
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
        return firebaseAuth.signInWithEmailAndPassword(email,password);
    }

    public DatabaseReference getUserInfo() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference userRef = databaseReference.getRef().child("users").child(userId);
            return userRef;
        }

        return null;
    }



    public void removeRequestAddFriend(String requestId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference requestRef = databaseReference.getRef().child("requestfriend").child(userId)
                    .child(requestId);
            requestRef.removeValue();
        }
    }

    public void updateUserLocation(String userId, String userLocation){
        if (userId != null && userLocation != null){
            DatabaseReference usersRef = databaseReference.getRef().child("users")
                    .child(userId).child("currentlocation");
            usersRef.setValue(userLocation);
        }
    }

    public DatabaseReference getUserLocationRef(String userId){
        if (userId != null){
            DatabaseReference locationRef = databaseReference.getRef().child("users")
                    .child(userId)
                    .child("currentlocation");
            return locationRef;
        }
        return null;
    }

    public DatabaseReference getUserInfo(String userId) {
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

    public Task<Void> saveUserFriend(String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendsRef = databaseReference.getRef().child("friends").child(userId);
            Friend friend = new Friend(friendId, false);
            return friendsRef.child(friendId).setValue(friend);
        }

        return null;
    }

    public void saveUserToListOfUserFriend(String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendsRef = databaseReference.getRef().child("friends").child(friendId);
            Friend friend = new Friend(userId, false);
            friendsRef.child(userId).setValue(friend);
        }
    }

    public Query getUserFriendById(String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendsRef = databaseReference.getRef().child("friends")
                    .child(userId);
            return friendsRef.orderByChild("id").equalTo(friendId);
        }

        return null;
    }

    public DatabaseReference getCurrentUserFriends() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            return databaseReference.getRef().child("friends").child(userId);
        }

        return null;
    }


    public DatabaseReference getFriendsRefByFriendId(String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            return databaseReference.getRef().child("friends").child(friendId).child(userId);
        }

        return null;
    }

    public DatabaseReference getChatsReference() {
        //
        DatabaseReference conversationRef = databaseReference.getRef().child("chats");
        return conversationRef;
    }

    public Task<Void> sendChatMessage(ChatMessage message, String conversationId) {
        DatabaseReference messagesRef = databaseReference.getRef()
                .child("messages")
                .child(conversationId);
        return messagesRef.push().setValue(message);
    }

    public DatabaseReference getMembersReference() {
        return databaseReference.getRef().child("members");
    }

    public Task<Void> updateLastMessageConversation(MetaDataChats metaDataChats, String conversationId) {
        DatabaseReference chatsRef = databaseReference.getRef()
                .child("chats")
                .child(conversationId);

        return chatsRef.setValue(metaDataChats);
    }

    public DatabaseReference getMessagesReferenceByConversationId(String conversationId) {
        return databaseReference.getRef().child("messages").child(conversationId);
    }

    public void createConversationId(String conversationId) {
        DatabaseReference chatsRef = databaseReference.getRef().child("chats");
        MetaDataChats metaDataChats = new MetaDataChats();
        metaDataChats.setLastMessage("");
        metaDataChats.setLastSenderId("");
        metaDataChats.setTimeStamp("");
        //
        chatsRef.child(conversationId).setValue(metaDataChats);
    }

    public void sendMessageNotification(String conversationId, String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference messageNotificationRef = databaseReference.getRef()
                    .child("messagenotification")
                    .child(friendId)
                    .child(conversationId);
            messageNotificationRef.setValue(userId);
        }
    }

    public DatabaseReference getMessageNotificationRef() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            return databaseReference.getRef().child("messagenotification").child(userId);
        }
        return null;
    }

    public DatabaseReference getMessagesByConversationId(String conversationId) {
        DatabaseReference messagesRef = databaseReference.getRef()
                .child("messages").child(conversationId);

        return messagesRef;
    }


    public DatabaseReference getFriendsRef(String userId) {
        if (userId != null) {
            return databaseReference.getRef().child("friends").child(userId);
        }

        return null;
    }


    public void updateUserStatus(String status) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {

            DatabaseReference userRef = databaseReference.getRef().child("users").child(userId).child("status");
            userRef.setValue(status);
        }
    }

    public DatabaseReference getUserStatusRef() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        return databaseReference.getDatabase().getReference("/users/" + userId + "/status");
    }

    public DatabaseReference getUserStatusRefById(String userId) {
        return databaseReference.getDatabase().getReference("/users/" + userId + "/status");
    }


    public Query getUserRequestAddFriendById(String receiverId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference requestRef = databaseReference.getRef().child("requestfriend").child(receiverId);
            return requestRef.orderByChild("senderId").equalTo(userId);
        }

        return null;
    }

    public void removeFavoriteVenueById(String venueId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference favoriteVenueRef = databaseReference.getRef().child("favoritevenues").child(userId).child(venueId);
            favoriteVenueRef.removeValue();
        }
    }

    public StorageReference getUserPhotoReference() {
        StorageReference userPhotosRef = firebaseStorage.getReference().child("userphotos");
        return userPhotosRef;
    }

    public StorageReference getUserMessagePhotosReference() {
        StorageReference messagePhotosRef = firebaseStorage.getReference().child("messagephotos");
        return messagePhotosRef;
    }

    public void deleteAllUserFavoriteVenues() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference favoriteVenueRef = databaseReference.child("favoritevenues").child(userId);
            favoriteVenueRef.removeValue();
        }
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

    public void deleteRequestFollowById(String senderId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference requestRef = databaseReference.getRef().child("requestfollow").child(userId).child(senderId);
            requestRef.removeValue();
        }
    }

    public void acceptRequestFollow(String senderId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendsRef = databaseReference.getRef().child("friends").child(userId).child(senderId)
                    .child("permissionFollow");
            friendsRef.setValue(true);
        }
    }


    public Task<Void> sendRequestFollow(String receiverId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            RequestFollow requestFollow = new RequestFollow(userId);
            DatabaseReference requestFollowRef = databaseReference.getRef().child("requestfollow").child(receiverId)
                    .child(userId);

            return requestFollowRef.setValue(requestFollow);
        }

        return null;
    }

    public DatabaseReference getListRequestFollow() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference requestRef = databaseReference.getRef().child("requestfollow").child(userId);
            return requestRef;
        }

        return null;
    }

    public DatabaseReference getConnectionRef() {
        DatabaseReference connectionRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        return connectionRef;
    }

    public void createMembers(String conversationId, String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference membersRef = databaseReference.getRef()
                    .child("members").child(conversationId);

            membersRef.child(userId).setValue(true);
            membersRef.child(friendId).setValue(true);
        }
    }

    public Query getConversationsOfCurrentUser() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference membersRef = databaseReference.getRef()
                    .child("members");
            return membersRef.orderByChild(userId).equalTo(true);
        }
        return null;
    }

    public Query getUserFriendsHasPermissionFollow() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendRef = databaseReference.getRef()
                    .child("friends")
                    .child(userId);
            return friendRef.orderByChild("permissionFollow").equalTo(true);
        }
        return null;
    }

    public void removeUserMessageNotification(String conversationId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference messageNotificationRef = databaseReference.getRef().child("messagenotification")
                    .child(userId).child(conversationId);
            messageNotificationRef.removeValue();
        }
    }

    public void removeFriendByFriendId(String friendId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if(userId != null && friendId != null){
            DatabaseReference friendsRef = databaseReference.getRef().child("friends")
                    .child(userId)
                    .child(friendId);
            friendsRef.removeValue();
            //
            friendsRef = databaseReference.getRef().child("friends")
                    .child(friendId)
                    .child(userId);
            friendsRef.removeValue();


        }
    }

    public DatabaseReference getFollowingsRefById(String userId){
        if (userId != null){
            DatabaseReference followingsRef = databaseReference.getRef()
                    .child("followings")
                    .child(userId);
            return followingsRef;
        }
        return null;
    }

    public DatabaseReference getFollowingsOfUser(String userId){
        if (userId != null){
            DatabaseReference followingsRef=  databaseReference.getRef()
                    .child("followings")
                    .child(userId);
            return followingsRef;


        }
        return null;
    }

    public void saveUserFollowing(String senderId){
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (senderId != null && userId != null){
            DatabaseReference followingRef = databaseReference.getRef()
                    .child("followings")
                    .child(senderId)
                    .child(userId)
                    .child("followingid");

            followingRef.setValue(userId);
        }
    }



    public void removeMembersDataByConversationId(String conversationId){
        if (conversationId != null){
            DatabaseReference membersRef = databaseReference.getRef()
                    .child("members")
                    .child(conversationId);
            membersRef.removeValue();
        }
    }

    public void unfollowCurrentUser(String friendId) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        if (userId != null) {
            DatabaseReference friendRef = databaseReference.getRef()
                    .child("friends")
                    .child(userId)
                    .child(friendId)
                    .child("permissionFollow");
            friendRef.setValue(false);
        }
    }

    public String getCurrentUserId() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    public DatabaseReference getUsersRef() {
        return databaseReference.getRef().child("users");
    }


}
