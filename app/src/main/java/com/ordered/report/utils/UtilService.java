package com.ordered.report.utils;

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


}
