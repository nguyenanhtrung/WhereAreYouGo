package com.example.android.whereareyougo.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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


    public static File PhotoCompressor(File photoFile) {
        Bitmap b = BitmapFactory.decodeFile(photoFile.getPath());


        int originalWidth = b.getWidth();
        int originalHeight = b.getHeight();
        int boundWidth = 60;
        int boundHeight = 60;
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        //check if the image needs to be scale width
        if (originalWidth > boundWidth) {
            //scale width to fit
            newWidth = boundWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        //now check if we need to scale even the new height
        if (newHeight > boundHeight) {
            //scale height to fit instead
            newHeight = boundHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }
        // Log.i(TAG, "Original Image:" + originalHeight + " x" + originalWidth);
        //Log.i(TAG, "New Image:" + newHeight + " x" + newWidth);
        try {
            Bitmap out = Bitmap.createScaledBitmap(b, newWidth, newHeight, true);
            FileOutputStream fOut;
            fOut = new FileOutputStream(photoFile);
            out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            b.recycle();
            out.recycle();
        } catch (OutOfMemoryError exception) {
            // Log.e(TAG, "OutofMemory excpetion" + exception);
            exception.printStackTrace();
        } catch (FileNotFoundException e) {
            // Log.e(TAG, "File not found excpetion" + e);
            e.printStackTrace();
        } catch (IOException e) {
            // Log.e(TAG, "IO exception excpetion" + e);
            e.printStackTrace();
        }
        return photoFile;
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

    public static Drawable getDrawable(int drawableId, Context context){
        return  ContextCompat.getDrawable(context,drawableId);
    }


}
