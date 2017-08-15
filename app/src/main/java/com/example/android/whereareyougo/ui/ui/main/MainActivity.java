package com.example.android.whereareyougo.ui.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.android.whereareyougo.R;

import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendDialogFragment;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingFragment;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogFragment;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueFragment;
import com.example.android.whereareyougo.ui.ui.followers.FollowersFragment;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendFragment;
import com.example.android.whereareyougo.ui.ui.map.ListVenueDialogFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment.InteractionWithMapFragment;
import com.example.android.whereareyougo.ui.ui.messages.MessagesFragment;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsFragment;
import com.example.android.whereareyougo.ui.ui.profile.ProfileDialogFragment;
import com.example.android.whereareyougo.ui.ui.receiver.NetworkReceiver;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenueFragment;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment.InteractionWithSignupFragment;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingFragment;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailDialogFragment;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
//import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeader.OnAccountHeaderSelectionViewClickListener;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener,
        InteractionWithMapFragment,
        ListVenueDialogFragment.InteractionWithVenuesDialogFragment,
        VenueDetailDialogFragment.InteractionWithVenueDetailFragment,
        OnTabSelectListener,
        AddFriendDialogFragment.InteractionWithAddFriendFragment,
        ListFriendFragment.InteractionWithListFriendFragment,
        ProfileDialogFragment.InteractionWithProfileDialog,
        SearchVenueFragment.InteractionWithSearchVenueFragment,
        ChatDialogFragment.InteractionWithChatDialogFragment,
        MessagesFragment.InteractionWithMessagesFragment,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        AppSettingFragment.InteractionWithAppSettingFragment,
        LocationListener {

    @Inject
    MainMvpPresenter<MainView> mainMvpPresenter;
    @BindView(R.id.button_user_setting)
    ImageButton buttonUserSetting;
    @BindView(R.id.text_title_toolbar)
    TextView textTitleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    private User currentUser = null;
    private Drawer userDrawer;
    private AccountHeader userHeader;
    private ArrayList<User> userRequests;
    private ArrayList<User> requestFollows;
    private int badgeNotification = 0;
    private int requestFollowBadge;
    private int requestAddFriendBadge;
    private ArrayList<String> messageNotifications;
    private NetworkReceiver networkReceiver;
    private Snackbar snackbar;
    // private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentUserLocation;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private String currentFragmentTag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //
        getActivityComponent().inject(this);
        mainMvpPresenter.onAttach(this);
        //
        initUiComponents();
        initUiEvents();
        registerNetworkReceiver();
        buildGoogleApiClient();


        mainMvpPresenter.updateUserInfo();
        mainMvpPresenter.updateListRequestAddFriend();
        mainMvpPresenter.updateListRequestFollow();
        mainMvpPresenter.updateMessageNotification();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    private void registerNetworkReceiver() {
        networkReceiver = new NetworkReceiver(snackbar);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(networkReceiver, filter);
    }

    public Location getCurrentUserLocation() {
        return currentUserLocation;
    }

    public void setCurrentUserLocation(Location currentUserLocation) {
        this.currentUserLocation = currentUserLocation;
    }

    public ArrayList<String> messageNotifications() {
        if (messageNotifications == null) {
            messageNotifications = new ArrayList<>();
        }

        return messageNotifications;
    }


    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mainMvpPresenter.updaterUserStatus();

    }

    public void setRequestFollowBadge(int requestFollowBadge) {
        this.requestFollowBadge = requestFollowBadge;
    }

    public void setRequestAddFriendBadge(int requestAddFriendBadge) {
        this.requestAddFriendBadge = requestAddFriendBadge;
    }


    public void setRequestFollows(ArrayList<User> requestFollows) {
        this.requestFollows = requestFollows;
    }

    public void updateBadgeNotification(int badge) {
        badgeNotification += badge;
        BottomBarTab bottomBarTab = bottomBar.getTabWithId(R.id.tab_notification);
        if (bottomBarTab != null) {
            bottomBarTab.setBadgeCount(badgeNotification);
        }
    }

    public void resetBadgeNotification() {
        badgeNotification = 0;
        BottomBarTab bottomBarTab = bottomBar.getTabWithId(R.id.tab_notification);
        if (bottomBarTab != null) {
            bottomBarTab.setBadgeCount(badgeNotification);
        }
    }

    private void initUiEvents() {
        buttonUserSetting.setOnClickListener(this);
        bottomBar.setOnTabSelectListener(this);
    }


    private void initUiComponents() {
        setSupportActionBar(toolbar);
        setupUserDrawer();
        snackbar = Snackbar.make(bottomBar, R.string.network_error, Snackbar.LENGTH_INDEFINITE);

    }

    public void updateUserInfo(User user) {
        currentUser = user;
        updateUserHeaderDrawer(currentUser);
        Toast.makeText(this, "User name: " + currentUser.getName(), Toast.LENGTH_SHORT).show();
    }

    //setup User drawer¬

    private void updateUserHeaderDrawer(User user) {
        if (user != null) {
            IProfile iProfile = userHeader.getActiveProfile();
            iProfile.withName(currentUser.getName());
            iProfile.withEmail(currentUser.getEmail());

            if (currentUser.getImageUrl() != null) {
                Uri userImageUrl = Commons.convertStringToUri(currentUser.getImageUrl());
                iProfile.withIcon(userImageUrl);
            }

            userHeader.updateProfile(iProfile);
        }
    }

    private void setupUserDrawer() {
        AccountHeader accountHeader = createAccountHeaderDrawer();
        List<IDrawerItem> iDrawerItems = createListUserDrawerItem();

        if (accountHeader == null || iDrawerItems == null) {
            return;
        }

        //
        userDrawer = new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withAccountHeader(accountHeader)
                .withDrawerItems(iDrawerItems)
                .build();


        userDrawer.setOnDrawerItemClickListener(new OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                switch (position) {
                    case MyKey.USER_SETTING_ITEM:
                        //replace current fragment with user setting fragment
                        mainMvpPresenter.onCLickUserSettingDrawerItem();
                        break;

                    case MyKey.FAVORITE_PLACES_ITEM:
                        mainMvpPresenter.onClickUserFavoriteVenueItem();
                        break;

                    case MyKey.FOLLOWERS_ITEM:
                        mainMvpPresenter.onClickFollowersItem();
                        break;

                    case MyKey.APP_SETTING_ITEM:
                        mainMvpPresenter.onClickAppSettingItemUserDrawer();
                        break;
                }
                return true;
            }
        });


    }

    public void openAppSettingFragment() {
        //
        replaceFragmentNotVersion4(AppSettingFragment.newInstance(locationRequest), MyKey.APP_SETTING_FRAGMENT_TAG);
    }


    private PrimaryDrawerItem createPrimaryDrawerItem(int identifier, int nameID, int imageId) {
        return new PrimaryDrawerItem()
                .withIdentifier(identifier)
                .withName(nameID)
                .withIcon(imageId)
                .withTextColor(ContextCompat.getColor(this, R.color.colorSecondaryText));
    }

    private List<IDrawerItem> createListUserDrawerItem() {
        List<IDrawerItem> iDrawerItems = new ArrayList<IDrawerItem>();
        iDrawerItems
                .add(createPrimaryDrawerItem(MyKey.USER_SETTING_ITEM, R.string.user_setting_item,
                        R.drawable.ic_account_box_black_24dp));
        iDrawerItems.add(createPrimaryDrawerItem(MyKey.FAVORITE_PLACES_ITEM, R.string.favorite_places,
                R.drawable.ic_view_list_black_24dp));
        iDrawerItems.add(createPrimaryDrawerItem(MyKey.FOLLOWERS_ITEM, R.string.follower_list,
                R.drawable.ic_group_black_24dp));
        iDrawerItems.add(createPrimaryDrawerItem(MyKey.FOLLOWINGS_ITEM, R.string.following_list,
                R.drawable.ic_group_black_24dp));
        iDrawerItems.add(createPrimaryDrawerItem(MyKey.APP_SETTING_ITEM, R.string.app_setting,
                R.drawable.ic_settings));
        iDrawerItems.add(createPrimaryDrawerItem(MyKey.LOG_OUT_ITEM, R.string.sign_out,
                R.drawable.ic_exit_to_app_black_24dp));

        return iDrawerItems;
    }

    private AccountHeader createAccountHeaderDrawer() {
   /* if (currentUser == null) {
      return null;
    }*/
        //

        final ProfileDrawerItem profileDrawerItem = new ProfileDrawerItem()
                .withName("UserName")
                .withEmail("UserEmail").withIdentifier(MyKey.PROFILE_IDENTIFY_DRAWER);

        //set user default image
        profileDrawerItem.withIcon(R.drawable.ic_user_default);

        userHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.accent)
                .addProfiles(
                        profileDrawerItem
                )
                .withOnAccountHeaderSelectionViewClickListener(
                        new OnAccountHeaderSelectionViewClickListener() {
                            @Override
                            public boolean onClick(View view, IProfile profile) {

                                return false;
                            }
                        })
                .build();

        return userHeader;

    }

    //end setup User drawer


    public void openUserListFavoriteVenueFragment() {
        replaceFragment(ListFavoriteVenueFragment.newInstance(), MyKey.LIST_FAVORITE_VENUE_FRAGMENT_TAG);
    }

    private void replaceFragment(Fragment newFragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        if (currentFragment == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container_layout, newFragment, tag).commit();
            currentFragmentTag = tag;
        }


       /* FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.remove(getFragmentManager().findFragmentByTag()).commit();

            FragmentTransaction transactionV4 = fragmentManager.beginTransaction();
            transactionV4.add(R.id.fragment_container_layout, newFragment,tag).commit();
        }*/
    }

    private void replaceFragmentNotVersion4(android.app.Fragment fragment, String tag) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.Fragment currentFragment = fragmentManager.findFragmentByTag(tag);
        if (currentFragment == null) {
            FragmentTransaction transactionV4 = getSupportFragmentManager().beginTransaction();
            transactionV4.remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container_layout)).commit();
            //
            android.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_container_layout, fragment, MyKey.APP_SETTING_FRAGMENT_TAG).commit();
            currentFragmentTag = tag;
        }
    }

    public void openFollowersFragment() {


    }

    public void openUserSettingFragment() {
        replaceFragment(UserSettingFragment.getInstance(currentUser), MyKey.USER_SETTING_FRAGMENT_TAG);
    }

    private void setupMapFragment(Location currentLocation) {
        MapFragment mapFragment = MapFragment.newInstance(currentLocation);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_container_layout, mapFragment, MyKey.MAP_FRAGMENT_TAG)
                .commit();
    }


    @Override
    public void setTitleToolbar(String title) {

    }

    public void openUserSettingDrawer() {
        if (userDrawer != null) {
            userDrawer.openDrawer();
        }
    }

    public void closeUserSettingDrawer() {
        if (userDrawer != null) {
            userDrawer.closeDrawer();

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_user_setting:
                if (mainMvpPresenter != null) {

                    mainMvpPresenter.onClickUserSettingButton();
                    Toast.makeText(this, "userDrawer is open = " + userDrawer.isDrawerOpen(),
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public String getUserImage() {
        if (currentUser != null) {
            String userImage = currentUser.getImageUrl();
            return userImage;
        }

        return null;
    }

    @Override
    public void openListVenueDialogFragment(ArrayList<Result> results) {
        ListVenueDialogFragment.newInstance(results).show(getSupportFragmentManager(), "ListVenueDialogFragment");
    }


    @Override
    public void openVenueDetailDialogFragment(String venueId) {
        VenueDetailDialogFragment.newInstance(venueId).show(getSupportFragmentManager(), "VenueDetailDialogFragment");
    }

    @Override
    public Context getContextOfFragment() {
        return this;
    }

    private void dismissListVenueDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ListVenueDialogFragment dialogFragment = (ListVenueDialogFragment) fragmentManager.findFragmentByTag("ListVenueDialogFragment");
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.fragment_container_layout);

        ArrayList<Result> results = dialogFragment.getVenuesSelected();
        if (results != null || !results.isEmpty()) {
            mapFragment.addVenueMarkerItems(results);
        }
        dialogFragment.dismiss();

    }

    @Override
    public void onClickButtonOkay() {
        dismissListVenueDialogFragment();
    }


    @Override
    public void drawPolyLineOnMap(LatLng destination) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
        if (mapFragment != null) {
            mapFragment.drawPolyLineOnMap(destination);
        }
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.tab_list_friend:
                if (mainMvpPresenter != null) {
                    mainMvpPresenter.onSelectListFriendTab();
                }
                break;
            case R.id.tab_notification:
                if (mainMvpPresenter != null) {
                    mainMvpPresenter.onSelectNotificationsTab();
                }
                break;
            case R.id.tab_search:
                if (mainMvpPresenter != null) {
                    mainMvpPresenter.onSelectSearchVenueTab();
                }
                break;
        }
    }

    public void openNotificationsFragment() {
        NotificationsFragment fragment = NotificationsFragment.newInstance(userRequests, requestFollows
                , messageNotifications, requestAddFriendBadge, requestFollowBadge);
        openFragmentVersionFour(fragment, MyKey.NOTIFICATIONS_FRAGMENT_TAG);
    }

    private void openFragmentVersionFour(Fragment newFragment, String tag) {
        if (currentFragmentTag.equals(MyKey.APP_SETTING_FRAGMENT_TAG)) {
            replaceAppSettingFragment(newFragment,tag);
        } else {
            replaceFragment(newFragment, tag);
        }
    }

    @Override
    public void setRequestAddFriends(ArrayList<User> userRequests) {
        this.userRequests = userRequests;
    }

    public void openListFriendFragment() {
        ListFriendFragment friendFragment = ListFriendFragment.newInstance();
        openFragmentVersionFour(friendFragment, MyKey.LIST_FRIEND_FRAGMENT_TAG);
    }

    public void openProfileDialogFragment(User user) {
        ProfileDialogFragment fragment = ProfileDialogFragment.newInstance(user);
        fragment.show(getSupportFragmentManager(), MyKey.PROFILE_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void openChatDialogFragment(User friend) {
        ChatDialogFragment fragment = ChatDialogFragment.newInstance(friend);
        fragment.show(getSupportFragmentManager(), MyKey.CHAT_DIALOG_FRAGMENT_TAG);
    }

    private void replaceAppSettingFragment(Fragment newFragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.remove(getFragmentManager().findFragmentById(R.id.fragment_container_layout)).commit();

            FragmentTransaction transactionV4 = fragmentManager.beginTransaction();
            transactionV4.add(R.id.fragment_container_layout, newFragment, tag).commit();
            currentFragmentTag = MyKey.SEARCH_VENUE_FRAGMENT_TAG;
        }
    }

    public void openSearchVenueFragment() {
        SearchVenueFragment venueFragment = SearchVenueFragment.newInstance();
        openFragmentVersionFour(venueFragment,MyKey.SEARCH_VENUE_FRAGMENT_TAG);
    }


    @Override
    public void openAddFriendDialogFragment() {
        AddFriendDialogFragment fragment = AddFriendDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), MyKey.ADD_FRIEND_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public String getCurrentUserId() {
        return currentUser.getUserID();
    }

    @Override
    public String getCurrentUserImageUrl() {
        return currentUser.getImageUrl();
    }

    @Override
    public void removeMessageNotificationChildEvent() {
        if (mainMvpPresenter != null) {
            mainMvpPresenter.removeMessageNotificationChildEvent();
        }
    }

    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    @Override
    public void createMessageNotificationChildEvent() {
        if (mainMvpPresenter != null) {
            mainMvpPresenter.updateMessageNotification();
        }
    }

    public void callPhone(String phone) {
        checkCallPhonePermissions();
        String phoneNumber = "tel:" + phone;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        try {
            startActivity(intent);
        } catch (SecurityException e) {

        }

    }

    public void checkCallPhonePermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MyKey.MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null) {
            this.unregisterReceiver(networkReceiver);
        }
    }

    @Override
    public void openMapFragmentFromSearchFragment(Bundle bundleSearchVenue) {
        MapFragment fragment = MapFragment.newInstance(currentUserLocation);
        fragment.setArguments(bundleSearchVenue);
        openFragmentVersionFour(fragment, "MapFragment2");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        currentUserLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        //
        displayCurrentUserLocation(currentUserLocation);

    }

    public void displayCurrentUserLocation(Location currentLocation) {
        if (currentLocation != null) {
            setupMapFragment(currentLocation);
        } else {
            setupMapFragment(null);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void turnOnLocationUpdate(LocationRequest locationRequest) {
        if (locationRequest != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            this.locationRequest = locationRequest;
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void turnOffLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // Toast.makeText(this, "Lat: " + location.getLatitude() + " - Log: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(location.getLatitude())
                .append(",")
                .append(location.getLongitude());
        if (mainMvpPresenter != null) {
            mainMvpPresenter.onUserLocationChange(locationBuilder.toString());
        }

    }
}
