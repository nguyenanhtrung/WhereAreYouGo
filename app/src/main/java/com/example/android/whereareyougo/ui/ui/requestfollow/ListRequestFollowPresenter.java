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

    public void onClickButtonAccept(String senderId, int position){
        //set isFollow = true of friend on node friends database
          getDataManager().acceptRequestFollow(senderId);
        //remove request follow on recyclerview
        getMvpView().removeRequestFollowRecyclerView(position);

        //remove request follow on firebase database
        getDataManager().deleteRequestFollowById(senderId);
    }

    public void onClickButtonCancel(String senderId, int position){
        //remove on recyclerview
        getMvpView().removeRequestFollowRecyclerView(position);

        //remove on firebase database
        getDataManager().deleteRequestFollowById(senderId);
    }
}
