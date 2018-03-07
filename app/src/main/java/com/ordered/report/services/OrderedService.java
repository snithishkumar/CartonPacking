package com.ordered.report.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.json.models.ProductDetailsJson;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;
import com.ordered.report.utils.Constants;
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


    public List<OrderEntity> getDeliveredOrdersList(){
        return cartonbookDao.getDeliveredOrders();
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


    public List<CartonDetailsJson> getCartonDetailsJson(String orderGuid){
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
       String orderItems =  orderEntity.getOrderedItems();
        List<OrderCreationDetailsJson>  orderCreationDetailsJsons =  getOrderedItems(orderItems);
        List<CartonDetailsEntity> cartonDetailsEntityList =  cartonbookDao.getCartonDetailsList(orderEntity);
        List<CartonDetailsJson> cartonDetailsJsonList = new ArrayList<>();
        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList){
            CartonDetailsJson cartonDetailsJson = new CartonDetailsJson(cartonDetailsEntity);
            List<ProductDetailsEntity> productDetailsEntities =  cartonbookDao.getProductDetailsEntityList(orderEntity,cartonDetailsEntity);
            for(ProductDetailsEntity productDetailsEntity : productDetailsEntities){
                OrderDetailsListViewModel  orderDetailsListViewModel = new OrderDetailsListViewModel();


                OrderCreationDetailsJson orderCreationDetailsJsonTemp = new OrderCreationDetailsJson();
                orderCreationDetailsJsonTemp.setOrderItemGuid(productDetailsEntity.getOrderItemGuid());
               int pos = orderCreationDetailsJsons.indexOf(orderCreationDetailsJsonTemp);
               if(pos != -1){
                   OrderCreationDetailsJson orderCreationDetailsJson =  orderCreationDetailsJsons.get(pos);
                   orderDetailsListViewModel.loadOrderItemsDetails(orderCreationDetailsJson);
               }
                orderDetailsListViewModel.setCartonNumber(cartonDetailsEntity.getCartonNumber());
                orderDetailsListViewModel.loadOrderItemsDetails(productDetailsEntity);
                cartonDetailsJson.getOrderDetailsListViewModels().add(orderDetailsListViewModel);
            }

            cartonDetailsJsonList.add(cartonDetailsJson);
        }
        return cartonDetailsJsonList;
    }


    private List<OrderCreationDetailsJson> getOrderedItems(String orderedItems){
        Type listType = new TypeToken<ArrayList<OrderCreationDetailsJson>>() {
        }.getType();
       return  gson.fromJson(orderedItems, listType);
    }


    public boolean saveProductDetails(String orderGuid,List<CartonDetailsJson> cartonDetailsJsonList,String view){
        OrderEntity orderEntity = cartonbookDao.getCartonBookEntityByGuid(orderGuid);
        boolean isEdited = false;
       for(CartonDetailsJson cartonDetailsJson : cartonDetailsJsonList){
           CartonDetailsEntity cartonDetailsEntity =  cartonbookDao.getCartonDetailsEntity(cartonDetailsJson.getCartonGuid());
           if(cartonDetailsEntity == null){
               cartonDetailsEntity = new CartonDetailsEntity(cartonDetailsJson);
               cartonDetailsEntity.setOrderEntity(orderEntity);
               cartonbookDao.createCartonDetailsEntity(cartonDetailsEntity);
           }else{
               cartonDetailsEntity.setLastModifiedBy(cartonDetailsJson.getLastModifiedBy());
               cartonDetailsEntity.setLastModifiedTime(cartonDetailsJson.getLastModifiedTime());
           }
           List<OrderDetailsListViewModel> orderDetailsListViewModels = cartonDetailsJson.getOrderDetailsListViewModels();
           for(OrderDetailsListViewModel orderDetailsListViewModel : orderDetailsListViewModels){
               isEdited = true;
               ProductDetailsEntity productDetailsEntity =  cartonbookDao.getProductDetails(orderDetailsListViewModel.getProductGuid());
               if(productDetailsEntity == null){
                   productDetailsEntity = new ProductDetailsEntity(orderDetailsListViewModel);
                   productDetailsEntity.setCartonNumber(cartonDetailsEntity);
                   productDetailsEntity.setOrderEntity(orderEntity);
                   productDetailsEntity.setCreatedBy(Constants.getLoginUser());
                   productDetailsEntity.setModifiedBy(Constants.getLoginUser());
                   productDetailsEntity.setLastModifiedDateTime(productDetailsEntity.getCreatedDateTime());
                   cartonbookDao.createProductDetailsEntity(productDetailsEntity);
               }
           }

       }


        if(isEdited){
            orderEntity.setSync(false);
            orderEntity.setCartonCounts(cartonDetailsJsonList.size()+"");
            orderEntity.setLastModifiedDate(System.currentTimeMillis());
            orderEntity.setOrderStatus(OrderStatus.PACKING);

            cartonbookDao.updateCortonbookEntity(orderEntity);
        }
        return isEdited;

    }


    public void createDelivery(DeliveryDetailsEntity deliveryDetailsEntity){
        cartonbookDao.createDeliveryDetailsEntity(deliveryDetailsEntity);
    }


    public void updateOrderUpdates(OrderEntity orderEntity){
        cartonbookDao.updateCortonbookEntity(orderEntity);
    }
}
