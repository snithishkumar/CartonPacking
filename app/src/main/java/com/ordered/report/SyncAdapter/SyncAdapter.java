package com.ordered.report.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.OrderDetailsJson;
import com.ordered.report.json.models.ProductDetailsJson;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


/**
 * Created by selva on 6/21/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = SyncAdapter.class.getSimpleName();
    private Context context;
    private Gson gson = null;
    private SyncServiceApi syncServiceApi;
    private CartonbookDao cartonbookDao = null;
    private JsonParser jsonParser = null;

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize, false);
        this.context = context;

        init(context);
    }

    /**
     * Initialize DAO's and Gson
     *
     * @param context
     */
    private void init(Context context) {
        try {
            cartonbookDao = new CartonbookDao(context);
            gson = new Gson();
            syncServiceApi = ServiceAPI.INSTANCE.getSyncServiceApi();
            jsonParser = new JsonParser();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error in AirOpsSyncAdapter", e);
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String authority, ContentProviderClient provider, SyncResult syncResult) {
        System.out.println("sync called");
        Log.e("sync adapter:", "sync called");
        try {
            downloadDataFromServer();
            uploadDataToServer();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error in onPerformSync", e);
        }

    }


    private void downloadDataFromServer() {
        try {
            Call<ResponseData> orderDetailsCall = syncServiceApi.getDownloadedSyncItems(-1);
            Response<ResponseData> orderDetailsResponse = orderDetailsCall.execute();
            if (orderDetailsResponse != null && orderDetailsResponse.isSuccessful()) {
                ResponseData orderDetailsData = orderDetailsResponse.body();
                Type listType = new TypeToken<ArrayList<OrderDetailsJson>>() {
                }.getType();

                List<OrderDetailsJson> orderDetailsJsonsList = gson.fromJson(gson.toJson(orderDetailsData.getData()), listType);
                for (OrderDetailsJson orderDetailsJson : orderDetailsJsonsList) {
                    processOrderDetails(orderDetailsJson);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processOrderDetails(OrderDetailsJson orderDetailsJson) {
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderDetailsJson.getOrderGuid());
        if (orderEntity == null) {
            orderEntity = new OrderEntity(orderDetailsJson);
            orderEntity.setSync(true);
            orderEntity.setOrderedItems(gson.toJson(orderDetailsJson.getOrderedItems()));
            cartonbookDao.savCartonbookEntity(orderEntity);
            if (orderDetailsJson.getProductDetails() != null && orderDetailsJson.getProductDetails().size() > 0) {
                for (CartonDetailsJson cartonDetailsJson : orderDetailsJson.getProductDetails()) {

                    CartonDetailsEntity cartonDetailsEntity = cartonbookDao.getCartonDetailsEntity(cartonDetailsJson.getCartonGuid());
                    if (cartonDetailsEntity == null) {
                        cartonDetailsEntity = new CartonDetailsEntity(cartonDetailsJson);
                        cartonDetailsEntity.setOrderEntity(orderEntity);
                        cartonbookDao.createCartonDetailsEntity(cartonDetailsEntity);
                    }

                    for (ProductDetailsJson productDetailsJson : cartonDetailsJson.getProductDetailsJsonList()) {
                        ProductDetailsEntity productDetailsEntity = cartonbookDao.getProductDetails(productDetailsJson.getProductGuid());
                        if (productDetailsEntity == null) {
                            productDetailsEntity = new ProductDetailsEntity(productDetailsJson);
                            productDetailsEntity.setCartonNumber(cartonDetailsEntity);
                            productDetailsEntity.setOrderEntity(orderEntity);
                            cartonbookDao.saveProductEntity(productDetailsEntity);
                        }

                    }

                }
            }
            ResponseData responseData = new ResponseData();
            AppBus.getInstance().post(responseData);
        }
    }


    private void uploadDataToServer() {
        try {
            List<OrderEntity> orderEntityList = cartonbookDao.getUnSyncedOrderDetails();
            List<OrderDetailsJson> orderDetailsJsonList = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntityList) {
                OrderDetailsJson orderDetailsJson = new OrderDetailsJson(orderEntity);
                orderDetailsJson.setOrderStatus(orderEntity.getOrderStatus());
                orderDetailsJsonList.add(orderDetailsJson);
                List<CartonDetailsEntity> cartonDetailsEntityList = cartonbookDao.getCartonDetailsList(orderEntity);
                for (CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList) {
                    CartonDetailsJson cartonDetailsJson = new CartonDetailsJson(cartonDetailsEntity);
                    orderDetailsJson.getProductDetails().add(cartonDetailsJson);
                    List<ProductDetailsEntity> productDetailsEntities = cartonbookDao.getProductDetailsEntityList(orderEntity, cartonDetailsEntity);
                    for (ProductDetailsEntity productDetailsEntity : productDetailsEntities) {
                        ProductDetailsJson productDetailsJson = new ProductDetailsJson(productDetailsEntity);
                        cartonDetailsJson.getProductDetailsJsonList().add(productDetailsJson);
                    }
                }


            }
            if (orderDetailsJsonList.size() > 0) {
                Call<String> orderSyncedDetails = syncServiceApi.uploadData(orderDetailsJsonList);
                Response<String> response = orderSyncedDetails.execute();
                if (response != null && response.isSuccessful()) {
                    String dataRespond = response.body();
                    JsonArray orderGuids = (JsonArray) jsonParser.parse(dataRespond);
                    int size = orderGuids.size();
                    for (int i = 0; i < size; i++) {
                        String orderGuid = orderGuids.get(i).getAsString();
                        cartonbookDao.updateSyncStatus(orderGuid);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}