package com.example.android.whereareyougo.ui.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.RequestAddFriend;
import com.example.android.whereareyougo.ui.data.database.entity.RequestFollow;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.NotificationsFragmentPagerAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotificationsFragment extends BaseFragment implements NotificationsView {

    @Inject
    NotificationsMvpPresenter<NotificationsView> presenter;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private NotificationsFragmentPagerAdapter pagerAdapter;
    private ArrayList<User> userRequests;
    private ArrayList<User> requestFollows;
    private ArrayList<String> messageNotifications;
    private int requestAddFriendBadge;
    private int requestFollowBadge;
    Unbinder unbinder;

    public static NotificationsFragment newInstance(ArrayList<User> userRequests, ArrayList<User> requestFollows,
                                                    ArrayList<String> messageNotifications, int requestAddFriendBadge, int requestFollowBadge) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("requestaddfriend", userRequests);
        bundle.putParcelableArrayList("requestfollow", requestFollows);
        bundle.putStringArrayList("messagenotifications",messageNotifications);
        bundle.putInt("requestaddfriendbadge", requestAddFriendBadge);
        bundle.putInt("requestfollowbadge", requestAddFriendBadge);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userRequests = bundle.getParcelableArrayList("requestaddfriend");
            requestFollows = bundle.getParcelableArrayList("requestfollow");
            messageNotifications = bundle.getStringArrayList("messagenotifications");
            requestFollowBadge = bundle.getInt("requestfollowbadge");
            requestAddFriendBadge = bundle.getInt("requestaddfriendbadge");
        }


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewPager();
    }

    private void setupViewPager() {
        pagerAdapter = new NotificationsFragmentPagerAdapter(getFragmentManager(), getActivity(), userRequests, requestFollows
        ,messageNotifications);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
        tabLayout.setupWithViewPager(viewPager);
        setupTabCustomView();
    }

    private void setupTabCustomView() {
        if (tabLayout != null) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (i == 0) {
                    tab.setCustomView(pagerAdapter.getTabView(i, requestAddFriendBadge));
                } else if (i == 1) {
                    if(messageNotifications != null){
                        tab.setCustomView(pagerAdapter.getTabView(i, messageNotifications.size()));
                    }else{
                        tab.setCustomView(pagerAdapter.getTabView(i, 0));

                    }

                } else if (i == 2) {
                    //notify fragment
                } else if (i == 3) {
                    tab.setCustomView(pagerAdapter.getTabView(i, requestFollowBadge));
                }

            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
