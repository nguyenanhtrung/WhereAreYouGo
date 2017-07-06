package com.example.android.whereareyougo.ui.ui.messages;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class MessagesPresenter<V extends MessagesView> extends BasePresenter<V> implements MessagesMvpPresenter<V> {
    @Inject
    public MessagesPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
