package com.example.android.whereareyougo.ui.ui.notifications;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotificationsPresenter<V extends NotificationsView> extends BasePresenter<V> implements NotificationsMvpPresenter<V> {

    @Inject
    public NotificationsPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
