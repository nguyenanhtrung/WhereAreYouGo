package com.example.android.whereareyougo.ui.data.remote;

import com.example.android.whereareyougo.ui.data.database.entity.VenueResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public interface ApiService {

    @GET("nearbysearch/json")
    Call<VenueResponse> getVenueByName(
            @Query("key") String apiKey,
            @Query("location") String location,
            @Query("radius") double radius,
            @Query("keyword") String keyword
    );

}
