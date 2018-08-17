package com.ordered.report.SyncAdapter;


import com.ordered.report.json.models.OrderDetailsJson;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.json.models.ServerSyncModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Admin on 1/1/2018.
 */

public interface SyncServiceApi {

    @GET(ServiceUrls.CHECK_AUTHENTICATION)
    Response checkAuthentication(@Field("userName") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST(ServiceUrls.GET_ORDER_LIST)
    Call<ResponseData> getDownloadedSyncItems(@Field("lastSyncTime") long lastSyncTime);

    @POST(ServiceUrls.UPDATE_ORDER_DETAILS)
    Call<String> uploadData(@Body ServerSyncModel serverSyncModel);

}
