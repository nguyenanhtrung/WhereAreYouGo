package com.example.android.whereareyougo.ui.ui.followers;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public interface FollowersView extends MvpView {
    void setupDatasForRecyclerViewFollowers(ArrayList<User> datas);

    void dismissLoadingDialog();

    void removeFollowerInRecyclerView(int position);

    void showDeleteFollowerDialog(User user, int position);
}
