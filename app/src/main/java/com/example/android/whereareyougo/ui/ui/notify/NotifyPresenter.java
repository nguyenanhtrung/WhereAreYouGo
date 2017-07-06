package com.example.android.whereareyougo.ui.ui.notify;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotifyPresenter<V extends NotifyView> extends BasePresenter<V> implements NotifyMvpPresenter<V> {

    @Inject
    public NotifyPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
