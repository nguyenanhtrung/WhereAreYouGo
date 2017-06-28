package com.example.android.whereareyougo.ui.di.module;

import android.app.Activity;
import android.content.Context;
import com.example.android.whereareyougo.ui.di.ActivityContext;
import com.example.android.whereareyougo.ui.di.PerActivity;
import com.example.android.whereareyougo.ui.ui.login.LoginMvpPresenter;
import com.example.android.whereareyougo.ui.ui.login.LoginPresenter;
import com.example.android.whereareyougo.ui.ui.login.LoginView;
import com.example.android.whereareyougo.ui.ui.main.MainMvpPresenter;
import com.example.android.whereareyougo.ui.ui.main.MainPresenter;
import com.example.android.whereareyougo.ui.ui.main.MainView;
import com.example.android.whereareyougo.ui.ui.map.MapMvpPresenter;
import com.example.android.whereareyougo.ui.ui.map.MapMvpView;
import com.example.android.whereareyougo.ui.ui.map.MapPresenter;
import com.example.android.whereareyougo.ui.ui.signup.SignupMvpPresenter;
import com.example.android.whereareyougo.ui.ui.signup.SignupPresenter;
import com.example.android.whereareyougo.ui.ui.signup.SignupView;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingMvpPresenter;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingPresenter;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingView;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailMvpPresenter;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailPresenter;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */
@Module
public class ActivityModule {

  private Activity activity;

  public ActivityModule(Activity activity) {
    this.activity = activity;
  }

  @Provides
  @ActivityContext
  Context provideContext() {
    return activity;
  }

  @Provides
  Activity provideActivity() {
    return activity;
  }

  @Provides
  @PerActivity
  LoginMvpPresenter<LoginView> provideLoginPresenter(LoginPresenter<LoginView> presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  SignupMvpPresenter<SignupView> provideSignupPresenter(SignupPresenter<SignupView> presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  MainMvpPresenter<MainView> provideMainPresenter(MainPresenter<MainView> presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  MapMvpPresenter<MapMvpView> provideMapPresenter(MapPresenter<MapMvpView> presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  UserSettingMvpPresenter<UserSettingView> provideUserSettingPresenter(UserSettingPresenter<UserSettingView> presenter) {
    return presenter;
  }

  @Provides
  @PerActivity
  VenueDetailMvpPresenter<VenueDetailView> provideVenueDetailDialogPresenter(VenueDetailPresenter<VenueDetailView> presenter) {
    return presenter;
  }


}
