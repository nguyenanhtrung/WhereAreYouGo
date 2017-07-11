package com.example.android.whereareyougo.ui.ui.favoritevenues;

import android.util.Log;

import com.example.android.whereareyougo.R;
import com.example.android.whereareyougo.ui.data.database.entity.FavoriteVenue;
import com.example.android.whereareyougo.ui.data.manager.DataManager;
import com.example.android.whereareyougo.ui.ui.base.BasePresenter;
import com.example.android.whereareyougo.ui.utils.MyKey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by nguyenanhtrung on 30/06/2017.
 */

public class ListFavoriteVenuePresenter<V extends ListFavoriteVenueView> extends BasePresenter<V> implements ListFavoriteVenueMvpPresenter<V> {

    @Inject
    public ListFavoriteVenuePresenter(DataManager dataManager) {
        super(dataManager);
    }


    public void getFavoriteVenues() {
        DatabaseReference favoriteVenuesRef = getDataManager().getFavoriteVenuesRef();


        favoriteVenuesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FavoriteVenue> favoriteVenues = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FavoriteVenue favoriteVenue = postSnapshot.getValue(FavoriteVenue.class);
                    favoriteVenues.add(favoriteVenue);

                    Log.i("listfavoritevenue", "VenueName = " + favoriteVenue.getName());
                }
                getMvpView().setupFavoriteVenuesRecyclerViewAdapter(favoriteVenues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onClickButtonDeleteVenue(FavoriteVenue venue, int position) {

        //show Dialog ask user if delete venue
        getMvpView().showDeleleVenueDialog(venue, position, R.string.content_delete_venue_dialog, MyKey.DELETE_ONE);


    }

    public void onClickAgreeDeleteDialog(FavoriteVenue venue, int position) {
        //delete venue in recyclerview
        getMvpView().removeVenueInRecyclerView(position);
        //delele venue in share prefefrences
        getDataManager().removeFavoriteVenueId(venue.getName());
        //delete venue in firebase database
        getDataManager().removeFavoriteVenueById(venue.getVenueId());
    }

    @Override
    public void onClickButtonDeleteAllVenue(List<FavoriteVenue> favoriteVenues) {
        if (favoriteVenues.isEmpty()){
            //show message list venue is empty
            return;
        }
        getMvpView().showDeleteAllVenueDialog(favoriteVenues);
    }

    public void onClickAgreeDeleteAll(List<FavoriteVenue> favoriteVenues){
        //delete all favoriteVenues in share preferences
        getDataManager().deleteAllFavoriteVenueIdOnPreRef(favoriteVenues);

        //delete all favoriteVenues in recyclerview
        getMvpView().removeAllFavoriteVenuesRecyclerView();

        //delete all favoriteVenues in firebase database
        getDataManager().deleteAllUserFavoriteVenues();
    }
}
