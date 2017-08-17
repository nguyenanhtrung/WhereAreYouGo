package com.example.android.whereareyougo.ui.ui.map;

import android.os.Bundle;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.data.database.entity.VenueResponse;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.data.remote.ApiHelper;
import com.example.android.whereareyougo.ui.data.remote.RetrofitClient;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenanhtrung on 15/06/2017.
 */

public class MapPresenter<V extends MapMvpView> extends BasePresenter<V> implements
        MapMvpPresenter<V> {
    private DatabaseReference userLocationRef;

    @Inject
    public MapPresenter(DataManager dataManager) {
        super(dataManager);
    }




    @Override
    public int getMapTypeChoose(String[] maptype, String choose) {
        if (choose.equals(maptype[0])) {
            return MyKey.MAP_TYPE_NORMAL;
        }

        if (choose.equals(maptype[1])) {
            return MyKey.MAP_TYPE_HYBRID;
        }

        if (choose.equals(maptype[2])) {
            return MyKey.MAP_TYPE_SATELLITE;
        }

        if (choose.equals(maptype[3])) {
            return MyKey.MAP_TYPE_TERRAIN;
        }

        return MyKey.MAP_TYPE_NONE;
    }

    @Override
    public void onClickFloatButtonMapType() {
        getMvpView().showDialogChooseMapType();
    }

    @Override
    public void onClickButtonSearchVenue(String venueName) {
        if (venueName.isEmpty() || venueName == null) {
            //show venue name is empty
            getMvpView().showMessage(R.string.text_search_venue_empty);
            return;
        }

        ApiHelper.getAPIService()
                .getVenueByName(MyKey.GOOGLE_PLACES_KEY,
                        getMvpView().getCurrentUserLocation(),
                        MyKey.VENUE_RADIUS_DEFAULT,
                        venueName)
                .enqueue(new Callback<VenueResponse>() {
                    @Override
                    public void onResponse(Call<VenueResponse> call, Response<VenueResponse> response) {
                        getMvpView().removeAllVenueMarkerItems();
                        getMvpView().showLoadingDialog(R.string.title_loading_search_venue, R.string.content_loading_search_venue);
                        VenueResponse venueResponse = response.body();
                        if (venueResponse != null) {
                            ArrayList<Result> results = venueResponse.getResults();
                            if (results != null) {
                                //delay
                                getMvpView().openListVenueDialogFragment(results);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VenueResponse> call, Throwable t) {

                    }
                });
    }

    public void getVenuesByRadiusAndCategory(Bundle bundle) {
        //get radius from bundle
        double radius = bundle.getDouble("radius");
        //check if radius = -1 => set radius default = 10000
        if (radius == -1) {
            radius = 10000;
        }
        //
        //get venue category id from bundle
        String venueCategoryId = bundle.getString("categoryid");
        if (venueCategoryId == null) {
            return;
        }

        //get Venues from Firebase Database
        ApiHelper.getAPIService().getVenuesByTypeAndRadius(
                MyKey.GOOGLE_PLACES_KEY,
                getMvpView().getCurrentUserLocation(),
                radius,
                venueCategoryId
        ).enqueue(new Callback<VenueResponse>() {
            @Override
            public void onResponse(Call<VenueResponse> call, Response<VenueResponse> response) {
                getMvpView().showLoadingDialog(R.string.title_loading_search_venue, R.string.content_loading_search_venue);
                VenueResponse venueResponse = response.body();
                if (venueResponse != null) {
                    ArrayList<Result> results = venueResponse.getResults();
                    if (results != null) {
                        //delay
                        getMvpView().openListVenueDialogFragment(results);
                    }
                }
            }

            @Override
            public void onFailure(Call<VenueResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClusterItemInfoWindowClick(String venueId) {
        getMvpView().openVenueDetailDialogFragment(venueId);
    }
}
