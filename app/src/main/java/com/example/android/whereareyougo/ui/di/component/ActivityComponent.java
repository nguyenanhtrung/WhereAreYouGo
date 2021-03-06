package com.example.android.whereareyougo.ui.di.component;

import com.example.android.whereareyougo.ui.di.PerActivity;
import com.example.android.whereareyougo.ui.di.module.ActivityModule;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendDialogFragment;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingFragment;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogFragment;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueFragment;
import com.example.android.whereareyougo.ui.ui.followers.FollowersFragment;
import com.example.android.whereareyougo.ui.ui.followings.FollowingsFragment;
import com.example.android.whereareyougo.ui.ui.followingselection.FollowingsSelectionDialogFragment;
import com.example.android.whereareyougo.ui.ui.friendsmap.FriendsMapFragment;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendFragment;
import com.example.android.whereareyougo.ui.ui.locationupdatesetting.LocationUpdateSettingFragment;
import com.example.android.whereareyougo.ui.ui.login.LoginActivity;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
import com.example.android.whereareyougo.ui.ui.messages.MessagesFragment;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsFragment;
import com.example.android.whereareyougo.ui.ui.notify.NotifyFragment;
import com.example.android.whereareyougo.ui.ui.profile.ProfileDialogFragment;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendFragment;
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowFragment;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenueFragment;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingFragment;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailDialogFragment;

import dagger.Component;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
  void inject(LoginActivity activity);

  void inject(MainActivity activity);

  void inject(SignupDialogFragment fragment);

  void inject(MapFragment fragment);

  void inject(UserSettingFragment fragment);

  void inject(VenueDetailDialogFragment fragment);

  void inject(ListFavoriteVenueFragment fragment);

  void inject(ListFriendFragment fragment);

  void inject(AddFriendDialogFragment fragment);

  void inject(NotificationsFragment fragment);

  void inject(RequestAddFriendFragment fragment);

  void inject(MessagesFragment fragment);

  void inject(NotifyFragment fragment);

  void inject(ProfileDialogFragment fragment);

  void inject(SearchVenueFragment fragment);

  void inject(ListRequestFollowFragment fragment);

  void inject(ChatDialogFragment fragment);

  void inject(FollowersFragment fragment);

  void inject(AppSettingFragment fragment);

  void inject(LocationUpdateSettingFragment fragment);

  void inject(FollowingsFragment fragment);

  void inject(FriendsMapFragment fragment);

  void inject(FollowingsSelectionDialogFragment fragment);
}