package com.example.android.whereareyougo.ui.data.database.model;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
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

  Query getUsersByName(String name);

  Query getUsersByPhoneNumber(String phoneNumber);

  void sendRequestAddFriend(String receiverId);

  DatabaseReference getListRequestAddFriend();

  DatabaseReference getUserInfo(String userId);

  Task<Void> saveUserFriend(String friendId);

  void removeRequestAddFriend(String requestId);

  Query getUserFriendById(String friendId);

  Query getUserRequestAddFriendById(String receiverId);

  void saveUserToListOfUserFriend(String friendId);

  DatabaseReference getCurrentUserFriends();

  DatabaseReference getConnectionRef();

  void updateUserStatus(String status);

  DatabaseReference getUserStatusRef();

  DatabaseReference getFriendsRef(String userId);

  void removeFavoriteVenueById(String venueId);

  void deleteAllUserFavoriteVenues();

  Task<Void> sendRequestFollow(String receiverId);

  DatabaseReference getListRequestFollow();

  void deleteRequestFollowById(String senderId);

  void acceptRequestFollow(String senderId);

  DatabaseReference getFriendsRefByFriendId(String friendId);

  DatabaseReference getChatsReference();

  DatabaseReference getMessagesByConversationId(String conversationId);

  void createConversationId(String conversationId);

  Task<Void> sendChatMessage(ChatMessage message, String conversationId);

  Task<Void> updateLastMessageConversation(MetaDataChats metaDataChats, String conversationId);

  DatabaseReference getMessagesReferenceByConversationId(String conversationId);

  DatabaseReference getUserStatusRefById(String userId);

  StorageReference getUserMessagePhotosReference();

  DatabaseReference getMembersReference();

  void sendMessageNotification(String conversationId, String friendId);

  DatabaseReference getMessageNotificationRef();
}
