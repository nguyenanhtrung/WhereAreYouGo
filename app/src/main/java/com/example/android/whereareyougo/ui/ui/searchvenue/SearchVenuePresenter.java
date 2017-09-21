package com.example.android.whereareyougo.ui.ui.searchvenue;

import android.os.Bundle;
import android.util.Log;

import com.example.android.whereareyougo.ui.data.database.entity.VenueCategory;
import com.example.android.whereareyougo.ui.data.database.entity.VenueSearchCondition;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public class SearchVenuePresenter<V extends SearchVenueView> extends BasePresenter<V> implements SearchVenueMvpPresenter<V> {
    private String categoryIdChoose;
    private double radius = 0;


    @Inject
    public SearchVenuePresenter(DataManager dataManager) {
        super(dataManager);
    }

    public void onClickCardVenueCategory(List<VenueCategory> venueCategories, int position, int previousPosition){
        VenueCategory venueCategoryClick = venueCategories.get(position);
        if (previousPosition >= 0){
            VenueCategory previousVenueCategory = venueCategories.get(previousPosition);
            if (!venueCategoryClick.isChoose()){
                previousVenueCategory.setChoose(false);
            }
        }

        if(!venueCategoryClick.isChoose()){
            venueCategoryClick.setChoose(true);
            categoryIdChoose = venueCategoryClick.getId();
            getMvpView().notifyDataChangeForAdapter();
            getMvpView().setPreviousPosition(position);

        }else{
            venueCategoryClick.setChoose(false);
            getMvpView().notifyDataChangeForAdapter();
        }
    }

    @Override
    public void onClickButtonSearch() {
        //get radius from edit text radius
        radius = getMvpView().getSearchVenueRadius();

        VenueSearchCondition searchCondition = new VenueSearchCondition(categoryIdChoose,radius);
        Log.d(MyKey.SEARCH_VENUE_FRAGMENT_TAG,"Radius = " + radius + " - CategoryId = " + categoryIdChoose);
        getMvpView().openMapFragmentBySearchCondition(searchCondition);

        //open map Fragment and send venue category choose, radius to map fragment
       // getMvpView().openMapFragment(bundle);
    }
}
