package com.example.android.whereareyougo.ui.ui.followers;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public interface FollowersMvpPresenter<V extends FollowersView> extends MvpPresenter<V> {
    void showUserFollowers();

    void onClickButtonUnfollow(User friend, int position);

    void onClickButtonAgreeDeleteDialog(User friend, int position);
}
