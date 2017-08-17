package com.example.android.whereareyougo.ui.di.module;

import android.app.Activity;
import android.content.Context;

import com.example.android.whereareyougo.ui.di.ActivityContext;
import com.example.android.whereareyougo.ui.di.PerActivity;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendMvpPresenter;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendPresenter;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendView;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingMvpPresenter;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingPresenter;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingView;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogMvpPresenter;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogPresenter;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogView;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueMvpPresenter;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenuePresenter;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueView;
import com.example.android.whereareyougo.ui.ui.followers.FollowersMvpPresenter;
import com.example.android.whereareyougo.ui.ui.followers.FollowersPresenter;
import com.example.android.whereareyougo.ui.ui.followers.FollowersView;
import com.example.android.whereareyougo.ui.ui.followings.FollowingsMvpPresenter;
import com.example.android.whereareyougo.ui.ui.followings.FollowingsPresenter;
import com.example.android.whereareyougo.ui.ui.followings.FollowingsView;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendMvpPresenter;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendPresenter;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendView;
import com.example.android.whereareyougo.ui.ui.locationupdatesetting.LocationUpdateSettingMvpPresenter;
import com.example.android.whereareyougo.ui.ui.locationupdatesetting.LocationUpdateSettingPresenter;
import com.example.android.whereareyougo.ui.ui.locationupdatesetting.LocationUpdateSettingView;
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
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowMvpPresenter;
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowPresenter;
import com.example.android.whereareyougo.ui.ui.requestfollow.ListRequestFollowView;
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

    @Provides
    @PerActivity
    ListRequestFollowMvpPresenter<ListRequestFollowView> provideListRequestFollowPresenter(ListRequestFollowPresenter<ListRequestFollowView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    ChatDialogMvpPresenter<ChatDialogView> provideChatDialogPresenter(ChatDialogPresenter<ChatDialogView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FollowersMvpPresenter<FollowersView> provideFollowersPresenter(FollowersPresenter<FollowersView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    AppSettingMvpPresenter<AppSettingView> provideAppSettingPresenter(AppSettingPresenter<AppSettingView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    LocationUpdateSettingMvpPresenter<LocationUpdateSettingView> provideLocationUpdateSettingPresenter(LocationUpdateSettingPresenter<LocationUpdateSettingView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    FollowingsMvpPresenter<FollowingsView> provideFollowingsPresenter(FollowingsPresenter<FollowingsView> presenter) {
        return presenter;
    }
}
