package com.example.android.whereareyougo.ui.ui.map;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.ui.adapter.VenuesRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class ListVenueDialogFragment extends DialogFragment {


    @BindView(R.id.recycler_view_venues)
    UltimateRecyclerView recyclerViewVenues;
    @BindView(R.id.text_num_of_result)
    TextView textNumOfResult;
    Unbinder unbinder;

    private ArrayList<Result> venues;

    public ListVenueDialogFragment() {

    }

    public static  ListVenueDialogFragment newInstance(ArrayList<Result> results){
        ListVenueDialogFragment fragment = new ListVenueDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("result",results);

        fragment.setArguments(bundle);

        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);

        Bundle bundle = getArguments();
        if (bundle != null){
            venues = bundle.getParcelableArrayList("result");
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSizeOfDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return super.onCreateDialog(savedInstanceState);

    }

    private void setSizeOfDialog() {
        int width = 750;
        int height = 900;
        getDialog().getWindow().setLayout(
                width,height
        );
        getDialog().getWindow().setGravity(Gravity.CENTER);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerViewVenues();
    }

    private void setupRecyclerViewVenues(){
        if (venues == null){
            return;
        }

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getResources().getDrawable(R.drawable.divider_recycler_view));
        recyclerViewVenues.addItemDecoration(dividerItemDecoration);

        VenuesRecyclerViewAdapter adapter = new VenuesRecyclerViewAdapter(venues,getActivity());

        recyclerViewVenues.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerViewVenues.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
