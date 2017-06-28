package com.example.android.whereareyougo.ui.ui.venuedetail;

import com.example.android.whereareyougo.ui.ui.base.MvpPresenter;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public interface VenueDetailMvpPresenter<V extends VenueDetailView> extends MvpPresenter<V> {
    void showVenueDetailByVenueId(String venueId);
}
