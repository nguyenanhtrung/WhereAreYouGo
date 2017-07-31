package com.example.android.whereareyougo.ui.ui.followers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.User;
import com.example.android.whereareyougo.ui.ui.adapter.FollowersRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 31/07/2017.
 */

public class FollowersFragment extends BaseFragment implements FollowersView,FollowersRecyclerViewAdapter.MyItemClickListener {

    @Inject
    FollowersMvpPresenter<FollowersView> presenter;
    @BindView(R.id.recycler_view_followers)
    UltimateRecyclerView recyclerViewFollowers;
    Unbinder unbinder;
    private ArrayList<User> followers;
    private FollowersRecyclerViewAdapter adapter;
    private MaterialDialog loadingDialog;


    public static FollowersFragment newInstance(){
        FollowersFragment fragment = new FollowersFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        setupRecyclerViewFollowers();
        showLoadingDialog();


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.showUserFollowers();
    }

    private void setupRecyclerViewFollowers() {
        recyclerViewFollowers.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFollowers.showEmptyView();

        //
    }

    public void showLoadingDialog(){
        loadingDialog = new MaterialDialog.Builder(getActivity())
                            .title(R.string.title_loading_followers_dialog)
                            .titleColorRes(R.color.colorAccent)
                            .content(R.string.content_loading_followers_dialog)
                            .progress(true,3)
                            .build();
        loadingDialog.show();

    }

    public void dismissLoadingDialog(){
        if (loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    public void setFollowers(ArrayList<User> followers){
        this.followers = followers;
    }

    public void setupDatasForRecyclerViewFollowers(ArrayList<User> datas){
        followers = datas;

        //
        adapter = new FollowersRecyclerViewAdapter(getActivity(),followers,this);
        recyclerViewFollowers.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View v, int position) {

    }
}
