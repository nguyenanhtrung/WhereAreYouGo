package com.example.android.whereareyougo.ui.data.manager;

import android.content.Context;

import com.example.android.whereareyougo.ui.data.database.entity.ChatMessage;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.MetaDataChats;
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

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class AppDataManager implements DataManager{

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
  public DatabaseReference getCurrentUserFriends() {
    return databaseHelper.getCurrentUserFriends();
  }

  @Override
  public DatabaseReference getConnectionRef() {
    return  databaseHelper.getConnectionRef();
  }

  @Override
  public void updateUserStatus(String status) {
    databaseHelper.updateUserStatus(status);
  }

  @Override
  public DatabaseReference getUserStatusRef() {
    return databaseHelper.getUserStatusRef();
  }

  @Override
  public DatabaseReference getFriendsRef(String userId) {
    return databaseHelper.getFriendsRef(userId);
  }

  @Override
  public void removeFavoriteVenueById(String venueId) {
    databaseHelper.removeFavoriteVenueById(venueId);
  }

  @Override
  public void deleteAllUserFavoriteVenues() {
    databaseHelper.deleteAllUserFavoriteVenues();
  }

  @Override
  public Task<Void> sendRequestFollow(String receiverId) {
    return databaseHelper.sendRequestFollow(receiverId);
  }

  @Override
  public DatabaseReference getListRequestFollow() {
    return databaseHelper.getListRequestFollow();
  }

  @Override
  public void deleteRequestFollowById(String senderId) {
    databaseHelper.deleteRequestFollowById(senderId);
  }

  @Override
  public void acceptRequestFollow(String senderId) {
    databaseHelper.acceptRequestFollow(senderId);
  }

  @Override
  public DatabaseReference getFriendsRefByFriendId(String friendId) {
    return databaseHelper.getFriendsRefByFriendId(friendId);
  }

  @Override
  public DatabaseReference getChatsReference() {
    return databaseHelper.getChatsReference();
  }

  @Override
  public DatabaseReference getMessagesByConversationId(String conversationId) {
    return databaseHelper.getMessagesByConversationId(conversationId);
  }

  @Override
  public void createConversationId(String conversationId) {
    databaseHelper.createConversationId(conversationId);
  }

  @Override
  public Task<Void> sendChatMessage(ChatMessage message, String conversationId) {
    return databaseHelper.sendChatMessage(message,conversationId);
  }

  @Override
  public Task<Void> updateLastMessageConversation(MetaDataChats metaDataChats, String conversationId) {
    return databaseHelper.updateLastMessageConversation(metaDataChats,conversationId);
  }

  @Override
  public DatabaseReference getMessagesReferenceByConversationId(String conversationId) {
    return databaseHelper.getMessagesReferenceByConversationId(conversationId);
  }

  @Override
  public DatabaseReference getUserStatusRefById(String userId) {
    return databaseHelper.getUserStatusRefById(userId);
  }

  @Override
  public StorageReference getUserMessagePhotosReference() {
    return databaseHelper.getUserMessagePhotosReference();
  }

  @Override
  public DatabaseReference getMembersReference() {
    return databaseHelper.getMembersReference();
  }

  @Override
  public void sendMessageNotification(String conversationId, String friendId) {
    databaseHelper.sendMessageNotification(conversationId,friendId);
  }

  @Override
  public DatabaseReference getMessageNotificationRef() {
    return databaseHelper.getMessageNotificationRef();
  }

  @Override
  public void createMembers(String conversationId, String friendId) {
    databaseHelper.createMembers(conversationId,friendId);
  }

  @Override
  public Query getConversationsOfCurrentUser() {
    return databaseHelper.getConversationsOfCurrentUser();
  }

  @Override
  public String getCurrentUserId() {
    return databaseHelper.getCurrentUserId();
  }

  @Override
  public DatabaseReference getUsersRef() {
    return databaseHelper.getUsersRef();
  }

  @Override
  public void removeUserMessageNotification(String conversationId) {
    databaseHelper.removeUserMessageNotification(conversationId);
  }

  @Override
  public Query getUserFriendsHasPermissionFollow() {
    return databaseHelper.getUserFriendsHasPermissionFollow();
  }

  @Override
  public void unfollowCurrentUser(String friendId) {
    databaseHelper.unfollowCurrentUser(friendId);
  }

  @Override
  public void removeFriendByFriendId(String friendId) {
    databaseHelper.removeFriendByFriendId(friendId);
  }

  @Override
  public void removeMembersDataByConversationId(String conversationId) {
    databaseHelper.removeMembersDataByConversationId(conversationId);
  }

  @Override
  public void updateUserLocation(String userId, String userLocation) {
    databaseHelper.updateUserLocation(userId,userLocation);
  }

  @Override
  public DatabaseReference getUserLocationRef(String userId) {
    return databaseHelper.getUserLocationRef(userId);
  }

  @Override
  public void saveUserFollowing(String senderId) {
    databaseHelper.saveUserFollowing(senderId);
  }

  @Override
  public DatabaseReference getFollowingsOfUser(String userId) {
    return databaseHelper.getFollowingsOfUser(userId);
  }

  @Override
  public DatabaseReference getFollowingsRefById(String userId) {
    return databaseHelper.getFollowingsRefById(userId);
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

  @Override
  public void removeFavoriteVenueId(String key) {
    preferencesHelper.removeFavoriteVenueId(key);
  }

  @Override
  public void deleteAllFavoriteVenueIdOnPreRef(List<FavoriteVenue> favoriteVenues) {
    preferencesHelper.deleteAllFavoriteVenueIdOnPreRef(favoriteVenues);
  }
}
