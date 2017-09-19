package com.example.android.whereareyougo.ui.ui.notifications;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;

import java.util.ArrayList;

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
        setupViewPager();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userRequests = bundle.getParcelableArrayList("requestaddfriend");
            requestFollows = bundle.getParcelableArrayList("requestfollow");
            messageNotifications = bundle.getStringArrayList("messagenotifications");
            requestFollowBadge = bundle.getInt("requestfollowbadge");
            requestAddFriendBadge = bundle.getInt("requestaddfriendbadge");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();

    }

    private void initUiEvents() {
        setupTabLayoutEvent();

    }

    private void setupTabLayoutEvent() {
        if (tabLayout != null){
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    if (view != null){
                        TextView textBadge = (TextView) view.findViewById(R.id.text_badge_number);
                        TextView textTabName = (TextView) view.findViewById(R.id.text_tab_name);
                        //
                        textTabName.setTextColor(ContextCompat.getColor(getActivity(),R.color.md_white_1000));
                        textBadge.setVisibility(View.GONE);
                    }


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    if (view != null){
                        TextView textTabName = (TextView) view.findViewById(R.id.text_tab_name);
                        //
                        textTabName.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }


    private void setupViewPager() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            pagerAdapter = new NotificationsFragmentPagerAdapter(getChildFragmentManager(), getActivity(), userRequests, requestFollows
            ,messageNotifications);
        }
        viewPager.setAdapter(pagerAdapter);
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
                        tab.setCustomView(pagerAdapter.getTabView(i, 1));

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
