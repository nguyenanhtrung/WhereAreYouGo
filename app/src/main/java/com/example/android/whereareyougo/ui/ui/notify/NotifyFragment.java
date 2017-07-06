package com.example.android.whereareyougo.ui.ui.notify;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 05/07/2017.
 */

public class NotifyFragment extends BaseFragment implements NotifyView {

    @Inject
    NotifyMvpPresenter<NotifyView> presenter;

    public static NotifyFragment newInstance(){
        NotifyFragment fragment = new NotifyFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_notification,container,false);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
    }
}
