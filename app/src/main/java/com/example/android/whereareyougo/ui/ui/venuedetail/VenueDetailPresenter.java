package com.example.android.whereareyougo.ui.ui.venuedetail;

import com.example.android.whereareyougo.ui.data.database.entity.VenueDetailResponse;
import com.example.android.whereareyougo.ui.data.database.entity.VenueDetailResult;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.data.remote.ApiHelper;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenueDetailPresenter<V extends VenueDetailView> extends BasePresenter<V> implements VenueDetailMvpPresenter<V> {

    @Inject
    public VenueDetailPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void showVenueDetailByVenueId(String venueId) {
        if (venueId == null || venueId.isEmpty()) {
            return;
        }

        ApiHelper.getAPIService().getVenueDetailByVenueId(MyKey.GOOGLE_PLACES_KEY, venueId)
                .enqueue(new Callback<VenueDetailResponse>() {
                    @Override
                    public void onResponse(Call<VenueDetailResponse> call, Response<VenueDetailResponse> response) {
                        VenueDetailResponse venueDetailResponse = response.body();
                        if (venueDetailResponse != null) {
                            VenueDetailResult result = venueDetailResponse.getVenueDetailResult();
                            showVenueDetail(result);

                        }
                    }

                    @Override
                    public void onFailure(Call<VenueDetailResponse> call, Throwable t) {

                    }
                });
    }

    private void showVenueDetail(VenueDetailResult venueDetailResult) {
        getMvpView().showVenueName(venueDetailResult.getName());
        getMvpView().showVenueAddress(venueDetailResult.getFormattedAddress());
        getMvpView().showVenuePhoneNumber(venueDetailResult.getFormattedPhoneNumber());
        if (venueDetailResult.getOpeningHour() != null) {
            getMvpView().showVenueStatus(venueDetailResult.getOpeningHour().isOpenNow());
        }
        getMvpView().showVenueRating(venueDetailResult.getRating());
        getMvpView().showVenuePriceLevel(venueDetailResult.getPriceLevel());
    }


}
