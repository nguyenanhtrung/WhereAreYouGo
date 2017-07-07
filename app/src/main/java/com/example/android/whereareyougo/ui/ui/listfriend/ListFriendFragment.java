package com.example.android.whereareyougo.ui.ui.listfriend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FriendsRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;
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

public class ListFriendFragment extends BaseFragment implements ListFriendView,FriendsRecyclerViewAdapter.onClickListener {
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

    private InteractionWithListFriendFragment interaction;
    private List<User> users;

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    private List<Friend> friends;
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

        setupFriendsRecyclerView();
        if (presenter != null){
            presenter.getUserListFriend();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void setupFriendsRecyclerView() {
        recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriends.showEmptyView();

        Drawable divider = ContextCompat.getDrawable(getActivity(),R.drawable.divider_recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(divider);
        recyclerViewFriends.addItemDecoration(itemDecoration);

        //setupFriendsRecyclerViewAdapter();
    }

    public void setupFriendsRecyclerViewAdapter(ArrayList<User> datas){
        users = datas;
        adapter = new FriendsRecyclerViewAdapter(getActivity(),users,ListFriendFragment.this);
        recyclerViewFriends.setAdapter(adapter);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        interaction = (InteractionWithListFriendFragment) context;
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
                openAddFriendDialogFragment();
                break;
            case R.id.float_button_friends_action:
                break;
        }
    }

    public void openAddFriendDialogFragment(){
        interaction.openAddFriendDialogFragment();
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    public interface InteractionWithListFriendFragment{
        void openAddFriendDialogFragment();
    }
}
