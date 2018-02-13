package com.ordered.report.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Admin on 1/5/2018.
 */

public class DeviceConfig {
    public static String getAppRootPath(Context context) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            String path = Environment.getExternalStoragePublicDirectory("/").getAbsolutePath();
            File file = new File(path + File.separator + "Carton");
            if (!file.exists()) {
                file.mkdirs();
            }
            //create Pictures folder for store image
            File imgFile = new File(path + File.separator + "Pictures");
            if (!file.exists()) {
                imgFile.mkdirs();
            }
            return file.getPath();
        } else {
            return context.getDir("Carton", Context.MODE_PRIVATE).getAbsolutePath();
        }
}
}
