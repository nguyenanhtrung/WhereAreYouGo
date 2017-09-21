package com.example.android.whereareyougo.ui.ui.main;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.database.entity.VenueSearchCondition;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendDialogFragment;
import com.example.android.whereareyougo.ui.ui.appsetting.AppSettingFragment;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.chat.ChatDialogFragment;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueFragment;
import com.example.android.whereareyougo.ui.ui.followers.FollowersFragment;
import com.example.android.whereareyougo.ui.ui.followings.FollowingsFragment;
import com.example.android.whereareyougo.ui.ui.followingselection.FollowingsSelectionDialogFragment;
import com.example.android.whereareyougo.ui.ui.friendsmap.FriendsMapFragment;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendFragment;
import com.example.android.whereareyougo.ui.ui.map.ListVenueDialogFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment.InteractionWithMapFragment;
import com.example.android.whereareyougo.ui.ui.messages.MessagesFragment;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsFragment;
import com.example.android.whereareyougo.ui.ui.profile.ProfileDialogFragment;
import com.example.android.whereareyougo.ui.ui.receiver.NetworkReceiver;
import com.example.android.whereareyougo.ui.ui.searchvenue.SearchVenueFragment;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingFragment;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailDialogFragment;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyFragmentUtil;
import com.example.android.whereareyougo.ui.utils.MyKey;
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
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.google.android.gms.location.FusedLocationProviderClient;

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
        LocationListener,
        FriendsMapFragment.InteractionWithFriendsMapFragment,
        FollowingsSelectionDialogFragment.InteractionWithFFollowingSelection {

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
    private boolean checkRequestUpdateLocation = false;


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
        //setupMapFragment(null);


        mainMvpPresenter.updateUserInfo();
        mainMvpPresenter.updaterUserStatus();
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

    public User getCurrentUser(){
        return currentUser;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserSettingFragment fragment = (UserSettingFragment) getFragmentManager().findFragmentByTag(MyKey.USER_SETTING_FRAGMENT_TAG);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


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
                        //
                        break;
                    case MyKey.FOLLOWINGS_ITEM:
                        mainMvpPresenter.onClickFollowingsItemUserDrawer();
                        break;

                }
                return true;
            }
        });


    }

    public void openAppSettingFragment() {
        //
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, AppSettingFragment.newInstance(locationRequest, checkRequestUpdateLocation),
                MyKey.FOLLOWERS_FRAGMENT_TAG);

    }

    public void openFollowingsFragment() {
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, FollowingsFragment.newInstance(),
                MyKey.FOLLOWINGS_FRAGMENT_TAG);
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
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, ListFavoriteVenueFragment.newInstance(),
                MyKey.LIST_FAVORITE_VENUE_FRAGMENT_TAG);
    }


    public void openFollowersFragment() {
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, FollowersFragment.newInstance(), MyKey.FOLLOWERS_FRAGMENT_TAG);
    }

    public void openUserSettingFragment() {
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, UserSettingFragment.getInstance(currentUser), MyKey.USER_SETTING_FRAGMENT_TAG);

    }

    private void setupMapFragment(Location currentLocation) {
        MapFragment mapFragment = MapFragment.newInstance(currentLocation);
        MyFragmentUtil.addFragment(getFragmentManager(), R.id.fragment_container_layout, mapFragment, MyKey.MAP_FRAGMENT_TAG);
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
    public void openFollowingsSelectionDialogFragment(ArrayList<String> followingIds) {
        FollowingsSelectionDialogFragment fragment = FollowingsSelectionDialogFragment.newInstance(followingIds);
        fragment.show(getFragmentManager(), MyKey.FOLLOWING_SELECTION_FRAGMENT_TAG);


    }

    @Override
    public void openListVenueDialogFragment(ArrayList<Result> results) {
        ListVenueDialogFragment.newInstance(results).show(getFragmentManager(), "ListVenueDialogFragment");
    }


    @Override
    public void openVenueDetailDialogFragment(String venueId) {
        VenueDetailDialogFragment.newInstance(venueId).show(getFragmentManager(), "VenueDetailDialogFragment");
    }

    public void openFriendsMapFragment() {
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout,
                FriendsMapFragment.newInstance(currentUserLocation,checkRequestUpdateLocation), MyKey.FRIENDS_MAP_FRAGMENT_TAG);
    }

    @Override
    public Context getContextOfFragment() {
        return this;
    }

    @Override
    public boolean getCheckRequestLocationUpdate() {
        return checkRequestUpdateLocation;
    }

    private void dismissListVenueDialogFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        ListVenueDialogFragment dialogFragment = (ListVenueDialogFragment) fragmentManager.findFragmentByTag("ListVenueDialogFragment");
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentById(R.id.fragment_container_layout);

        ArrayList<Result> results = dialogFragment.getVenuesSelected();
        if (results != null) {
            if (!results.isEmpty()){
                mapFragment.addVenueMarkerItems(results);
            }

        }
        dialogFragment.dismiss();

    }

    @Override
    public void onClickButtonOkay() {
        dismissListVenueDialogFragment();
    }


    @Override
    public void drawPolyLineOnMap(LatLng destination) {
        FragmentManager fragmentManager = getFragmentManager();
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
            case R.id.tab_home:
                if (mainMvpPresenter != null) {
                    mainMvpPresenter.onSelectMapTab();
                }
                break;
            case R.id.tab_friends_on_map:
                if (mainMvpPresenter != null) {
                    mainMvpPresenter.onSelectFriendsMapTab();
                }
                break;

        }
    }

    public void openNotificationsFragment() {
        NotificationsFragment fragment = NotificationsFragment.newInstance(userRequests, requestFollows
                , messageNotifications, requestAddFriendBadge, requestFollowBadge);
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, fragment, MyKey.NOTIFICATIONS_FRAGMENT_TAG);
    }


    @Override
    public void setRequestAddFriends(ArrayList<User> userRequests) {
        this.userRequests = userRequests;
    }

    public void openListFriendFragment() {
        ListFriendFragment friendFragment = ListFriendFragment.newInstance();
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, ListFriendFragment.newInstance(), MyKey.LIST_FRIEND_FRAGMENT_TAG);

    }

    public void openProfileDialogFragment(User user) {
        ProfileDialogFragment fragment = ProfileDialogFragment.newInstance(user);
        fragment.show(getFragmentManager(), MyKey.PROFILE_DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void openChatDialogFragment(User friend) {
        ChatDialogFragment fragment = ChatDialogFragment.newInstance(friend);
        fragment.show(getFragmentManager(), MyKey.CHAT_DIALOG_FRAGMENT_TAG);
    }


    public void openSearchVenueFragment() {
        SearchVenueFragment venueFragment = SearchVenueFragment.newInstance();
        MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, SearchVenueFragment.newInstance(), MyKey.SEARCH_VENUE_FRAGMENT_TAG);

    }


    @Override
    public void openAddFriendDialogFragment() {
        AddFriendDialogFragment fragment = AddFriendDialogFragment.newInstance();
        fragment.show(getFragmentManager(), MyKey.ADD_FRIEND_DIALOG_FRAGMENT_TAG);
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

    public void openMapFragment() {
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
        if (googleApiClient != null) {
            currentUserLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }

        Fragment mapFragment = getFragmentManager().findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
        if (mapFragment == null){
            MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, MapFragment.newInstance(currentUserLocation), MyKey.MAP_FRAGMENT_TAG);
        }else{
            MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout, mapFragment, MyKey.MAP_FRAGMENT_TAG);

        }

    }

    @Override
    public void openMapFragmentFromSearchFragment(VenueSearchCondition searchCondition) {
        Fragment mapFragment = getFragmentManager().findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
        if (mapFragment != null){

            if (searchCondition != null){
                mapFragment.getArguments().putParcelable("searchcondition",searchCondition);
            }
            Log.d(MyKey.MAP_FRAGMENT_TAG,"My Map Fragment");
            MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout,
                    mapFragment, MyKey.MAP_FRAGMENT_TAG);
        }else{
            MyFragmentUtil.replaceFragment(getFragmentManager(), R.id.fragment_container_layout,
                    MapFragment.newInstance(currentUserLocation), MyKey.MAP_FRAGMENT_TAG);
        }


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
        if (currentUserLocation != null){
            if (mainMvpPresenter != null) {
                mainMvpPresenter.onGoogleApiClientConnected();
            }
        }
        //


    }

    public void displayCurrentUserLocation(Location location) {
        if (location != null) {
            Toast.makeText(this, "userLocation = " + location.getLatitude(), Toast.LENGTH_SHORT).show();

            setupMapFragment(location);
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
            checkRequestUpdateLocation = true;
        }
    }

    @Override
    public void turnOffLocationUpdate() {
        if (checkRequestUpdateLocation) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
            checkRequestUpdateLocation = false;
        }
    }

    public void updateUserLocationOnMap(Location location) {
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
        if (mapFragment != null) {
            mapFragment.updateUserLocationOnMap(location);
        } else {
            FriendsMapFragment friendsMapFragment = (FriendsMapFragment) fragmentManager.findFragmentByTag(MyKey.FRIENDS_MAP_FRAGMENT_TAG);
            if (friendsMapFragment != null) {
                friendsMapFragment.updateUserLocation(location);
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // Toast.makeText(this, "Lat: " + location.getLatitude() + " - Log: " + location.getLongitude(), Toast.LENGTH_SHORT).show();

        if (mainMvpPresenter != null) {
            mainMvpPresenter.onUserLocationChange(location);
        }

    }



    @Override
    public void sendFollowingsToFriendMapFragment() {
        FollowingsSelectionDialogFragment dialogFragment = (FollowingsSelectionDialogFragment) getFragmentManager().findFragmentByTag(MyKey.FOLLOWING_SELECTION_FRAGMENT_TAG);
        if (dialogFragment != null) {
            FriendsMapFragment friendsMapFragment = (FriendsMapFragment) getFragmentManager().findFragmentByTag(MyKey.FRIENDS_MAP_FRAGMENT_TAG);
            if (friendsMapFragment != null) {
                friendsMapFragment.setFollowingsSelected(dialogFragment.getFollowingsAfterSelected());
            }
        }
    }
}
