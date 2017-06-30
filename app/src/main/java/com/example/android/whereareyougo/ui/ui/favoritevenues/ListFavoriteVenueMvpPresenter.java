package com.example.android.whereareyougo.ui.ui.favoritevenues;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

import java.util.List;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public interface ListFavoriteVenueMvpPresenter<V extends ListFavoriteVenueView> extends MvpPresenter<V> {
    void getFavoriteVenues();
}
