package com.example.android.whereareyougo.ui.data.remote;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class ApiHelper {
    public static final String PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    public static ApiService getAPIService(){
        return RetrofitClient.getClient(PLACES_BASE_URL).create(ApiService.class);
    }
}
