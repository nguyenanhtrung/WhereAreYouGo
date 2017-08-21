package com.example.android.whereareyougo.ui.ui.followingselection;

import android.view.View;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;


import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 20/08/2017.
 */

public interface FollowingSelectionMvpPresenter<V extends FollowingSelectionView> extends MvpPresenter<V> {
    void getFollowingsInfo(final ArrayList<String> followingIds);

    void onClickFollowingsRecyclerViewItem(User user, View view);

    void onClickButtonCloseDialog();

    void onClickButtonAddFollowings(int numOfFollowings);
}
