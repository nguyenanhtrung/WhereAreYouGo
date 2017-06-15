package com.example.android.whereareyougo.ui.ui.map;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapPresenter<V extends MapMvpView> extends BasePresenter<V> implements
    MapMvpPresenter<V>{

  @Inject
  public MapPresenter(DataManager dataManager) {
    super(dataManager);
  }

  @Override
  public void onAttach(V view) {

  }

  @Override
  public void onDetach() {

  }
}
