package com.example.android.whereareyougo.ui.ui.base;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

  private static final String TAG = "BasePresenter";
  private DataManager dataManager;
  private V mvpView;

  @Inject
  public BasePresenter(DataManager dataManager) {
    this.dataManager = dataManager;

  }

  @Override
  public void onAttach(V view) {
    mvpView = view;
  }

  @Override
  public void onDetach() {
    mvpView = null;

  }

  public V getMvpView() {
    return mvpView;
  }

  public DataManager getDataManager() {
    return dataManager;
  }
}



