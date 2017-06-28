package com.example.android.whereareyougo.ui.ui.map;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.Result;
import com.example.android.whereareyougo.ui.data.database.entity.VenueResponse;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.data.remote.ApiHelper;
import com.example.android.whereareyougo.ui.data.remote.RetrofitClient;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;

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
                        if (venueResponse != null){
                            ArrayList<Result> results = venueResponse.getResults();
                            if (results != null){
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
}
