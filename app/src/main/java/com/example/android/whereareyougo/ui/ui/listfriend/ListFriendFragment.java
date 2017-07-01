package com.example.android.whereareyougo.ui.ui.listfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FriendsRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 01/07/2017.
 */

public class ListFriendFragment extends BaseFragment implements ListFriendView {
    @Inject
    ListFriendPresenter<ListFriendView> presenter;
    @BindView(R.id.recycler_view_friends)
    UltimateRecyclerView recyclerViewFriends;

    Unbinder unbinder;
    @BindView(R.id.button_search_friend)
    FloatingActionButton buttonSearchFriend;
    @BindView(R.id.button_add_friend)
    FloatingActionButton buttonAddFriend;
    @BindView(R.id.float_button_friends_action)
    FloatingActionMenu floatButtonFriendsAction;

    private List<User> friends;
    private FriendsRecyclerViewAdapter adapter;

    public static ListFriendFragment newInstance() {
        ListFriendFragment friendFragment = new ListFriendFragment();

        return friendFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupFriendsRecyclerView();
    }

    private void setupFriendsRecyclerView() {


        recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriends.showEmptyView();

        setupFriendsRecyclerViewAdapter();
    }

    public void setupFriendsRecyclerViewAdapter(){
        adapter = new FriendsRecyclerViewAdapter(getActivity(),new ArrayList<User>());
        recyclerViewFriends.setAdapter(adapter);
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

    @OnClick({R.id.button_search_friend, R.id.button_add_friend, R.id.float_button_friends_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_search_friend:
                break;
            case R.id.button_add_friend:
                break;
            case R.id.float_button_friends_action:
                break;
        }
    }
}
