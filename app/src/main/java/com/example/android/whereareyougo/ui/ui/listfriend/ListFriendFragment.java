package com.example.android.whereareyougo.ui.ui.listfriend;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Friend;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.GridDividerItemDecoration;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
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

public class ListFriendFragment extends BaseFragment implements ListFriendView, SearchView.OnQueryTextListener, FriendsRecyclerViewAdapter.OnClickBoomButtonListener {
    @Inject
    ListFriendPresenter<ListFriendView> presenter;
    @BindView(R.id.recycler_view_friends)
    SuperRecyclerView recyclerViewFriends;

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
        showLoading();
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
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setupFriendsRecyclerView() {
        recyclerViewFriends.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //Drawable divider1 = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);
        recyclerViewFriends.addItemDecoration(new GridDividerItemDecoration(8, 2));

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

    public void removeFriendInRecyclerView(int position) {
        adapter.removeItem(position);
    }

    public void setupFriendsRecyclerViewAdapter(final ArrayList<User> datas) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                users = datas;
                adapter = new FriendsRecyclerViewAdapter(getActivity(), users, ListFriendFragment.this);
                recyclerViewFriends.setAdapter(adapter);

            }
        }, 3000);


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
        interaction = (InteractionWithListFriendFragment) context;

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

    public void showAskUnfriendDialog(final String friendId, final int friendPosition) {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.title_ask_unfriend_dialog)
                .content(R.string.content_ask_unfriend_dialog)
                .positiveText(R.string.text_agree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //if user click, then delete friend by friend id, delelete all messages between user and friend,
                        //delete all last message
                        //
                        if (presenter != null) {
                            presenter.onClickButtonAgreeUnfriendDialog(friendId, friendPosition);
                        }
                    }
                })
                .negativeText(R.string.text_disagree)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }


    public void openChatDialogFragment(User friend) {
        interaction.openChatDialogFragment(friend);
    }

    public void openFriendProfile(User user) {
        interaction.openProfileDialogFragment(user);
    }


    //onBoomButton Click
    @Override
    public void onButtonClick(int index, BoomButton boomButton, int position) {
        switch (index) {
            case 0: // index of button follow/unfollow
                if (presenter != null) {
                    //boomButton.getTextView().setText("Huy theo doi");
                    presenter.onClickButtonFollow(users.get(position));


                }
                break;
            case 1: //index of button unfriend
                if (presenter != null) {
                    presenter.onClickButtonUnfriend(users.get(position).getUserID(), position);
                }
                break;
            case 2: // index of button chat
                if (presenter != null) {
                    presenter.onClickButtonChat(users.get(position));
                }
                break;
            case 3: // index of button see friend profile
                if (presenter != null) {
                    presenter.onClickButtonSeeProfile(users.get(position));
                }
                //Toast.makeText(getActivity(), "Friend Name = " + users.get(position).getName(), Toast.LENGTH_SHORT).show();
                break;
            case 5: // index of button call phone
                if (presenter != null) {
                    presenter.onClickButtonCall(users.get(position));
                }
                break;

        }
        //Toast.makeText(getActivity(), "INDEX = " + index, Toast.LENGTH_SHORT).show();
    }

    public void callPhone(String phoneNumber) {
        if (phoneNumber != null) {
            interaction.callPhone(phoneNumber);
        } else {
            //show message to user: friend phone number not exists
        }
    }


    public void showMessage(int messageId) {
        Snackbar.make(getView(), messageId, 2000).show();
    }


    public interface InteractionWithListFriendFragment {
        void openAddFriendDialogFragment();

        void openProfileDialogFragment(User user);

        void openChatDialogFragment(User friend);

        String getCurrentUserId();

        void callPhone(String phoneNumber);
    }
}
