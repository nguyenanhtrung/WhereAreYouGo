package com.example.android.whereareyougo.ui.ui.appsetting;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/08/2017.
 */

public class AppSettingPresenter<V extends AppSettingView> extends BasePresenter<V> implements AppSettingMvpPresenter<V> {

    @Inject
    public AppSettingPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
