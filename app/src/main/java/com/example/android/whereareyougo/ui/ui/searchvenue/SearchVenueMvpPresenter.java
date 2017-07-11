package com.example.android.whereareyougo.ui.ui.searchvenue;

import com.example.android.whereareyougo.ui.data.database.entity.VenueCategory;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public interface SearchVenueMvpPresenter<V extends SearchVenueView> extends MvpPresenter<V> {
    void onClickCardVenueCategory(List<VenueCategory> venueCategories, int position, int previousPosition);

    void onClickButtonSearch();
}
