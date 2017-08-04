package com.example.android.whereareyougo.ui.ui.venuedetail;

import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.VenuePhoto;
import com.example.android.whereareyougo.ui.ui.base.MvpView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public interface VenueDetailView extends MvpView {
    void showVenueName(String venueName);

    void showVenueAddress(String venueAddress);

    void showVenuePriceLevel(int priceLevel);

    void showVenueRating(double rating);

    void showVenuePhoneNumber(String phoneNumber);

    void showVenueStatus(boolean status);

    void setDataForRecyclerViewVenuePhotos(List<VenuePhoto> venuePhotos);

    void setupRecyclerViewVenuePhotos();

    void checkCallPhonePermissions();

    void callPhone();

    void dismissDialog();

    void drawPolyLineOnMap(LatLng destination);

    void showMessage(int messageId);

}
