package com.example.android.whereareyougo.ui.data.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class VenueResponse {
    @SerializedName("results")
    @Expose
    private ArrayList<Result> results = null;
    @SerializedName("status")
    @Expose
    private String status;

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
