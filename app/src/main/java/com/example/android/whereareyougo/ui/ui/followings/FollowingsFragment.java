package com.example.android.whereareyougo.ui.ui.followings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FollowersRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.adapter.FollowingsRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 17/08/2017.
 */

public class FollowingsFragment extends BaseFragment implements FollowingsView {

    @Inject
    FollowingsMvpPresenter<FollowingsView> presenter;
    @BindView(R.id.search_view_following)
    SearchView searchViewFollowing;
    @BindView(R.id.recycler_view_followings)
    UltimateRecyclerView recyclerViewFollowings;
    private FollowingsRecyclerViewAdapter adapter;
    private ArrayList<User> followings;
    private MaterialDialog loadingDialog;
    Unbinder unbinder;


    public static FollowingsFragment newInstance(){
        FollowingsFragment followingsFragment = new FollowingsFragment();

        return followingsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followings, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        setupFollowingsRecyclerView();
        showLoadingDialog();
        if (presenter != null){
            presenter.showUserFollowings();
        }

        return view;
    }

    private void setupFollowingsRecyclerView() {
        recyclerViewFollowings.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFollowings.showEmptyView();
        //

    }

    public void setDataForFollowingsRecyclerViewAdapter(ArrayList<User> datas){
        followings  = datas;
        adapter = new FollowingsRecyclerViewAdapter(getActivity(),followings);
        recyclerViewFollowings.setAdapter(adapter);
    }

    public void showLoadingDialog(){
        loadingDialog = new MaterialDialog.Builder(getActivity())
                    .titleColorRes(R.color.colorAccent)
                    .title(R.string.title_loading_dialog)
                    .contentColorRes(R.color.colorSecondaryText)
                    .content(R.string.content_loading_dialog)
                    .progress(true,4)
                    .build();
        loadingDialog.show();
    }

    public void hideLoadingDialog(){
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }
}
