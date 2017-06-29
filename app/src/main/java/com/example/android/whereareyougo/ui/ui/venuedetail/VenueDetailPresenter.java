package com.example.android.whereareyougo.ui.ui.venuedetail;

import android.support.design.widget.Snackbar;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.database.entity.VenueDetailResponse;
import com.example.android.whereareyougo.ui.data.database.entity.VenueDetailResult;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.data.remote.ApiHelper;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenueDetailPresenter<V extends VenueDetailView> extends BasePresenter<V> implements VenueDetailMvpPresenter<V> {
    private FavoriteVenue favoriteVenue;
    private VenueDetailResult venueDetailResult;

    public FavoriteVenue getFavoriteVenue() {
        return favoriteVenue;
    }

    public void setFavoriteVenue(FavoriteVenue favoriteVenue) {
        this.favoriteVenue = favoriteVenue;
    }

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
                            getMvpView().setDataForRecyclerViewVenuePhotos(result.getPhotos());
                            getMvpView().setupRecyclerViewVenuePhotos();
                            showVenueDetail(result);
                            setVenueDetailResult(result);
                            saveFavoriteVenue(result);

                        }
                    }

                    @Override
                    public void onFailure(Call<VenueDetailResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onClickImageButtonCallPhone() {
        getMvpView().checkCallPhonePermissions();
        //
        getMvpView().callPhone();

    }

    public void setVenueDetailResult(VenueDetailResult venueDetailResult) {
        this.venueDetailResult = venueDetailResult;
    }

    @Override
    public void onClickButtonCloseDialog() {
        getMvpView().dismissDialog();
    }



    @Override
    public void onClickButtonSaveVenue() {
        if (favoriteVenue != null){
            if (isVenueAlreadySave(favoriteVenue.getName())){
                //show venue already save
                getMvpView().showMessage(R.string.venue_already_save);
                return;
            }

            getDataManager().saveFavoriteVenue(favoriteVenue);
            getDataManager().saveFavoriteVenueId(favoriteVenue.getName(),favoriteVenue.getVenueId());
            //Show message save venue success(de sau)
            getMvpView().showMessage(R.string.venue_saved);
        }
    }

    private boolean isVenueAlreadySave(String key){
        String venueId = getDataManager().getFavoriteVenueId(key);

        if (venueId != null && !venueId.isEmpty()){
            return true;
        }

        return false;
    }

    @Override
    public void onClickButtonFindWay() {
        LatLng venueLatLng = new LatLng(venueDetailResult.getGeometry().getLocation().getLat(),
                venueDetailResult.getGeometry().getLocation().getLng());

        getMvpView().dismissDialog();
        getMvpView().drawPolyLineOnMap(venueLatLng);
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


    private void saveFavoriteVenue(VenueDetailResult venueDetailResult){
        favoriteVenue = new FavoriteVenue();

        favoriteVenue.setName(venueDetailResult.getName());
        favoriteVenue.setVenueId(venueDetailResult.getPlaceId());
        favoriteVenue.setVenueCategoryIcon(venueDetailResult.getIcon());
        favoriteVenue.setLat(venueDetailResult.getGeometry().getLocation().getLat());
        favoriteVenue.setLng(venueDetailResult.getGeometry().getLocation().getLng());

    }

}
