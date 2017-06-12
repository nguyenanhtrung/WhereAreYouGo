package com.example.android.whereareyougo.ui.data.database.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nguyenanhtrung on 12/06/2017.
 */


public class User {

  private String userID;
  private String name;
  private String email;
  private String password;


  private String phoneNumber;
  private String imageName;
  private String status;
  private String currentPosition;

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


  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(String currentPosition) {
    this.currentPosition = currentPosition;
  }

  public Map<String,Object> toMap(){
    HashMap<String,Object> result = new HashMap<>();
    result.put("userID",userID);
    result.put("name",name);
    result.put("phoneNumber",phoneNumber);
    result.put("email",email);
    result.put("password",password);
    result.put("imageName", imageName);

    return result;
  }
}