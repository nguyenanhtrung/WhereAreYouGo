package com.example.android.whereareyougo.ui.ui.requestfollow;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class ListRequestFollowFragment extends BaseFragment implements ListRequestFollowView {

    @Inject
    ListRequestFollowMvpPresenter<ListRequestFollowView> presenter;
    @BindView(R.id.recyclerview_request_follow)
    UltimateRecyclerView recyclerviewRequestFollow;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_follow, container, false);
        unbinder = ButterKnife.bind(this, view);
        //

        return view;
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
