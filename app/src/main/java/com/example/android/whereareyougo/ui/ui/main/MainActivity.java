package com.example.android.whereareyougo.ui.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseActivity;
import com.example.android.whereareyougo.ui.ui.map.MapFragment;
import com.roughike.bottombar.BottomBar;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getActivityComponent().inject(this);
    mainMvpPresenter.onAttach(this);

    //
    initUiComponents();
    setupMapFragment();

    //update User info
    mainMvpPresenter.updateUserInfo();

  }


  private void initUiComponents() {
    setSupportActionBar(toolbar);
  }

  public void updateUserInfo(User user){
    currentUser = user;
    Toast.makeText(this, "User name: " + currentUser.getName(), Toast.LENGTH_SHORT).show();
  }


  //setup User drawer

  private void setupUserDrawer(){

  }

  private void setupUserHeaderDrawer(){

  }


  //end setup User




  private void setupMapFragment() {
    MapFragment mapFragment = MapFragment.newInstance();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().add(R.id.fragment_container_layout, mapFragment)
        .commit();
  }


  @Override
  public void setTitleToolbar(String title) {

  }


}
