package com.example.android.whereareyougo.ui.ui.locationupdatesetting;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class LocationUpdateSettingPresenter<V extends LocationUpdateSettingView> extends BasePresenter<V> implements
    LocationUpdateSettingMvpPresenter<V>{

    @Inject
    public LocationUpdateSettingPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
