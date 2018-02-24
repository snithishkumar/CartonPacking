package com.ordered.report.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Admin on 1/1/2018.
 */

public class OrderedService {
    private Context context = null;
    private SyncServiceApi syncServiceApi = null;
    public final String LOG_TAG = LoginService.class.getSimpleName();
    private CartonbookDao cartonbookDao = null;
    private LoginEvent loginEvent = null;
    public static final String AUTHORITY = "com.ordered.report.SyncAdapter";
    private Gson gson;

    public OrderedService(Context context) {
        try {
            this.context = context;
            cartonbookDao = new CartonbookDao(context);
            gson = new Gson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderEntity> getCartonBookEntityList(String userName) {
        try {
            return cartonbookDao.getAllCartonbokEntityList(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public OrderEntity getOrderEntityByGuid(String orderGuid) {
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
        return orderEntity;
    }

    public void createProductEntity(ProductDetailsEntity productDetailsEntity) {
        try {
            cartonbookDao.saveProductEntity(productDetailsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductDetailsEntity> getProductEntityList(OrderEntity orderEntity) {
        List<ProductDetailsEntity> productEntities = cartonbookDao.getProductEntityList(orderEntity);
        return productEntities;
    }


    public List<OrderEntity> getOrdersList(){
        return cartonbookDao.getOrders();
    }


    public List<OrderEntity> getPackingOrdersList(){
        return cartonbookDao.getPackingOrders();
    }

    public List<OrderEntity> getCartonBookEntityByType(OrderStatus orderType) {
        if (orderType.toString().equals(OrderStatus.ORDERED.toString())) {
            return cartonbookDao.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderStatus.PACKING.toString())) {
            return cartonbookDao.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderType.DELIVERED.toString())) {
            return cartonbookDao.getCartonBookByOrderType(orderType);
        }
        return new ArrayList<>();
    }





    public Map<String,List> getOrderItems(String orderGuid){
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
       String orderedItems = orderEntity.getOrderedItems();
        Type listType = new TypeToken<ArrayList<OrderCreationDetailsJson>>() {
        }.getType();
        ArrayList<OrderCreationDetailsJson>  orderDetailsJsonArrayList =  gson.fromJson(orderedItems,listType);
        List<String> productName = new ArrayList<>();
        List<String> productGroup = new ArrayList<>();
        for(OrderCreationDetailsJson orderCreationDetailsJson : orderDetailsJsonArrayList){
            productName.add(orderCreationDetailsJson.getProductStyle());
            productGroup.add(orderCreationDetailsJson.getProductGroup());
        }
        Map<String,List> orderDetails = new HashMap<>();
        orderDetails.put("productName",productName);
        orderDetails.put("productGroup",productGroup);
        return orderDetails;
    }


    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels(String orderGuid) {
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
        String orderedItems = orderEntity.getOrderedItems();
        Type listType = new TypeToken<ArrayList<OrderCreationDetailsJson>>() {
        }.getType();
        List<OrderCreationDetailsJson> orderDetailsJsonArrayList = gson.fromJson(orderedItems, listType);
        List<OrderDetailsListViewModel> orderDetailsListViewModels = new ArrayList<>();
        for (OrderCreationDetailsJson orderCreationDetailsJson : orderDetailsJsonArrayList) {
            OrderDetailsListViewModel orderDetailsListViewModel = new OrderDetailsListViewModel();
            orderDetailsListViewModel.loadOrderItemsDetails(orderCreationDetailsJson);
            orderDetailsListViewModels.add(orderDetailsListViewModel);
        }
        return orderDetailsListViewModels;
    }


    public boolean saveProductDetails(String orderGuid,List<OrderDetailsListViewModel> orderDetailsListViewModels,String totalNoOfCartons){
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
        boolean isEdited = false;
        Map<String,CartonDetailsEntity> cartonDetailsEntityMap = new HashMap<>();
        for(OrderDetailsListViewModel orderDetailsListViewModel : orderDetailsListViewModels){
           if(orderDetailsListViewModel.isEdited()){
               String cartonNumber =  orderDetailsListViewModel.getCartonNumber();
               CartonDetailsEntity cartonDetailsEntity =  cartonDetailsEntityMap.get(cartonNumber);
               if(cartonDetailsEntity == null){
                   cartonDetailsEntity = new CartonDetailsEntity();
                   cartonDetailsEntity.setOrderEntity(orderEntity);
                   cartonDetailsEntity.setCartonGuid(UUID.randomUUID().toString());
                   cartonDetailsEntity.setCartonNumber(cartonNumber);
                   cartonDetailsEntity.setCreatedDateTime(System.currentTimeMillis());
                   cartonbookDao.createCartonDetailsEntity(cartonDetailsEntity);
                   cartonDetailsEntityMap.put(cartonNumber,cartonDetailsEntity);
               }
               ProductDetailsEntity productDetailsEntity = new ProductDetailsEntity(orderDetailsListViewModel);
               productDetailsEntity.setCartonNumber(cartonDetailsEntity);
               productDetailsEntity.setOrderEntity(orderEntity);
               cartonbookDao.createProductDetailsEntity(productDetailsEntity);
               isEdited = true;
           }


        }
        if(isEdited){
            orderEntity.setSync(false);
            orderEntity.setCartonCounts(totalNoOfCartons);
            orderEntity.setLastModifiedDate(System.currentTimeMillis());
            orderEntity.setOrderStatus(OrderStatus.PACKING);
            cartonbookDao.updateCortonbookEntity(orderEntity);
        }
        return isEdited;

    }


    public void updateOrderUpdates(OrderEntity orderEntity){
        cartonbookDao.updateCortonbookEntity(orderEntity);
    }
}
