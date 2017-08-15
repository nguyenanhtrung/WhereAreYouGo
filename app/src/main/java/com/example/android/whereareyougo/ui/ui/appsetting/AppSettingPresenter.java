package com.example.android.whereareyougo.ui.ui.appsetting;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.example.android.whereareyougo.ui.utils.StringUtil;
import com.google.android.gms.location.LocationRequest;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class AppSettingPresenter<V extends AppSettingView> extends BasePresenter<V> implements AppSettingMvpPresenter<V> {

    @Inject
    public AppSettingPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onIntervalUpdateLocationChange(String interval, String key) {
        if (interval != null) {
            if (interval.isEmpty()) {
                //interval empty --> show message to user, set interval variable = MIN(1 minute)
                getMvpView().showErrorDialog(R.string.error_empty);
                getMvpView().setSummaryForIntervalPreference(key, interval, true);

            } else if (!Commons.checkIsNumber(interval)) {
                //show message to user: interval is not a number, set interval =  MIN(1 minute)
                getMvpView().showErrorDialog(R.string.error_number_format);
                getMvpView().setSummaryForIntervalPreference(key, interval, true);
            } else if (Integer.valueOf(interval) > Commons.convertMillisToMinute(MyKey.MAX_INTERVAL_UPDATE_LOCATION)) {
                //show message to user: interval > MAX INTERVAl, set interval = MAX(180 minute)
                getMvpView().showErrorDialog(R.string.error_max_value);
                getMvpView().setSummaryForIntervalPreference(key, interval, true);

            } else if (Integer.valueOf(interval) < Commons.convertMillisToMinute(MyKey.MIN_INTERVAL_UPDATE_LOCATION)) {
                //show message to user: interval < MIN INTERVAL, set interval =  MIN (1 minute)
                getMvpView().showErrorDialog(R.string.error_min_value);
                getMvpView().setSummaryForIntervalPreference(key, interval, true);

            } else {
                //set summary of this preferences  = "thoi gian cap nhat = " + interval
                getMvpView().setSummaryForIntervalPreference(key, interval, false);
                getMvpView().getLocationRequest().setInterval(Long.parseLong(interval));
                getMvpView().getLocationRequest().setFastestInterval(Long.parseLong(interval) / 2);
            }
        }
    }

    public void onSwitchUpdateLocationChange(boolean isTurnOn) {
        if (isTurnOn) {
            //turn on location update
            getMvpView().turnOnLocationUpdate();

        } else {
            //turn off location update

            getMvpView().turnOffLocationUpdate();

        }
    }

    public void onListPriorityUpdateLocationChange(String key, String value) {
        getMvpView().setSummaryForPriorityUpdateLocationPref(key, value);
        String[] priorities = getMvpView().getPrioritiesUpdateLocation();
        if (value.equals(priorities[0])) {
            getMvpView().getLocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else if (value.equals(priorities[1])) {
            getMvpView().getLocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        } else if (value.equals(priorities[2])) {
            getMvpView().getLocationRequest().setPriority(LocationRequest.PRIORITY_LOW_POWER);
        }
    }

    public void onDistanceUpdateLocationChange(String key, String value) {
        if (value != null) {
            if (value.isEmpty()) {
                //distance empty --> show message to user, set value = MIN(0 m)
                getMvpView().showErrorDialog(R.string.error_empty);
                getMvpView().setSummaryForDistancePreference(key, value, true);

            } else if (!Commons.checkIsNumber(value)) {
                //show message to user: value is not a number, set value =  0m
                getMvpView().showErrorDialog(R.string.error_number_format);
                getMvpView().setSummaryForDistancePreference(key, value, true);
            } else if (Integer.valueOf(value) > MyKey.MAX_DISTANCE_UPDATE_LOCATION) {
                //show message to user: value > MAX DISTANCE, set value = MAX(50000m)
                getMvpView().showErrorDialog(R.string.error_max_value);
                getMvpView().setSummaryForDistancePreference(key, value, true);

            } else if (Integer.valueOf(value) < MyKey.MIN_DISTANCE_UPDATE_LOCATION) {
                //show message to user: value < MIN DISTANCE, set value =  MIN (0m)
                getMvpView().showErrorDialog(R.string.error_min_value);
                getMvpView().setSummaryForDistancePreference(key, value, true);

            } else {
                //set summary of this preferences  = "Khoang cach = " + value
                getMvpView().setSummaryForDistancePreference(key, value, false);
                getMvpView().getLocationRequest().setSmallestDisplacement(Float.parseFloat(value));
            }
        }
    }


}
