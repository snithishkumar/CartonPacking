package com.ordered.report.SyncAdapter;


import com.ordered.report.json.models.OrderDetailsJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by Admin on 1/1/2018.
 */

public interface SyncServiceApi {

    @GET(ServiceUrls.CHECK_AUTHENTICATION)
    Response checkAuthentication(@Field("userName") String userName, @Field("password") String password);
    @GET(ServiceUrls.DOWNLOAD_URL)
    Call<String> getDownloadedSyncItems(@Field("lastSyncTime") long lastSyncTime);

    @GET(ServiceUrls.DOWNLOAD_URL)
    Call<String> uploadData(@Body List<OrderDetailsJson> orderDetailsJsonList);

}
