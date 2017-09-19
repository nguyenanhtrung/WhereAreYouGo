package com.example.android.whereareyougo.ui.ui.appsetting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.LocationUpdateSetting;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.utils.Commons;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.android.gms.location.LocationRequest;


import javax.inject.Inject;


/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class AppSettingFragment extends PreferenceFragment implements AppSettingView, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    AppSettingMvpPresenter<AppSettingView> presenter;
    private InteractionWithAppSettingFragment interaction;
    private SharedPreferences sharedPreferences;
    private LocationRequest locationRequest;
    private boolean isReset = false;
    private SwitchPreference switchPreference;
    private boolean checkRequestLocationUpdate = false;


    public static final long DEFAULT_UPDATE_INTERVAL = 1;
    public static final int DEFAULT_UPDATE_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;
    public static final float DEFAULT_UPDATE_DISTANCE = MyKey.MIN_DISTANCE_UPDATE_LOCATION;

    public AppSettingFragment() {

    }

    public static AppSettingFragment newInstance(LocationRequest locationRequest, boolean checkRequestLocationUpdate) {
        AppSettingFragment fragment = new AppSettingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("locationrequest", locationRequest);
        bundle.putBoolean("requestlocationupdate",checkRequestLocationUpdate);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onAttach(AppSettingFragment.this);
        initUiEvents();
    }

    private void initUiEvents() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, true);
        interaction = (InteractionWithAppSettingFragment) getActivity();
        interaction.getActivityComponent().inject(this);
        initPreferences();

        //
        Bundle bundle = getArguments();
        if (bundle != null) {
            locationRequest = bundle.getParcelable("locationrequest");
            checkRequestLocationUpdate = bundle.getBoolean("requestlocationupdate");
            if (locationRequest == null) {
                createLocationRequest();

            } else {

            }
        } else {
            //create new location request with location setting default
            createLocationRequest();
        }
        initSwitchUpdateLocationPref();
        //

    }

    private void initPreferences() {
        switchPreference = (SwitchPreference) findPreference(getString(R.string.pref_key_request_update_location));
    }

    private void initSwitchUpdateLocationPref() {
        if (!checkRequestLocationUpdate){
            SharedPreferences connectionPref = getPreferenceScreen().getSharedPreferences();
            if (connectionPref != null) {
                boolean isTurnOn = connectionPref.getBoolean(getString(R.string.pref_key_request_update_location), false);
                if (isTurnOn) {
                    connectionPref.edit().putBoolean(getString(R.string.pref_key_request_update_location), false).apply();
                }
            }
        }
        switchPreference.setChecked(checkRequestLocationUpdate);
        //

    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(DEFAULT_UPDATE_INTERVAL / 2);
        locationRequest.setPriority(DEFAULT_UPDATE_PRIORITY);
        locationRequest.setSmallestDisplacement(DEFAULT_UPDATE_DISTANCE);
    }

    public LocationRequest getLocationRequest() {
        return locationRequest;
    }


    public String[] getPrioritiesUpdateLocation() {
        return getResources().getStringArray(R.array.pref_priority_update_location_entries);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);


    }


    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void hideKeyboard() {

    }

    @Override
    public void onError(int messageId) {

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (this.sharedPreferences == null) {
            this.sharedPreferences = sharedPreferences;
        }
        if (key.equals(getString(R.string.pref_key_request_update_location))) {
            if (presenter != null) {
                boolean isTurnOn = sharedPreferences.getBoolean(key, false);
                // Toast.makeText(getActivity(), "Switch = " + isTurnOn, Toast.LENGTH_SHORT).show()

                presenter.onSwitchUpdateLocationChange(isTurnOn);
            }

        } else if (key.equals(getString(R.string.pref_key_update_location_interval))) {
            String interval = sharedPreferences.getString(key, String.valueOf(MyKey.MIN_INTERVAL_UPDATE_LOCATION));
            if (presenter != null) {
                presenter.onIntervalUpdateLocationChange(interval, key);

            }
        } else if (key.equals(getString(R.string.pref_key_priority_update_location))) {
            String priority = sharedPreferences.getString(key, "");
            if (presenter != null) {
                presenter.onListPriorityUpdateLocationChange(key, priority);
            }
        } else if (key.equals(getString(R.string.pref_key_update_location_distance))) {
            String distance = sharedPreferences.getString(key, "0");
            if (presenter != null) {
                presenter.onDistanceUpdateLocationChange(key, distance);
            }
        }
    }

    public void setSummaryForIntervalPreference(String key, String value, boolean isError) {
        Preference connectionPref = findPreference(key);
        if (!isError) {
            connectionPref.setSummary(value + " " + getString(R.string.label_minute));
        } else {
            connectionPref.setSummary(R.string.error_input);

        }
    }

    public void setSummaryForDistancePreference(String key, String value, boolean isError) {
        Preference connectionPref = findPreference(key);
        if (!isError) {
            connectionPref.setSummary(value + " " + getString(R.string.meter));
        } else {
            connectionPref.setSummary(R.string.error_input);
        }
    }

    public void setSummaryForPriorityUpdateLocationPref(String key, String value) {
        Preference connectionPref = findPreference(key);
        if (value != null && !value.isEmpty()) {
            connectionPref.setSummary(getString(R.string.summary_priority_update_location) + value);
        }
    }

    public void turnOnLocationUpdate() {
        if (locationRequest != null) {
            interaction.turnOnLocationUpdate(locationRequest);
        }
    }

    public void turnOffLocationUpdate() {
        interaction.turnOffLocationUpdate();
    }


    public SharedPreferences getSharePreferences() {
        return this.sharedPreferences;
    }

    public void showErrorDialog(int messageId) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.title_error_dialog)
                .titleColorRes(R.color.colorAccent)
                .content(messageId)
                .contentColorRes(R.color.colorSecondaryText)
                .positiveText(R.string.button_ok)
                .build().show();
    }


    public interface InteractionWithAppSettingFragment {
        ActivityComponent getActivityComponent();

        void turnOnLocationUpdate(LocationRequest locationRequest);

        void turnOffLocationUpdate();
    }
}
