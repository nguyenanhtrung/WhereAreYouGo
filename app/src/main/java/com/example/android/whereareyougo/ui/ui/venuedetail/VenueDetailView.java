package com.example.android.whereareyougo.ui.ui.venuedetail;

import com.example.android.whereareyougo.ui.ui.base.MvpView;

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
}
