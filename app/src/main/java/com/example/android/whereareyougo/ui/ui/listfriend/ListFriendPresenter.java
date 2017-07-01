package com.example.android.whereareyougo.ui.ui.listfriend;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class ListFriendPresenter<V extends ListFriendView> extends BasePresenter<V> implements ListFriendMvpPresenter<V> {

    @Inject
    public ListFriendPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
