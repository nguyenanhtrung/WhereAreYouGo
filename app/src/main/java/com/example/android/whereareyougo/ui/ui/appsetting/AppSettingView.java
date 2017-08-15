package com.example.android.whereareyougo.ui.ui.appsetting;

import android.content.SharedPreferences;

import com.example.android.whereareyougo.ui.data.database.entity.LocationUpdateSetting;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
import com.google.android.gms.location.LocationRequest;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public interface AppSettingView extends MvpView {
    void showErrorDialog(int messageId);

    SharedPreferences getSharePreferences();

    void setSummaryForIntervalPreference(String key, String value, boolean isError);

    void setSummaryForPriorityUpdateLocationPref(String key, String value);

    void setSummaryForDistancePreference(String key, String value, boolean isError);

    String[] getPrioritiesUpdateLocation();

    LocationRequest getLocationRequest();

    void turnOnLocationUpdate();

    void turnOffLocationUpdate();

}
