package com.example.android.whereareyougo.ui.utils;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * Created by nguyenanhtrung on 16/08/2017.
 */

public class MyFragmentUtil {
    public static void replaceFragment(FragmentManager fragmentManager, int containerId, Fragment newFragment, String tag){
        fragmentManager.beginTransaction().replace(containerId,newFragment,tag).commit();
    }

    public static void addFragment(FragmentManager fragmentManager, int containerId, Fragment newFragment, String tag){
        fragmentManager.beginTransaction().add(containerId,newFragment,tag).commit();
    }
}
