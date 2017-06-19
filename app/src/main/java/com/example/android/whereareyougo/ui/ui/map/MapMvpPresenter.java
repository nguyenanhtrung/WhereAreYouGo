package com.example.android.whereareyougo.ui.ui.map;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MapMvpPresenter<V extends MapMvpView> extends MvpPresenter<V> {
  int getMapTypeChoose(String[] maptype, String choose);
  void onClickFloatButtonMapType();
}
