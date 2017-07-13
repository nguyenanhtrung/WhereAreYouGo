package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nguyenanhtrung on 13/07/2017.
 */

public class RequestFollow implements Parcelable {
    private String senderId;

    public RequestFollow(String senderId) {
        this.senderId = senderId;
    }

    public RequestFollow() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.senderId);
    }

    protected RequestFollow(Parcel in) {
        this.senderId = in.readString();
    }

    public static final Parcelable.Creator<RequestFollow> CREATOR = new Parcelable.Creator<RequestFollow>() {
        @Override
        public RequestFollow createFromParcel(Parcel source) {
            return new RequestFollow(source);
        }

        @Override
        public RequestFollow[] newArray(int size) {
            return new RequestFollow[size];
        }
    };
}
