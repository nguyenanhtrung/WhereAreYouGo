package com.example.android.whereareyougo.ui.data.database.entity;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguyenanhtrung on 12/06/2017.
 */


public class User implements android.os.Parcelable {

  private String userID;
  private String name;
  private String email;
  private String password;


  private String phoneNumber;
  private String imageUrl;
  private String status;
  private String currentLocation;

  public User(String name, String email, String password) {
    this.name = name;
    this.email = email;
    this.password = password;
  }

  public User() {

  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(String currentLocation) {
    this.currentLocation = currentLocation;
  }

  public Map<String,Object> toMap(){
    HashMap<String,Object> result = new HashMap<>();
    result.put("userID",userID);
    result.put("name",name);
    result.put("phoneNumber",phoneNumber);
    result.put("email",email);
    result.put("password",password);
    result.put("imageUrl", imageUrl);

    return result;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.userID);
    dest.writeString(this.name);
    dest.writeString(this.email);
    dest.writeString(this.password);
    dest.writeString(this.phoneNumber);
    dest.writeString(this.imageUrl);
    dest.writeString(this.status);
    dest.writeString(this.currentLocation);
  }

  protected User(Parcel in) {
    this.userID = in.readString();
    this.name = in.readString();
    this.email = in.readString();
    this.password = in.readString();
    this.phoneNumber = in.readString();
    this.imageUrl = in.readString();
    this.status = in.readString();
    this.currentLocation = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override
    public User createFromParcel(Parcel source) {
      return new User(source);
    }

    @Override
    public User[] newArray(int size) {
      return new User[size];
    }
  };
}
