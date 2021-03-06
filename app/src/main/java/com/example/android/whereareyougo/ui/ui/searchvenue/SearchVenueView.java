package com.example.android.whereareyougo.ui.ui.searchvenue;

import android.os.Bundle;

import com.example.android.whereareyougo.ui.data.database.entity.VenueSearchCondition;
import com.example.android.whereareyougo.ui.ui.base.MvpView;

/**
 * Created by nguyenanhtrung on 11/07/2017.
 */

public interface SearchVenueView extends MvpView {
    void notifyDataChangeForAdapter();

    void setPreviousPosition(int previousPosition);

    double getSearchVenueRadius();

    void openMapFragmentBySearchCondition(VenueSearchCondition searchCondition);

}
