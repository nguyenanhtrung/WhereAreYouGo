package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nguyenanhtrung on 26/06/2017.
 */

public class OpeningHour implements Parcelable {
    @SerializedName("open_now")
    @Expose
    private boolean openNow;

    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText = null;

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.openNow ? (byte) 1 : (byte) 0);
    }

    public OpeningHour() {
    }

    protected OpeningHour(Parcel in) {
        this.openNow = in.readByte() != 0;
    }

    public static final Parcelable.Creator<OpeningHour> CREATOR = new Parcelable.Creator<OpeningHour>() {
        @Override
        public OpeningHour createFromParcel(Parcel source) {
            return new OpeningHour(source);
        }

        @Override
        public OpeningHour[] newArray(int size) {
            return new OpeningHour[size];
        }
    };
}
