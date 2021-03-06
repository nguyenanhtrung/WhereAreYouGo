package com.example.android.whereareyougo.ui.ui.map;

import android.os.Bundle;

import com.example.android.whereareyougo.ui.data.database.entity.VenueSearchCondition;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MapMvpPresenter<V extends MapMvpView> extends MvpPresenter<V> {
  int getMapTypeChoose(String[] maptype, String choose);
  void onClickFloatButtonMapType();
  void onClickButtonSearchVenue(String venueName);
  void onClusterItemInfoWindowClick(String venueid);
  void getVenuesByRadiusAndCategory(VenueSearchCondition venueSearchCondition);
}
