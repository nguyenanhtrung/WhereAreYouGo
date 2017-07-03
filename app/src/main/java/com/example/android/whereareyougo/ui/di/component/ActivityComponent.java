package com.example.android.whereareyougo.ui.di.component;

import com.example.android.whereareyougo.ui.di.PerActivity;
import com.example.android.whereareyougo.ui.di.module.ActivityModule;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendDialogFragment;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueFragment;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendFragment;
import com.example.android.whereareyougo.ui.ui.login.LoginActivity;
import com.example.android.whereareyougo.ui.ui.main.MainActivity;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
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

}