package com.example.android.whereareyougo.ui.ui.main;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements
    MainMvpPresenter<V>{

  @Inject
  public MainPresenter(DataManager dataManager) {
    super(dataManager);
  }

  public void updateUserInfo(){
    getDataManager().getUserInfo().addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        User user = dataSnapshot.getValue(User.class);
        getMvpView().updateUserInfo(user) ;
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @Override
  public void onClickUserSettingButton() {
    getMvpView().openUserSettingDrawer();
  }

  @Override
  public void onCLickUserSettingDrawerItem() {
    getMvpView().closeUserSettingDrawer();
    getMvpView().openUserSettingFragment();

  }

  @Override
  public void onClickUserFavoriteVenueItem() {
    getMvpView().closeUserSettingDrawer();
    getMvpView().openUserListFavoriteVenueFragment();
  }

  @Override
  public void onSelectListFriendTab() {
    getMvpView().openListFriendFragment();
  }
}
