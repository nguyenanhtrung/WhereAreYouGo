package com.example.android.whereareyougo.ui.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.example.android.whereareyougo.ui.utils.NetworkUtil;


/**
 * Created by nguyenanhtrung on 06/08/2017.
 */

public class NetworkReceiver extends BroadcastReceiver {
    private Snackbar snackbar;


    public NetworkReceiver(Snackbar snackbar) {
        this.snackbar = snackbar;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (!NetworkUtil.isNetworkConnected(context)) {
            snackbar.show();
        }else{
            snackbar.dismiss();
        }
    }
}
