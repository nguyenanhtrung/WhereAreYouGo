package com.example.android.whereareyougo.ui.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.android.whereareyougo.ui.di.ActivityContext;
import com.example.android.whereareyougo.ui.di.PerActivity;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendMvpPresenter;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendPresenter;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendView;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueMvpPresenter;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenuePresenter;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueView;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendMvpPresenter;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendPresenter;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendView;
import com.example.android.whereareyougo.ui.ui.login.LoginMvpPresenter;
import com.example.android.whereareyougo.ui.ui.login.LoginPresenter;
import com.example.android.whereareyougo.ui.ui.login.LoginView;
import com.example.android.whereareyougo.ui.ui.main.MainMvpPresenter;
import com.example.android.whereareyougo.ui.ui.main.MainPresenter;
import com.example.android.whereareyougo.ui.ui.main.MainView;
import com.example.android.whereareyougo.ui.ui.map.MapMvpPresenter;
import com.example.android.whereareyougo.ui.ui.map.MapMvpView;
import com.example.android.whereareyougo.ui.ui.map.MapPresenter;
import com.example.android.whereareyougo.ui.ui.messages.MessagesMvpPresenter;
import com.example.android.whereareyougo.ui.ui.messages.MessagesPresenter;
import com.example.android.whereareyougo.ui.ui.messages.MessagesView;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsMvpPresenter;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsPresenter;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsView;
import com.example.android.whereareyougo.ui.ui.notify.NotifyMvpPresenter;
import com.example.android.whereareyougo.ui.ui.notify.NotifyPresenter;
import com.example.android.whereareyougo.ui.ui.notify.NotifyView;
import com.example.android.whereareyougo.ui.ui.profile.ProfileMvpPresenter;
import com.example.android.whereareyougo.ui.ui.profile.ProfilePresenter;
import com.example.android.whereareyougo.ui.ui.profile.ProfileView;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendMvpPresenter;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendPresenter;
import com.example.android.whereareyougo.ui.ui.requestaddfriend.RequestAddFriendView;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenueMvpPresenter;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenuePresenter;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenueView;
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

    @Provides
    @PerActivity
    ListFavoriteVenueMvpPresenter<ListFavoriteVenueView> provideListFavoriteVenuePresenter(ListFavoriteVenuePresenter<ListFavoriteVenueView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ListFriendMvpPresenter<ListFriendView> provideListFriendPresenter(ListFriendPresenter<ListFriendView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AddFriendMvpPresenter<AddFriendView> provideAddFriendPresenter(AddFriendPresenter<AddFriendView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NotificationsMvpPresenter<NotificationsView> provideNotificationsPresenter(NotificationsPresenter<NotificationsView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    RequestAddFriendMvpPresenter<RequestAddFriendView> provideRequestAddFriendPresenter(RequestAddFriendPresenter<RequestAddFriendView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    NotifyMvpPresenter<NotifyView> provideNotifyPresenter(NotifyPresenter<NotifyView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MessagesMvpPresenter<MessagesView> provideMessagesPresenter(MessagesPresenter<MessagesView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ProfileMvpPresenter<ProfileView> provideProfilePresenter(ProfilePresenter<ProfileView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    SearchVenueMvpPresenter<SearchVenueView> provideSearchVenuePresenter(SearchVenuePresenter<SearchVenueView> presenter) {
        return presenter;
    }
}
