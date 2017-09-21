package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nguyenanhtrung on 21/09/2017.
 */

public class VenueSearchCondition implements Parcelable {
    private String categoryId;
    private double radius;

    public VenueSearchCondition(String categoryId, double radius) {
        this.categoryId = categoryId;
        this.radius = radius;
    }

    public VenueSearchCondition() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryId);
        dest.writeDouble(this.radius);
    }

    protected VenueSearchCondition(Parcel in) {
        this.categoryId = in.readString();
        this.radius = in.readDouble();
    }

    public static final Parcelable.Creator<VenueSearchCondition> CREATOR = new Parcelable.Creator<VenueSearchCondition>() {
        @Override
        public VenueSearchCondition createFromParcel(Parcel source) {
            return new VenueSearchCondition(source);
        }

        @Override
        public VenueSearchCondition[] newArray(int size) {
            return new VenueSearchCondition[size];
        }
    };
}
