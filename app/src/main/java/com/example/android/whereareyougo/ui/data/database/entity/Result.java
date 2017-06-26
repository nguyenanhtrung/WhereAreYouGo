package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class Result implements Parcelable {
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("opening_hours")
    @Expose
    private OpeningHour openingHour;

    @SerializedName("place_id")
    @Expose
    private String placeId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHour getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(OpeningHour openingHour) {
        this.openingHour = openingHour;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.geometry, flags);
        dest.writeString(this.icon);
        dest.writeString(this.name);
        dest.writeParcelable(this.openingHour, flags);
        dest.writeString(this.placeId);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
        this.icon = in.readString();
        this.name = in.readString();
        this.openingHour = in.readParcelable(OpeningHour.class.getClassLoader());
        this.placeId = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
