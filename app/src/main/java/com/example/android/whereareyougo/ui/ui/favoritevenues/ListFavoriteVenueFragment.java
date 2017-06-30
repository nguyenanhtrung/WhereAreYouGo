package com.example.android.whereareyougo.ui.ui.favoritevenues;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.ui.adapter.FavoriteVenuesRecyclerViewAdapter;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.DividerItemDecoration;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public class ListFavoriteVenueFragment extends BaseFragment implements ListFavoriteVenueView {

    @BindView(R.id.recycler_view_favorite_venues)
    UltimateRecyclerView recyclerViewFavoriteVenues;
    @BindView(R.id.text_recycler_view_empty)
    TextView textRecyclerViewEmpty;
    Unbinder unbinder;
    @Inject
    ListFavoriteVenueMvpPresenter<ListFavoriteVenueView> presenter;
    FavoriteVenuesRecyclerViewAdapter adapter;

    public List<FavoriteVenue> getFavoriteVenues() {
        return favoriteVenues;
    }

    public void setFavoriteVenues(List<FavoriteVenue> favoriteVenues) {
        this.favoriteVenues = favoriteVenues;
    }

    List<FavoriteVenue> favoriteVenues;


    public static ListFavoriteVenueFragment newInstance() {
        ListFavoriteVenueFragment favoriteVenueFragment = new ListFavoriteVenueFragment();

        return favoriteVenueFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_venues, container, false);

        unbinder = ButterKnife.bind(this, view);
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

        setupFavoriteVenuesRecyclerView();
    }

    private void setupFavoriteVenuesRecyclerView() {
        favoriteVenues = new ArrayList<>();

        //favoriteVenues = presenter.getFavoriteVenues();
        presenter.getFavoriteVenues();


        Drawable dividerDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.divider_recycler_view);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        recyclerViewFavoriteVenues.addItemDecoration(dividerItemDecoration);

        recyclerViewFavoriteVenues.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setSwipetoDismissRecyclerView() {
    }

    public void dragList() {
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(adapter);
        final ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewFavoriteVenues.mRecyclerView);
        adapter.setOnDragStartListener
                (new FavoriteVenuesRecyclerViewAdapter.OnStartDragListener() {
                    @Override
                    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                        mItemTouchHelper.startDrag(viewHolder);
                    }
                });
    }

    public void setupFavoriteVenuesRecyclerViewAdapter(List<FavoriteVenue> favoriteVenues) {
        if (favoriteVenues.isEmpty()) {
            textRecyclerViewEmpty.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new FavoriteVenuesRecyclerViewAdapter(getActivity(), favoriteVenues);
        recyclerViewFavoriteVenues.setAdapter(adapter);

        dragList();

        //adapter.setS


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
