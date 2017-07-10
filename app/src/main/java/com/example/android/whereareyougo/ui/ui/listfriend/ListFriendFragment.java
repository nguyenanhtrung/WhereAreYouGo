package com.example.android.whereareyougo.ui.ui.listfriend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FriendsRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.GridDividerItemDecoration;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.OnBoomListener;
import com.wang.avi.AVLoadingIndicatorView;

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

public class ListFriendFragment extends BaseFragment implements ListFriendView,SearchView.OnQueryTextListener,FriendsRecyclerViewAdapter.OnClickBoomButtonListener {
    @Inject
    ListFriendPresenter<ListFriendView> presenter;
    @BindView(R.id.recycler_view_friends)
    UltimateRecyclerView recyclerViewFriends;

    Unbinder unbinder;

    @BindView(R.id.button_add_friend)
    FloatingActionButton buttonAddFriend;
    @BindView(R.id.float_button_friends_action)
    FloatingActionMenu floatButtonFriendsAction;
    @BindView(R.id.loading_friends)
    AVLoadingIndicatorView loadingFriends;
    @BindView(R.id.search_view_friend)
    SearchView searchViewFriend;

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
        if (presenter != null) {
            presenter.getUserListFriend();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
    }

    private void initUiEvents() {
        searchViewFriend.setOnQueryTextListener(this);
        searchViewFriend.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewFriend.setAlpha(1.0f);
            }
        });
        searchViewFriend.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchViewFriend.setAlpha(0.4f);
                return  true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
    }

    private void setupFriendsRecyclerView() {
        recyclerViewFriends.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Drawable divider1 = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);


        recyclerViewFriends.addItemDecoration(new GridDividerItemDecoration(8, 2));

        recyclerViewFriends.showEmptyView();


        //setupFriendsRecyclerViewAdapter();
    }

    public void showLoading() {
        if (!loadingFriends.isShown()) {
            loadingFriends.show();
        }
    }

    public void hideLoading() {
        if (loadingFriends.isShown()) {
            loadingFriends.hide();
        }
    }

    public void setupFriendsRecyclerViewAdapter(final ArrayList<User> datas) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingFriends.isShown()) {
                    hideLoading();
                    users = datas;
                    adapter = new FriendsRecyclerViewAdapter(getActivity(), users,ListFriendFragment.this);
                    recyclerViewFriends.setAdapter(adapter);
                }
            }
        }, 3000);


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

    @OnClick({R.id.button_add_friend, R.id.float_button_friends_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.button_add_friend:
                openAddFriendDialogFragment();
                break;
            case R.id.float_button_friends_action:
                break;
        }
    }

    public void openAddFriendDialogFragment() {
        interaction.openAddFriendDialogFragment();
    }



    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filterByName(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filterByName(newText);
        return true;
    }




    public void openFriendProfile(User user){
        interaction.openProfileDialogFragment(user);
    }


    //onBoomButton Click
    @Override
    public void onButtonClick(int index, BoomButton boomButton, int position) {
        switch (index){
            case 3: // index of button see friend profile
                if (presenter != null){
                    presenter.onClickButtonSeeProfile(users.get(position));
                }
                //Toast.makeText(getActivity(), "Friend Name = " + users.get(position).getName(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public interface InteractionWithListFriendFragment {
        void openAddFriendDialogFragment();
        void openProfileDialogFragment(User user);
    }
}
