package com.example.android.whereareyougo.ui.ui.favoritevenues;

import android.util.Log;

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
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FavoriteVenue favoriteVenue = postSnapshot.getValue(FavoriteVenue.class);
                    favoriteVenues.add(favoriteVenue);

                    Log.i("listfavoritevenue","VenueName = " + favoriteVenue.getName());
                }
                getMvpView().setupFavoriteVenuesRecyclerViewAdapter(favoriteVenues);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
