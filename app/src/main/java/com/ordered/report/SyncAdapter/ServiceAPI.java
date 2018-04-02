package com.ordered.report.SyncAdapter;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Nithish on 11/02/18.
 */

public enum ServiceAPI {
    INSTANCE;

    private  final String SERVER_URL = "http://192.168.0.103:8182/globalimbx/";
//   private  final String SERVER_URL = "http://18.218.188.18:8080/global/";

    private SyncServiceApi syncServiceApi = null;

    ServiceAPI(){
        Executor executor = Executors.newCachedThreadPool();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(5, TimeUnit.MINUTES);
        httpClient.connectTimeout(5, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SERVER_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).callbackExecutor(executor).build();
        syncServiceApi = retrofit.create(SyncServiceApi.class);
    }

    public SyncServiceApi getSyncServiceApi(){
        return syncServiceApi;
    }
}
