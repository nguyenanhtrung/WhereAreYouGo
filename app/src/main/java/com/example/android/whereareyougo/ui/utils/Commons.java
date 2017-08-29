package com.example.android.whereareyougo.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.whereareyougo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.ValidatorResult;
import org.apache.commons.validator.util.ValidatorUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nguyenanhtrung on 20/06/2017.
 */

public class Commons {
    public static Uri convertStringToUri(String content) {
        return Uri.parse(content);
    }

    public static String convertUriToString(Uri uri) {
        return uri.toString();
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a", Locale.getDefault());
        String time = dateFormat.format(Calendar.getInstance().getTime());

        return time;
    }

    public static String convertLocationToString(Location location){
        if (location != null){
            return String.valueOf(location.getLatitude()) +
                    "," +
                    location.getLongitude();
        }
        return null;
    }

    public static LatLng convertStringToLocation(String location){
        if (location != null && !location.isEmpty()){
            String[] latlog = location.split(",");
            LatLng newLatLng = new LatLng(Double.parseDouble(latlog[0]),Double.parseDouble(latlog[1]));
            return newLatLng;
        }
        return null;
    }

    public static Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap, int drawableId) {
        CircleImageView mMarkerImageView = (CircleImageView) view.findViewById(R.id.image_user);
        if (drawableId == MyKey.NO_DRAWABLE) {
            mMarkerImageView.setImageBitmap(bitmap);
        } else {
            mMarkerImageView.setImageResource(drawableId);
        }

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public static Drawable getDrawable(int drawableId, Context context) {

        return ContextCompat.getDrawable(context, drawableId);
    }

    public static long convertMinuteToMillis(long minute) {
        return TimeUnit.MINUTES.toMillis(minute);
    }

    public static long convertMillisToMinute(long millis) {
        return TimeUnit.MILLISECONDS.toMinutes(millis);
    }

    public static boolean checkIsNumber(String value){
        return NumberUtils.isDigits(value);
    }




}
