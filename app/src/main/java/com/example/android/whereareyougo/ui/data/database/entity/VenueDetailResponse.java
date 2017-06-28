package com.example.android.whereareyougo.ui.data.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenueDetailResponse {
    @SerializedName("result")
    @Expose
    private VenueDetailResult venueDetailResult;

    @SerializedName("status")
    @Expose
    private String status;

    public VenueDetailResult getVenueDetailResult() {
        return venueDetailResult;
    }

    public void setVenueDetailResult(VenueDetailResult venueDetailResult) {
        this.venueDetailResult = venueDetailResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
