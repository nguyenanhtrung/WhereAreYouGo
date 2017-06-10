package com.example.android.whereareyougo.ui.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.validator.EmailValidator;

/**
 * Created by nguyenanhtrung on 10/06/2017.
 */

public class Validations {

  public static boolean checkEmail(String email) {

    return EmailValidator.getInstance().isValid(email);
  }

  public static boolean checkFullName(String fullName) {
    String pattern = "^[\\p{L} .'-]+$";
    Pattern objPattern = Pattern.compile(pattern);
    Matcher matcher = objPattern.matcher(fullName);
    return matcher.matches();
  }

  public static boolean checkPassword(String password) {
    String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
    Pattern objPattern = Pattern.compile(pattern);
    Matcher matcher = objPattern.matcher(password);
    return matcher.matches();
  }

  public static boolean checkAddress(String address) {
    String pattern = "^[a-zA-Z0-9.,\\-\\s]+$";
    Pattern objPattern = Pattern.compile(pattern);
    Matcher matcher = objPattern.matcher(address);
    return matcher.matches();
  }

  public static boolean checkPhoneNumber(String phoneNumber) {
    //validate phone numbers of format "1234567890"
    if (phoneNumber.matches("\\d{11}")) {
      return true;
    }
    //validating phone number with -, . or spaces
    else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
      return true;
    }
    //validating phone number with extension length from 3 to 5
    else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
      return true;
    }
    //validating phone number where area code is in braces ()
    else //return false if nothing matches the input
      return phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}");
  }

}

