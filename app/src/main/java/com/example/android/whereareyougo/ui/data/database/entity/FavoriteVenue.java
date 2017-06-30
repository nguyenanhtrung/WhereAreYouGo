package com.example.android.whereareyougo.ui.data.database.entity;

/**
 * Created by nguyenanhtrung on 29/06/2017.
 */

public class FavoriteVenue {
    private String name;
    private String venueCategoryIcon;
    private double lat;
    private double lng;
    private String venueId;

    public FavoriteVenue() {
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenueCategoryIcon() {
        return venueCategoryIcon;
    }

    public void setVenueCategoryIcon(String venueCategoryIcon) {
        this.venueCategoryIcon = venueCategoryIcon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
