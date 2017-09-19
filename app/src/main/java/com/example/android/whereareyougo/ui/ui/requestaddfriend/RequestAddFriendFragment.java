package com.example.android.whereareyougo.ui.ui.requestaddfriend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class RequestAddFriendFragment extends BaseFragment implements RequestAddFriendView, RequestAddFriendAdapter.OnClickListener {
    @Inject
    RequestAddFriendMvpPresenter<RequestAddFriendView> presenter;
    @BindView(R.id.recycler_view_invitations)
    SuperRecyclerView recyclerViewInvitations;
    Unbinder unbinder;
    //private ArrayList<RequestAddFriend> requestAddFriends;
    private ArrayList<User> userRequests;
    private RequestAddFriendAdapter adapter;

    public static RequestAddFriendFragment newInstance(ArrayList<User> userRequests) {
        RequestAddFriendFragment fragment = new RequestAddFriendFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("requests", userRequests);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userRequests = bundle.getParcelableArrayList("requests");
        }
        if (userRequests == null){
            userRequests = new ArrayList<>();
        }
    }

    public void setUserRequests(ArrayList<User> userRequests) {
        this.userRequests = userRequests;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_invitation, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        setupRequestFriendsRecyclerView();
        setupDatasForRequestFriendsRecyclerView();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void setupRequestFriendsRecyclerView() {
        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(divider);
        recyclerViewInvitations.addItemDecoration(itemDecoration);
        recyclerViewInvitations.setLayoutManager(new LinearLayoutManager(getActivity()));
        //
    }

    public void setupDatasForRequestFriendsRecyclerView() {
        adapter = new RequestAddFriendAdapter(userRequests, getActivity(), this);
        recyclerViewInvitations.setAdapter(adapter);
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

    public void showMessage(int messageId) {
        Snackbar.make(getView(), messageId, 2000).show();
    }

    public void removeRequestInRecyclerView(int position) {
        adapter.removeItem(position);
    }

    @Override
    public void onClickItem(View view, int position) {
        switch (view.getId()) {
            case R.id.button_accept:
                //Toast.makeText(getActivity(), "Button Accept", Toast.LENGTH_SHORT).show();
                if (presenter != null) {
                    presenter.onClickButtonAccept(userRequests.get(position).getUserID(), position);
                }
                break;
            case R.id.button_cancel:
                //Toast.makeText(getActivity(), "Button Cancel", Toast.LENGTH_SHORT).show();
                if (presenter != null) {
                    presenter.onClickButtonCancel(userRequests.get(position).getUserID(), position);
                }
                break;
        }
    }
}
