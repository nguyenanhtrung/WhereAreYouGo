package com.example.android.whereareyougo.ui.ui.map;

import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public interface MapMvpView extends MvpView {
  void showDialogChooseMapType();

  void showLoadingDialog(int titleId, int contentId);

  String getCurrentUserLocation();

  void openListVenueDialogFragment(ArrayList<Result> results);

  void removeAllVenueMarkerItems();

  void showMessage(int messageId);

  void openVenueDetailDialogFragment(String venueId);
}
