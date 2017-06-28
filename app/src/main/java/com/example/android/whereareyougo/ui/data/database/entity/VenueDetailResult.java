package com.example.android.whereareyougo.ui.data.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenueDetailResult {
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;

    @SerializedName("formatted_phone_number")
    @Expose
    private String formattedPhoneNumber;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("opening_hours")
    @Expose
    private OpeningHour openingHour;

    @SerializedName("photos")
    @Expose
    private List<VenuePhoto> photos = null;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    @SerializedName("rating")
    @Expose
    private double rating = -1.0;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("price_level")
    @Expose
    private int priceLevel = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }

    public List<VenuePhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<VenuePhoto> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
