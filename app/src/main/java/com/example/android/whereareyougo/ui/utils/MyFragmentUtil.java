package com.example.android.whereareyougo.ui.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

/**
 * Created by nguyenanhtrung on 16/08/2017.
 */

public class MyFragmentUtil {
    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment newFragment, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId,newFragment,tag).addToBackStack(null).commit();



    }

    public static void addFragment(FragmentManager fragmentManager, int containerId, Fragment newFragment, String tag){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerId,newFragment,tag).addToBackStack(null).commit();

    }


}
