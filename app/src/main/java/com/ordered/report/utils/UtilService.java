package com.ordered.report.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Admin on 2/14/2018.
 */

public class UtilService {

    public static long getCurrentTimeMilli() {
        return getCurrentDate().getTime();
    }
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * Returns Randomly Generated Number
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String reportFormat(long milliSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(milliSec);
    }

    public static String formatDateTime(long dateInMilliSecs) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM / dd / yyyy");
        return simpleDateFormat.format(dateInMilliSecs);
    }


    public static String getDeliveryId(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMM_HHmmss");
        return "DEL_"+sdf.format(System.currentTimeMillis());
    }

}
