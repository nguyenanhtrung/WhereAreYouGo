package com.example.android.whereareyougo.ui.ui.requestfollow;

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
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class ListRequestFollowFragment extends BaseFragment implements ListRequestFollowView,RequestFollowAdapter.MyClickItemListener {

    @Inject
    ListRequestFollowMvpPresenter<ListRequestFollowView> presenter;
    @BindView(R.id.recyclerview_request_follow)
    SuperRecyclerView recyclerViewRequestFollow;
    Unbinder unbinder;
    private ArrayList<User> requestFollows;
    private RequestFollowAdapter adapter;


    public static ListRequestFollowFragment newInstance(ArrayList<User> requestFollows){
        ListRequestFollowFragment fragment = new ListRequestFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("requestfollow",requestFollows);
        fragment.setArguments(bundle);

        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_follow, container, false);
        unbinder = ButterKnife.bind(this, view);
        //



        //setup recyclerview
        setupRequestFollowRecyclerView();

        return view;
    }

    private void setupRequestFollowRecyclerView() {

        Drawable divider = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(divider);
        recyclerViewRequestFollow.addItemDecoration(itemDecoration);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewRequestFollow.setLayoutManager(layoutManager);
        //
        if (requestFollows == null){
            requestFollows = new ArrayList<>();
        }
        adapter = new RequestFollowAdapter(getActivity(),requestFollows,this);
        recyclerViewRequestFollow.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            requestFollows = bundle.getParcelableArrayList("requestfollow");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }

    public void removeRequestFollowRecyclerView(int position){
        adapter.removeItem(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (v.getId()){
            case R.id.button_accept:
                if (presenter != null){
                    presenter.onClickButtonAccept(requestFollows.get(position).getUserID(),position);
                }
                break;
            case R.id.button_cancel:
                if (presenter != null){
                    presenter.onClickButtonCancel(requestFollows.get(position).getUserID(),position);
                }
                break;
        }
    }
}
