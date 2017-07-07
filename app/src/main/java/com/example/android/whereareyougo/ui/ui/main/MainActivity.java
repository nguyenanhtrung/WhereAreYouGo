package com.example.android.whereareyougo.ui.ui.main;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.addfriend.AddFriendDialogFragment;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.favoritevenues.ListFavoriteVenueFragment;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendFragment;
import com.example.android.whereareyougo.ui.ui.map.ListVenueDialogFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
import com.example.android.whereareyougo.ui.ui.map.MapFragment.InteractionWithMapFragment;
import com.example.android.whereareyougo.ui.ui.notifications.NotificationsFragment;
import com.example.android.whereareyougo.ui.ui.signup.SignupDialogFragment.InteractionWithSignupFragment;
import com.example.android.whereareyougo.ui.ui.usersetting.UserSettingFragment;
import com.example.android.whereareyougo.ui.ui.venuedetail.VenueDetailDialogFragment;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
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
import java.util.List;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener,
    InteractionWithMapFragment,
        ListVenueDialogFragment.InteractionWithVenuesDialogFragment,
        VenueDetailDialogFragment.InteractionWithVenueDetailFragment,
        OnTabSelectListener,
        AddFriendDialogFragment.InteractionWithAddFriendFragment,
        ListFriendFragment.InteractionWithListFriendFragment
{

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
  private int badgeNotification = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);
    mainMvpPresenter.onAttach(this);

    //
    initUiComponents();
    initUiEvents();
    setupMapFragment();

    //update User info object
    //mainMvpPresenter.updateUserInfo();
    setupUserDrawer();
    mainMvpPresenter.updateUserInfo();
    mainMvpPresenter.updateListRequestAddFriend();

  }


  @Override
  protected void onResume() {
    super.onResume();
    mainMvpPresenter.updaterUserStatus();
  }

  public void updateBadgeNotification(int badge){
    badgeNotification += badge;
    BottomBarTab bottomBarTab = bottomBar.getTabWithId(R.id.tab_notification);
    if (bottomBarTab != null && !userRequests.isEmpty()){
      bottomBarTab.setBadgeCount(badgeNotification);
    }
  }

  public void resetBadgeNotification(){
    badgeNotification = 0;
    BottomBarTab bottomBarTab = bottomBar.getTabWithId(R.id.tab_notification);
    if (bottomBarTab != null){
      bottomBarTab.setBadgeCount(badgeNotification);
    }
  }

  private void initUiEvents() {
    buttonUserSetting.setOnClickListener(this);
    bottomBar.setOnTabSelectListener(this);
  }


  private void initUiComponents() {
    setSupportActionBar(toolbar);
    bottomBar.getCurrentTab().setBadgeCount(2);
  }

  public void updateUserInfo(User user) {
    currentUser = user;
    updateUserHeaderDrawer(currentUser);
    Toast.makeText(this, "User name: " + currentUser.getName(), Toast.LENGTH_SHORT).show();
  }

  //setup User drawer

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
        }
        return true;
      }
    });


  }

  public void openUserListFavoriteVenueFragment(){
    replaceFragment(ListFavoriteVenueFragment.newInstance(),MyKey.LIST_FAVORITE_VENUE_FRAGMENT_TAG);
  }

  private void replaceFragment(Fragment newFragment, String tag) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment = fragmentManager.findFragmentByTag(tag);
    if (fragment == null) {
      FragmentTransaction transaction = fragmentManager.beginTransaction();
      transaction.replace(R.id.fragment_container_layout, newFragment).commit();
    }
  }

  public void openUserSettingFragment() {
    replaceFragment(UserSettingFragment.getInstance(currentUser), MyKey.USER_SETTING_FRAGMENT_TAG);
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


  private void setupMapFragment() {
    MapFragment mapFragment = MapFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().add(R.id.fragment_container_layout, mapFragment,MyKey.MAP_FRAGMENT_TAG)
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
    ListVenueDialogFragment.newInstance(results).show(getSupportFragmentManager(),"ListVenueDialogFragment");
  }



  @Override
  public void openVenueDetailDialogFragment(String venueId) {
    VenueDetailDialogFragment.newInstance(venueId).show(getSupportFragmentManager(),"VenueDetailDialogFragment");
  }

  @Override
  public Context getContextOfFragment() {
    return this;
  }

  private void dismissListVenueDialogFragment(){
    FragmentManager fragmentManager = getSupportFragmentManager();
    ListVenueDialogFragment dialogFragment = (ListVenueDialogFragment) fragmentManager.findFragmentByTag("ListVenueDialogFragment");
    MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
    if (dialogFragment != null && mapFragment != null){
      ArrayList<Result> results = dialogFragment.getVenuesSelected();
      if (results != null || !results.isEmpty()){
        mapFragment.addVenueMarkerItems(results);
      }
      dialogFragment.dismiss();
    }
  }

  @Override
  public void onClickButtonOkay() {
    dismissListVenueDialogFragment();
  }


  @Override
  public void drawPolyLineOnMap(LatLng destination) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    MapFragment mapFragment = (MapFragment) fragmentManager.findFragmentByTag(MyKey.MAP_FRAGMENT_TAG);
    if (mapFragment != null){
      mapFragment.drawPolyLineOnMap(destination);
    }
  }

  @Override
  public void onTabSelected(int tabId) {
    switch (tabId){
      case R.id.tab_list_friend:
        if (mainMvpPresenter != null){
          mainMvpPresenter.onSelectListFriendTab();
        }
        break;
      case R.id.tab_notification:
        if (mainMvpPresenter != null){
          mainMvpPresenter.onSelectNotificationsTab();
        }
    }
  }

  public void openNotificationsFragment(){
    NotificationsFragment fragment = NotificationsFragment.newInstance(userRequests);
    replaceFragment(fragment,MyKey.NOTIFICATIONS_FRAGMENT_TAG);
  }

  @Override
  public void setRequestAddFriends(ArrayList<User> userRequests) {
    this.userRequests = userRequests;
  }

  public void openListFriendFragment(){
    ListFriendFragment friendFragment = ListFriendFragment.newInstance();
    replaceFragment(friendFragment,MyKey.LIST_FRIEND_FRAGMENT_TAG);
  }

  @Override
  public void openAddFriendDialogFragment() {
    AddFriendDialogFragment fragment = AddFriendDialogFragment.newInstance();
    fragment.show(getSupportFragmentManager(),MyKey.ADD_FRIEND_DIALOG_FRAGMENT_TAG);
  }

  @Override
  public String getCurrentUserId() {
    return currentUser.getUserID();
  }
}
