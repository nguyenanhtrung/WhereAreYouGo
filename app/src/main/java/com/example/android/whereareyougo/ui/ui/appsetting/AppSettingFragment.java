package com.example.android.whereareyougo.ui.ui.appsetting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;


import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;



import javax.inject.Inject;



/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class AppSettingFragment extends PreferenceFragment implements AppSettingView {

    @Inject
    AppSettingMvpPresenter<AppSettingView> presenter;
    private InteractionWithAppSettingFragment interaction;


    public static AppSettingFragment newInstance() {
        AppSettingFragment fragment = new AppSettingFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onAttach(AppSettingFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity(),R.xml.preferences,false);
        interaction = (InteractionWithAppSettingFragment)getActivity();
        interaction.getActivityComponent().inject(this);
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
    public void onError(String message, Activity activity) {

    }

    @Override
    public void hideKeyboard() {

    }

    public interface InteractionWithAppSettingFragment{
        ActivityComponent getActivityComponent();
    }
}
