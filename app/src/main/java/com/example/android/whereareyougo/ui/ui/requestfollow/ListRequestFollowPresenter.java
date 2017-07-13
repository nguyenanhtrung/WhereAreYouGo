package com.example.android.whereareyougo.ui.ui.requestfollow;

import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.ui.listfriend.ListFriendMvpPresenter;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class ListRequestFollowPresenter<V extends ListRequestFollowView> extends BasePresenter<V> implements ListRequestFollowMvpPresenter<V> {
    @Inject
    public ListRequestFollowPresenter(DataManager dataManager) {
        super(dataManager);
    }
}
