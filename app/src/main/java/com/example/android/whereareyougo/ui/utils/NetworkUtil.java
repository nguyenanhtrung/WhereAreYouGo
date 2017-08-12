package com.example.android.whereareyougo.ui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.android.whereareyougo.R;

/**
 * Created by nguyenanhtrung on 09/06/2017.
 */

public class NetworkUtil {
  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

    if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI){
      return true;
    }else if(activeNetwork != null){
      return true;
    }else{
      return false;
    }
  }

  public static void showDisconnectNetworkDialog(Context context){
    new MaterialDialog.Builder(context)
            .title(R.string.title_disconnect_network_dialog)
            .titleColorRes(R.color.colorAccent)
            .content(R.string.content_disconnect_network_dialog)
            .contentColorRes(R.color.colorSecondaryText)
            .negativeText(R.string.text_connect_again)
            .build().show();
  }
}
