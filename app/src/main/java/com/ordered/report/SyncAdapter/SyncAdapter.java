package com.ordered.report.SyncAdapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.ordered.report.dao.OrderDAO;
import com.ordered.report.eventBus.AppBus;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.OrderDetailsJson;
import com.ordered.report.json.models.ProcessedDetails;
import com.ordered.report.json.models.ProductDetailsJson;
import com.ordered.report.json.models.ResponseData;
import com.ordered.report.json.models.ServerSyncModel;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.ClientDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;

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
    private OrderDAO orderDAO = null;
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
            orderDAO = new OrderDAO(context);
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
                ServerSyncModel serverSyncModel = gson.fromJson(gson.toJson(orderDetailsData.getData()), ServerSyncModel.class);
                processDeliveryDetails(serverSyncModel.getDeliveryDetailsEntities());
                List<OrderDetailsJson> orderDetailsJsonsList = serverSyncModel.getOrderDetailsJsonList();
                for (OrderDetailsJson orderDetailsJson : orderDetailsJsonsList) {
                    try {
                        processOrderDetails(orderDetailsJson);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processOrderDetails(OrderDetailsJson orderDetailsJson,OrderEntity orderEntity){
        if(orderDetailsJson.getLastModifiedDate() >= orderEntity.getLastModifiedDate()){
            orderEntity.populateData(orderDetailsJson);
            orderEntity.setSync(true);
            orderEntity.setOrderStatus(orderDetailsJson.getOrderStatus());
            orderEntity.setPaymentStatus(orderDetailsJson.getPaymentStatus());
            orderEntity.setLastModifiedDate(orderDetailsJson.getLastModifiedDate());
            orderEntity.setCreatedBy(orderDetailsJson.getCreatedBy());
            orderEntity.setCartonCounts(orderDetailsJson.getCartonCounts());
            orderEntity.setOrderedItems(gson.toJson(orderDetailsJson.getOrderedItems()));
            orderDAO.updateCortonbookEntity(orderEntity);
        }
    }



    private void processOrderDetails(OrderDetailsJson orderDetailsJson) {
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderDetailsJson.getOrderGuid());
        if (orderEntity == null) {
            orderEntity = new OrderEntity(orderDetailsJson);
            orderEntity.setSync(true);
            orderEntity.setOrderedItems(gson.toJson(orderDetailsJson.getOrderedItems()));
            orderDAO.savCartonbookEntity(orderEntity);
            if (orderDetailsJson.getProductDetails() != null && orderDetailsJson.getProductDetails().size() > 0) {
                for (CartonDetailsJson cartonDetailsJson : orderDetailsJson.getProductDetails()) {
                    CartonDetailsEntity cartonDetailsEntity = orderDAO.getCartonDetailsEntity(cartonDetailsJson.getCartonGuid());
                    if (cartonDetailsEntity == null) {
                        cartonDetailsEntity = new CartonDetailsEntity(cartonDetailsJson);
                        cartonDetailsEntity.setOrderEntity(orderEntity);
                        if(cartonDetailsJson.getDeliverDetailsGuid() != null){
                            DeliveryDetailsEntity deliveryDetailsEntity =  orderDAO.getDeliveryDetailsEntity(cartonDetailsJson.getDeliverDetailsGuid());
                            cartonDetailsEntity.setDeliveryDetails(deliveryDetailsEntity);
                        }
                        orderDAO.createCartonDetailsEntity(cartonDetailsEntity);
                    }else{
                        if(cartonDetailsJson.getDeliverDetailsGuid() != null){
                            DeliveryDetailsEntity deliveryDetailsEntity =  orderDAO.getDeliveryDetailsEntity(cartonDetailsJson.getDeliverDetailsGuid());
                            cartonDetailsEntity.setDeliveryDetails(deliveryDetailsEntity);
                            orderDAO.updateCartonDetailsEntity(cartonDetailsEntity);
                        }
                    }

                    for (ProductDetailsJson productDetailsJson : cartonDetailsJson.getProductDetailsJsonList()) {
                        ProductDetailsEntity productDetailsEntity = orderDAO.getProductDetails(productDetailsJson.getProductGuid());
                        if (productDetailsEntity == null) {
                            productDetailsEntity = new ProductDetailsEntity(productDetailsJson);
                            productDetailsEntity.setCartonNumber(cartonDetailsEntity);
                            productDetailsEntity.setOrderEntity(orderEntity);
                            orderDAO.saveProductEntity(productDetailsEntity);
                        }

                    }

                }
            }

            processClientDetails(orderDetailsJson,orderEntity);
            ResponseData responseData = new ResponseData();
            AppBus.getInstance().post(responseData);
        }
    }


    private void processDeliveryDetails(List<DeliveryDetailsEntity> deliveryDetailsEntityList){
        for(DeliveryDetailsEntity deliveryDetailsEntity : deliveryDetailsEntityList){
            try{
                DeliveryDetailsEntity dbDeliveryDetailsEntity =   orderDAO.getDeliveryDetailsEntity(deliveryDetailsEntity.getDeliveryUUID());
                if(dbDeliveryDetailsEntity == null){
                    orderDAO.createDeliveryDetailsEntity(deliveryDetailsEntity);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }


    private void processClientDetails(OrderDetailsJson orderDetailsJson, OrderEntity orderEntity ){
        if(orderDetailsJson.getClientDetails() != null){
            ClientDetailsEntity clientDetailsEntity =  orderDetailsJson.getClientDetails();
            ClientDetailsEntity dbClientDetailsEntity =  orderDAO.getClientDetailsEntity(clientDetailsEntity.getClientDetailsUUID());
            if(dbClientDetailsEntity == null){
                clientDetailsEntity.setOrderEntity(orderEntity);
                orderDAO.createClientDetails(clientDetailsEntity);
            }
        }
    }


    private void uploadDataToServer() {
        try {
            List<OrderEntity> orderEntityList = orderDAO.getUnSyncedOrderDetails();
            List<DeliveryDetailsEntity> deliveryDetailsEntityList = orderDAO.getUnSyncedDeliveryDetailsEntity();
            if (orderEntityList.size() == 0 && deliveryDetailsEntityList.size() == 0) {
                return;
            }
            ServerSyncModel serverSyncModel = new ServerSyncModel();
            for (OrderEntity orderEntity : orderEntityList) {
                OrderDetailsJson orderDetailsJson = new OrderDetailsJson(orderEntity);
                orderDetailsJson.setOrderStatus(orderEntity.getOrderStatus());
                serverSyncModel.getOrderDetailsJsonList().add(orderDetailsJson);
                List<CartonDetailsEntity> cartonDetailsEntityList = orderDAO.getCartonDetailsList(orderEntity);
                for (CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList) {
                    CartonDetailsJson cartonDetailsJson = new CartonDetailsJson(cartonDetailsEntity);
                    orderDetailsJson.getProductDetails().add(cartonDetailsJson);
                    List<ProductDetailsEntity> productDetailsEntities = orderDAO.getProductDetailsEntityList(orderEntity, cartonDetailsEntity);
                    for (ProductDetailsEntity productDetailsEntity : productDetailsEntities) {
                        ProductDetailsJson productDetailsJson = new ProductDetailsJson(productDetailsEntity);
                        cartonDetailsJson.getProductDetailsJsonList().add(productDetailsJson);
                    }
                }

                ClientDetailsEntity clientDetailsEntity = orderDAO.getClientDetailsEntity(orderEntity);
                clientDetailsEntity.setOrderEntity(null);
                orderDetailsJson.setClientDetails(clientDetailsEntity);
            }


            for (DeliveryDetailsEntity deliveryDetailsEntity : deliveryDetailsEntityList) {
                serverSyncModel.getDeliveryDetailsEntities().add(deliveryDetailsEntity);
            }

            Call<String> orderSyncedDetails = syncServiceApi.uploadData(serverSyncModel);
            Response<String> response = orderSyncedDetails.execute();
            if (response != null && response.isSuccessful()) {
                String dataRespond = response.body();
                ServerSyncModel responseModel = gson.fromJson(dataRespond,ServerSyncModel.class);
                List<ProcessedDetails> orderGuids  = responseModel.getOrderGuids();
                int size = orderGuids.size();
                for (int i = 0; i < size; i++) {
                    try{
                        ProcessedDetails processedDetails = orderGuids.get(i);
                        orderDAO.updateSyncStatus(processedDetails.getUuid(),processedDetails.getDateTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                List<ProcessedDetails> deliveryGuids  = responseModel.getDeliveryGuids();
                for(ProcessedDetails processedDetails : deliveryGuids){
                    try{
                        orderDAO.updateDeliverySyncStatus(processedDetails.getUuid(),processedDetails.getDateTime());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}