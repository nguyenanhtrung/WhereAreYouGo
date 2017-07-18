package com.example.android.whereareyougo.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.example.android.whereareyougo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nguyenanhtrung on 20/06/2017.
 */

public class Commons {
  public static Uri convertStringToUri(String content){
    return Uri.parse(content);
  }

  public static String convertUriToString(Uri uri){
    return uri.toString();
  }

  public static String getCurrentDate(){
    SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
    String time = dateFormat.format(Calendar.getInstance().getTime());

    return time;
  }


}
