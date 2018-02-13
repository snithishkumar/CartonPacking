package com.ordered.report.SyncAdapter;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


/**
 * Created by Nithish on 11/02/18.
 */

public enum ServiceAPI {
    INSTANCE;

    private  final String SERVER_URL = "http://192.168.0.193:9090/server/";

    private SyncServiceApi syncServiceApi = null;

    ServiceAPI(){
        Executor executor = Executors.newCachedThreadPool();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(5, TimeUnit.MINUTES);
        httpClient.connectTimeout(5, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).client(okHttpClient).callbackExecutor(executor).build();
        syncServiceApi = retrofit.create(SyncServiceApi.class);
    }

    public SyncServiceApi getSyncServiceApi(){
        return syncServiceApi;
    }
}
