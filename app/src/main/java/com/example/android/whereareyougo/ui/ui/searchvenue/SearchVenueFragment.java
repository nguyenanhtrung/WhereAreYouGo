package com.example.android.whereareyougo.ui.ui.searchvenue;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.VenueCategory;
import com.example.android.whereareyougo.ui.data.database.entity.VenueSearchCondition;
import com.example.android.whereareyougo.ui.ui.base.BaseFragment;
import com.example.android.whereareyougo.ui.ui.custom.GridDividerItemDecoration;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public class SearchVenueFragment extends BaseFragment implements SearchVenueView, View.OnClickListener, VenueCategoryAdapter.MyCardClickListener {
    @Inject
    SearchVenueMvpPresenter<SearchVenueView> presenter;
    @BindView(R.id.edit_text_radius)
    MaterialEditText editTextRadius;
    @BindView(R.id.recycler_view_category)
    SuperRecyclerView recyclerViewCategory;
    @BindView(R.id.button_search)
    Button buttonSearch;
    Unbinder unbinder;
    private VenueCategoryAdapter adapter;
    private List<VenueCategory> venueCategories;
    private int previousPosition = -1;
    private InteractionWithSearchVenueFragment interaction;


    public static SearchVenueFragment newInstance() {
        SearchVenueFragment fragment = new SearchVenueFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_venue, container, false);
        unbinder = ButterKnife.bind(this, view);
        //
        initUiComponents();
        setupVenueCategoryRecyclerView();

        return view;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    private void setupVenueCategoryRecyclerView() {
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewCategory.addItemDecoration(new GridDividerItemDecoration(3, 100));

        //setup data for recyclerview
        venueCategories = getVenueCategoriesForRecyclerView();

        //adapter for recyclerview
        adapter = new VenueCategoryAdapter(getActivity(), venueCategories, this);
        recyclerViewCategory.setAdapter(adapter);
    }

    private ArrayList<VenueCategory> getVenueCategoriesForRecyclerView() {
        ArrayList<VenueCategory> datas = new ArrayList<>();
        datas.add(new VenueCategory("book_store", getString(R.string.text_book_store), R.drawable.ic_book_store));
        datas.add(new VenueCategory("cafe", getString(R.string.text_cafe), R.drawable.ic_cafe));
        datas.add(new VenueCategory("clothing_store", getString(R.string.text_clothing_store), R.drawable.ic_clothing_store));
        datas.add(new VenueCategory("hospital", getString(R.string.text_hospital), R.drawable.ic_hospital));
        datas.add(new VenueCategory("atm", getString(R.string.text_atm), R.drawable.ic_atm));
        datas.add(new VenueCategory("gym", getString(R.string.text_gym), R.drawable.ic_gym));
        datas.add(new VenueCategory("restaurant", getString(R.string.text_restaurant), R.drawable.ic_restaurant));
        datas.add(new VenueCategory("shoe_store", getString(R.string.text_shoe_store), R.drawable.ic_shoe_store));
        datas.add(new VenueCategory("movie_theater", getString(R.string.text_movie_theater), R.drawable.ic_movie_theater));

        return datas;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiEvents();
    }

    private void initUiEvents() {
        buttonSearch.setOnClickListener(this);
    }

    private void initUiComponents() {
        editTextRadius.setBackgroundResource(R.drawable.background_edittext_selector);

    }

    public void notifyDataChangeForAdapter(){
        adapter.notifyDataSetChanged();
    }


    public void openMapFragmentBySearchCondition(VenueSearchCondition searchCondition){
        interaction.openMapFragmentFromSearchFragment(searchCondition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Callback callback = (Callback) context;
        callback.getActivityComponent().inject(this);
        presenter.onAttach(this);
        interaction = (InteractionWithSearchVenueFragment) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_search:
                //
                presenter.onClickButtonSearch();
                break;
        }
    }

    public double getSearchVenueRadius(){
        if (editTextRadius.getText().toString().isEmpty()){
            return -1;
        }

        return Double.parseDouble(editTextRadius.getText().toString());
    }

    @Override
    public void onCardClick(View v, int position) {
        //TextView textCategoryChoose = (TextView) v.findViewById(R.id.text_category_choose);
        if (presenter != null){
            presenter.onClickCardVenueCategory(venueCategories,position,previousPosition);
        }
    }

    public interface InteractionWithSearchVenueFragment{
        void openMapFragmentFromSearchFragment(VenueSearchCondition searchCondition);
    }
}
