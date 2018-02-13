package com.ordered.report.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 1/1/2018.
 */

public class Utils {

    public static boolean isNetworkAvailable(ConnectivityManager cm) {
        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String convertMiliToDate(Date date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
            return simpleDateFormat.format(date);
    }
}
