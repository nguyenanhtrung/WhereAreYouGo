package com.example.android.whereareyougo.ui.ui.followings;

import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public interface FollowingsView extends MvpView {
    void setDataForFollowingsRecyclerViewAdapter(ArrayList<User> datas);

    void showLoadingDialog();

    void hideLoadingDialog();
}
