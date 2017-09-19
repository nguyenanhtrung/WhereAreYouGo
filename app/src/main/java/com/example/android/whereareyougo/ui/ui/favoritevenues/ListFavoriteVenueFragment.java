package com.example.android.whereareyougo.ui.ui.favoritevenues;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.GridDividerItemDecoration;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.malinskiy.superrecyclerview.SuperRecyclerView;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public class ListFavoriteVenueFragment extends BaseFragment implements ListFavoriteVenueView, FavoriteVenuesRecyclerViewAdapter.MyItemClickListener,View.OnClickListener {

    @BindView(R.id.recycler_view_favorite_venues)
    SuperRecyclerView recyclerViewFavoriteVenues;
    @BindView(R.id.text_recycler_view_empty)
    TextView textRecyclerViewEmpty;
    Unbinder unbinder;
    @Inject
    ListFavoriteVenueMvpPresenter<ListFavoriteVenueView> presenter;
    FavoriteVenuesRecyclerViewAdapter adapter;
    List<FavoriteVenue> favoriteVenues;
    @BindView(R.id.button_delete_all)
    Button buttonDeleteAll;

    public List<FavoriteVenue> getFavoriteVenues() {
        return favoriteVenues;
    }

    public void setFavoriteVenues(List<FavoriteVenue> favoriteVenues) {
        this.favoriteVenues = favoriteVenues;
    }


    public static ListFavoriteVenueFragment newInstance() {
        ListFavoriteVenueFragment favoriteVenueFragment = new ListFavoriteVenueFragment();

        return favoriteVenueFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_venues, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupFavoriteVenuesRecyclerView();
        presenter.getFavoriteVenues();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();

    }

    private void initUiEvents() {
        buttonDeleteAll.setOnClickListener(this);
    }

    private void setupFavoriteVenuesRecyclerView() {
        // favoriteVenues = new ArrayList<>();

        //favoriteVenues = presenter.getFavoriteVenues();

        recyclerViewFavoriteVenues.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewFavoriteVenues.addItemDecoration(new GridDividerItemDecoration(8, 2));
    }





    public void setupFavoriteVenuesRecyclerViewAdapter(List<FavoriteVenue> venues) {
        favoriteVenues = new ArrayList<>();
        favoriteVenues = venues;

        adapter = new FavoriteVenuesRecyclerViewAdapter(getActivity(), favoriteVenues, this);
        recyclerViewFavoriteVenues.setAdapter(adapter);

    }

    public void showDeleleVenueDialog(final FavoriteVenue venue, final int position, int contentId, final int typeDelete) {
        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.title_delete_venue_dialog))
                .content(contentId)
                .positiveText(R.string.text_agree)
                .negativeText(R.string.text_disagree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (presenter != null) {
                            if (typeDelete == MyKey.DELETE_ONE){
                                presenter.onClickAgreeDeleteDialog(venue, position);
                            }

                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .build().show();

    }

    public void showDeleteAllVenueDialog(final List<FavoriteVenue> venues){
        new MaterialDialog.Builder(getActivity())
                .title(getString(R.string.title_delete_venue_dialog))
                .content(R.string.content_delete_all_venue)
                .contentColor(ContextCompat.getColor(getActivity(),R.color.md_red_500))
                .positiveText(R.string.text_agree)
                .negativeText(R.string.text_disagree)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (presenter != null) {
                            presenter.onClickAgreeDeleteAll(venues);
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .build().show();
    }

    public void removeVenueInRecyclerView(int position) {
        adapter.removeItem(position);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //onClick Button in RecyclerView
    @Override
    public void onButtonClick(View v, int position) {
        switch (v.getId()) {
            case R.id.button_delete:
                if (presenter != null) {
                    presenter.onClickButtonDeleteVenue(favoriteVenues.get(position), position);
                    //Toast.makeText(getActivity(), "Name = " + favoriteVenues.get(position).getName(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void removeAllFavoriteVenuesRecyclerView(){
        adapter.clear();
    }


    //onClick Button in Layout
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_delete_all:
                if (presenter != null){
                    presenter.onClickButtonDeleteAllVenue(favoriteVenues);
                }
                break;
        }
    }
}
