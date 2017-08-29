package com.example.android.whereareyougo.ui.ui.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.ui.base.BaseDialogFragment;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class ListVenueDialogFragment extends BaseDialogFragment implements View.OnClickListener {


    @BindView(R.id.recycler_view_venues)
    RecyclerView recyclerViewVenues;
    @BindView(R.id.text_num_of_result)
    TextView textNumOfResult;
    Unbinder unbinder;
    @BindView(R.id.button_okay)
    Button buttonOkay;

    private ArrayList<Result> venues;
    private ArrayList<Result> venuesSelected;
    private InteractionWithVenuesDialogFragment interactionWithFragment;

    public ListVenueDialogFragment() {

    }

    public static ListVenueDialogFragment newInstance(ArrayList<Result> results) {
        ListVenueDialogFragment fragment = new ListVenueDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("result", results);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            venues = bundle.getParcelableArrayList("result");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venues_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        interactionWithFragment = (InteractionWithVenuesDialogFragment) context;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initEvents(){
        buttonOkay.setOnClickListener(this);
    }

    /*private void setSizeOfDialog() {
        int width = 750;
        int height = 1090;
        getDialog().getWindow().setLayout(
                width, height
        );
        getDialog().getWindow().setGravity(Gravity.CENTER);

    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvents();
        setupRecyclerViewVenues();
        setNumberOfVenuesFound();
    }

    private void setNumberOfVenuesFound() {
        StringBuilder builder = new StringBuilder();
        builder.append(getResources().getString(R.string.text_num_of_result));
        builder.append(": ");
        builder.append(venues.size());
        textNumOfResult.setText(builder.toString());
    }

    private void setupRecyclerViewVenues() {
        if (venues == null) {
            venues = new ArrayList<>();
        }
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(getResources().getDrawable(R.drawable.divider_recycler_view));
        recyclerViewVenues.addItemDecoration(dividerItemDecoration);

        VenuesRecyclerViewAdapter adapter = new VenuesRecyclerViewAdapter(venues, getActivity(), new VenuesRecyclerViewAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Result result) {
                venuesSelected.add(result);
                Toast.makeText(getActivity(), "" + result.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemUncheck(Result result) {
                venuesSelected.remove(result);
            }
        });

        recyclerViewVenues.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewVenues.setAdapter(adapter);
    }

    public ArrayList<Result> getVenuesSelected(){
        return venuesSelected;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_okay:
                interactionWithFragment.onClickButtonOkay();
                break;
        }
    }

    @Override
    public void onError(String message, Activity activity) {

    }

    public interface InteractionWithVenuesDialogFragment{
        void onClickButtonOkay();
    }
}
