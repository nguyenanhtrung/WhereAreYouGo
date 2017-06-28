package com.example.android.whereareyougo.ui.data.database.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenanhtrung on 28/06/2017.
 */

public class VenuePhoto {
    @SerializedName("width")
    @Expose
    private int width;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("photo_reference")
    @Expose
    private String photoReference;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }
}
