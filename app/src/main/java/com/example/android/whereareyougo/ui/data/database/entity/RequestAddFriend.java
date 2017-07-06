package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nguyenanhtrung on 04/07/2017.
 */

public class RequestAddFriend implements Parcelable {
    private String senderId;
    private boolean isAccept = false;

    public RequestAddFriend(String senderId, boolean isAccept) {
        this.senderId = senderId;
        this.isAccept = isAccept;
    }

    public RequestAddFriend() {

    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.senderId);
        dest.writeByte(this.isAccept ? (byte) 1 : (byte) 0);
    }

    protected RequestAddFriend(Parcel in) {
        this.senderId = in.readString();
        this.isAccept = in.readByte() != 0;
    }

    public static final Creator<RequestAddFriend> CREATOR = new Creator<RequestAddFriend>() {
        @Override
        public RequestAddFriend createFromParcel(Parcel source) {
            return new RequestAddFriend(source);
        }

        @Override
        public RequestAddFriend[] newArray(int size) {
            return new RequestAddFriend[size];
        }
    };
}
