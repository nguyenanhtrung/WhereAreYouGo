package com.example.android.whereareyougo.ui.ui.appsetting;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public interface AppSettingMvpPresenter<V extends AppSettingView> extends MvpPresenter<V> {
    void onIntervalUpdateLocationChange(String interval,String key);

    void onListPriorityUpdateLocationChange(String key, String value);

    void onDistanceUpdateLocationChange(String key, String value);

    void onSwitchUpdateLocationChange(boolean isTurnOn);
}
