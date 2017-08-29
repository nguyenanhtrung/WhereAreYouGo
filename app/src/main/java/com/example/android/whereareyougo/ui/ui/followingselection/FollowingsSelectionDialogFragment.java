package com.example.android.whereareyougo.ui.ui.followingselection;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.di.component.ActivityComponent;
import com.example.android.whereareyougo.ui.ui.adapter.FriendsMapSelectionAdapter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 20/08/2017.
 */

public class FollowingsSelectionDialogFragment extends DialogFragment implements FollowingSelectionView, FriendsMapSelectionAdapter.FriendsMapClickListener,
        View.OnClickListener {

    @Inject
    FollowingSelectionMvpPresenter<FollowingSelectionView> presenter;
    @BindView(R.id.text_num_of_following)
    TextView textNumOfFollowing;
    @BindView(R.id.recycler_view_friends)
    UltimateRecyclerView recyclerViewFriends;
    @BindView(R.id.button_add_following)
    Button buttonAddFollowing;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    Unbinder unbinder;
    @BindView(R.id.loading_followings)
    AVLoadingIndicatorView loadingFollowings;
    private InteractionWithFFollowingSelection interaction;
    private ArrayList<String> followingIds;
    private FriendsMapSelectionAdapter adapter;
    private ArrayList<User> followings;
    private HashMap<String, User> followingsSelected;
    private int maxSelectFollowing = 0;

    public static FollowingsSelectionDialogFragment newInstance(ArrayList<String> followingIds) {
        FollowingsSelectionDialogFragment fragment = new FollowingsSelectionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("followingIds", followingIds);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            followingIds = bundle.getStringArrayList("followingIds");
            if (followingIds == null) {
                followingIds = new ArrayList<>();
            }
            setMaxSelectFollowing();
        }
    }

    private void setMaxSelectFollowing(){
        if (followingIds.size() > MyKey.MAX_FOLLOWING_SELECT){
            maxSelectFollowing = MyKey.MAX_FOLLOWING_SELECT;
        }else{
            maxSelectFollowing = followingIds.size();
        }
    }

    public int getMaxSelectFollowing(){
        return maxSelectFollowing;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interaction = (InteractionWithFFollowingSelection) context;
        interaction.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_map_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        loadingFollowings.hide();
        setupRecyclerViewFollowings();
        if (presenter != null) {
            presenter.getFollowingsInfo(followingIds);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initUiEvents();
    }

    private void initUiComponents() {
        textNumOfFollowing.setText(new StringBuilder().append("0/").append(String.valueOf(followingIds.size())).toString());
    }

    private void initUiEvents() {
        buttonAddFollowing.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setSizeOfDialog() {
        int width = 1000;
        int height = 1100;
        getDialog().getWindow().setLayout(
                width, height
        );
        getDialog().getWindow().setGravity(Gravity.CENTER);


    }

    private void setupRecyclerViewFollowings() {
        recyclerViewFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFriends.showEmptyView();
        //

    }

    public void setupDatasForRecyclerViewFollowings(ArrayList<User> datas) {
        if (datas != null) {
            this.followings = datas;
            adapter = new FriendsMapSelectionAdapter(getActivity(), followings, this);
            recyclerViewFriends.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        setSizeOfDialog();
        super.onResume();


    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void onError(String message, Activity activity) {

    }

    @Override
    public void hideKeyboard() {

    }

    public void setTextNumOfFollowing(String numOfFollowing) {
        if (numOfFollowing != null) {
            textNumOfFollowing.setText(numOfFollowing);
        }
    }

    public HashMap<String, User> getFollowingsSelected() {
        if (followingsSelected == null) {
            followingsSelected = new HashMap<>();
        }
        return followingsSelected;
    }

    public ArrayList<String> getFollowingIds(){
        return followingIds;
    }

    public void addFollowingSelected(User user) {
        if (followingsSelected == null) {
            followingsSelected = new HashMap<>();
        } else {
            if (user != null) {
                followingsSelected.put(user.getUserID(), user);
            }
        }
    }

    public void removeFollowingSelected(String userId) {
        if (followingsSelected != null) {
            if (userId != null) {
                followingsSelected.remove(userId);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    //Click item recyclerView
    @Override
    public void onClickItem(View view, int position) {
        if (presenter != null) {
            presenter.onClickFollowingsRecyclerViewItem(followings.get(position), view);
        }
    }

    public HashMap<String,User> getFollowingsAfterSelected() {
        return followingsSelected;
    }

    public void dismissDialog() {
        if (isVisible()) {
            dismiss();
        }
    }


    //Click button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_following:
                if (presenter != null) {
                    //followingsSelected null
                    presenter.onClickButtonAddFollowings(followingsSelected.size());
                }
                break;
            case R.id.button_cancel:
                if (presenter != null) {
                    presenter.onClickButtonCloseDialog();
                }
                break;
        }
    }

    public void sendFollowingsToFriendMapFragment() {
        interaction.sendFollowingsToFriendMapFragment();
    }

    public interface InteractionWithFFollowingSelection {
        ActivityComponent getActivityComponent();

        void sendFollowingsToFriendMapFragment();

    }
}
