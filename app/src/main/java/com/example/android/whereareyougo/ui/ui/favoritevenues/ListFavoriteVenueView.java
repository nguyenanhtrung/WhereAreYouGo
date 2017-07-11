package com.example.android.whereareyougo.ui.ui.favoritevenues;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

import java.util.List;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public interface ListFavoriteVenueView extends MvpView {
    void setFavoriteVenues(List<FavoriteVenue> favoriteVenues);
    void setupFavoriteVenuesRecyclerViewAdapter(List<FavoriteVenue> venues);
    void showDeleleVenueDialog(FavoriteVenue venue, int position, int contentId, int typeDelete);
    void removeVenueInRecyclerView(int position);
    void removeAllFavoriteVenuesRecyclerView();
    void showDeleteAllVenueDialog(List<FavoriteVenue> venues);
}
