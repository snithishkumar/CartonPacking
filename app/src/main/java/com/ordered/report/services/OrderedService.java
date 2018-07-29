package com.ordered.report.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.enumeration.Status;
import com.ordered.report.json.models.CartonDetailsJson;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.json.models.OrderCreationDetailsJson;
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
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(orderGuid);
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


    public List<OrderEntity> getDeliveryOrdersList(){
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderEntity> orderEntityList = cartonbookDao.getPackingOrders();
        for(OrderEntity orderEntity : orderEntityList){
            List<CartonDetailsEntity> detailsEntities =  cartonbookDao.getUnDeliveredCartonDetailsList(orderEntity);
            if(detailsEntities.size() > 0){
                orderList.add(orderEntity);
            }
        }
        return orderList;
    }


    public List<DeliveryDetailsEntity> getDeliveredOrdersList(){
        return cartonbookDao.getDeliveryDetailsEntity();
    }


    public List<CartonDetailsEntity> getCartonDetailsEntities(int deliveryId){
        DeliveryDetailsEntity deliveryDetailsEntity = cartonbookDao.getDeliveryDetailsEntity(deliveryId);
        return  cartonbookDao.getCartonDetailsList(deliveryDetailsEntity);
    }

    public DeliveryDetailsEntity getDeliveryDetailsEntity(int deliveryId){
        DeliveryDetailsEntity deliveryDetailsEntity = cartonbookDao.getDeliveryDetailsEntity(deliveryId);
        return deliveryDetailsEntity;
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
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(orderGuid);
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


    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels(String cartonbookGuid) {
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(cartonbookGuid);
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


    public List<CartonDetailsJson> getCartonDetailsJson(String cartonbookGuid){
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(cartonbookGuid);
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


    public boolean saveProductDetails(String orderGuid,List<CartonDetailsJson> cartonDetailsJsonList,OrderStatus orderStatus){
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(orderGuid);
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
               cartonDetailsEntity.setTotalWeight(cartonDetailsJson.getTotalWeight());
               cartonbookDao.updateCartonDetailsEntity(cartonDetailsEntity);
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

    public void calcAvailableCount(List<OrderDetailsListViewModel> orderDetailsListViewModelList,List<CartonDetailsJson> cartonDetailsJsonsList){
        for(OrderDetailsListViewModel orderDetailsListViewModel : orderDetailsListViewModelList){
            calcAvailableCount(orderDetailsListViewModel,cartonDetailsJsonsList);
        }
    }

    public boolean checkAvailability(List<CartonDetailsJson> cartonDetailsJsonsList,String orderGuid){
        OrderEntity orderEntity = cartonbookDao.getOrderEntityByGuid(orderGuid);
        String orderItems = orderEntity.getOrderedItems();
        List<OrderCreationDetailsJson> creationDetailsJsonList =  getOrderedItems(orderItems);
       // List<ProductDetailsEntity> productDetailsEntityList = cartonbookDao.getProductEntityList(orderEntity);
        int oneSizeOrder = 0;
        int xsOrder = 0;
        int sOrder = 0;
        int mOrder = 0;
        int lOrder = 0;
        int xlOrder = 0;
        int xxlOrder = 0;
        int xxxlOrder = 0;
        for(OrderCreationDetailsJson orderCreationDetailsJson : creationDetailsJsonList){
            oneSizeOrder = oneSizeOrder + intValueOf(orderCreationDetailsJson.getOneSize());
            xsOrder = xsOrder + intValueOf(orderCreationDetailsJson.getXs());
            sOrder = sOrder + intValueOf(orderCreationDetailsJson.getS());
            mOrder = mOrder + intValueOf(orderCreationDetailsJson.getM());
            lOrder = lOrder + intValueOf(orderCreationDetailsJson.getL());
            xlOrder = xlOrder + intValueOf(orderCreationDetailsJson.getXl());
            xxlOrder = xxlOrder + intValueOf(orderCreationDetailsJson.getXxl());
            xxxlOrder = xxxlOrder + intValueOf(orderCreationDetailsJson.getXxxl());
        }
        int totalOrder = oneSizeOrder + xsOrder + sOrder + mOrder + lOrder + xlOrder +xxlOrder + xxxlOrder;

        int oneSizeCaptured = 0;
        int xsCaptured = 0;
        int sCaptured = 0;
        int mCaptured = 0;
        int lCaptured = 0;
        int xlCaptured = 0;
        int xxlCaptured = 0;
        int xxxlCaptured = 0;




        for(CartonDetailsJson cartonDetailsJson : cartonDetailsJsonsList) {
            List<OrderDetailsListViewModel> orderDetailsListViewModelList = cartonDetailsJson.getOrderDetailsListViewModels();
            for (OrderDetailsListViewModel detailsListViewModel : orderDetailsListViewModelList) {
                oneSizeCaptured = oneSizeCaptured + intValueOf(detailsListViewModel.getProductOneSize());
                xsCaptured = xsCaptured + intValueOf(detailsListViewModel.getProductXS());
                sCaptured = sCaptured + intValueOf(detailsListViewModel.getProductS());
                mCaptured = mCaptured + intValueOf(detailsListViewModel.getProductM());
                lCaptured = lCaptured + intValueOf(detailsListViewModel.getProductL());
                xlCaptured = xlCaptured + intValueOf(detailsListViewModel.getProductXl());
                xxlCaptured = xxlCaptured + intValueOf(detailsListViewModel.getProductXxl());
                xxxlCaptured = xxxlCaptured + intValueOf(detailsListViewModel.getProductXxxl());
            }
        }

        int totalCaptured = oneSizeCaptured + xsCaptured + sCaptured + mCaptured + lCaptured + xlCaptured +xxlCaptured + xxxlCaptured;
        return  totalCaptured  != totalOrder;
    }


    public void calcAvailableCount(OrderDetailsListViewModel orderDetailsListViewModel,List<CartonDetailsJson> cartonDetailsJsonsList){
        List<ProductDetailsEntity> productDetailsEntityList = cartonbookDao.getOrderItem(orderDetailsListViewModel.getOrderItemGuid());
        int oneSize = 0;
        int xs = 0;
        int s = 0;
        int m = 0;
        int l = 0;
        int xl = 0;
        int xxl = 0;
        int xxxl = 0;

        for(ProductDetailsEntity productDetailsEntity : productDetailsEntityList){
            oneSize = oneSize + intValueOf(productDetailsEntity.getOneSize());
            xs = xs + intValueOf(productDetailsEntity.getXs());
            s = s + intValueOf(productDetailsEntity.getS());
            m = m + intValueOf(productDetailsEntity.getM());
            l = l + intValueOf(productDetailsEntity.getL());
            xl = xl + intValueOf(productDetailsEntity.getXl());
            xxl = xxl + intValueOf(productDetailsEntity.getXxl());
            xxxl = xxxl + intValueOf(productDetailsEntity.getXxxl());
        }

        for(CartonDetailsJson cartonDetailsJson : cartonDetailsJsonsList){
            List<OrderDetailsListViewModel> orderDetailsListViewModelList =  cartonDetailsJson.getOrderDetailsListViewModels();
            for(OrderDetailsListViewModel detailsListViewModel : orderDetailsListViewModelList){
                if(orderDetailsListViewModel.getOrderItemGuid().equals(detailsListViewModel.getOrderItemGuid())){
                    oneSize = oneSize + intValueOf(detailsListViewModel.getProductOneSize());
                    xs = xs + intValueOf(detailsListViewModel.getProductXS());
                    s = s + intValueOf(detailsListViewModel.getProductS());
                    m = m + intValueOf(detailsListViewModel.getProductM());
                    l = l + intValueOf(detailsListViewModel.getProductL());
                    xl = xl + intValueOf(detailsListViewModel.getProductXl());
                    xxl = xxl + intValueOf(detailsListViewModel.getProductXxl());
                    xxxl = xxxl + intValueOf(detailsListViewModel.getProductXxxl());
                }
            }
        }
        orderDetailsListViewModel.setRemainingOneSize(calculate(orderDetailsListViewModel.getOrderItemOneSize() , oneSize));
        orderDetailsListViewModel.setRemainingS(calculate(orderDetailsListViewModel.getOrderItemS() , s));
        orderDetailsListViewModel.setRemainingM(calculate(orderDetailsListViewModel.getOrderItemM() , m));
        orderDetailsListViewModel.setRemainingL(calculate(orderDetailsListViewModel.getOrderItemL() , l));
        orderDetailsListViewModel.setRemainingXS(calculate(orderDetailsListViewModel.getOrderItemXS() , xs));
        orderDetailsListViewModel.setRemainingXl(calculate(orderDetailsListViewModel.getOrderItemXl() , xl));
        orderDetailsListViewModel.setRemainingXxl(calculate(orderDetailsListViewModel.getOrderItemXxl() , xxl));
        orderDetailsListViewModel.setRemainingXxxl(calculate(orderDetailsListViewModel.getOrderItemXxxl() , xxxl));

    }


    public void addDelivery(List<Integer> cartonsIds, int deliveryId, Status status){
        try{
           DeliveryDetailsEntity deliveryDetailsEntity =  cartonbookDao.getDeliveryDetailsEntity(deliveryId);
           for(Integer cartonsId : cartonsIds){
               CartonDetailsEntity cartonDetailsEntity = cartonbookDao.getCartonDetailsEntity(cartonsId);
               cartonDetailsEntity.setDeliveryDetails(deliveryDetailsEntity);
               cartonDetailsEntity.setLastModifiedTime(System.currentTimeMillis());
               cartonbookDao.updateCartonDetailsEntity(cartonDetailsEntity);

               cartonDetailsEntity.getOrderEntity().setLastModifiedDate(cartonDetailsEntity.getLastModifiedTime());
               cartonDetailsEntity.getOrderEntity().setSync(false);
               cartonbookDao.updateCortonbookEntity(cartonDetailsEntity.getOrderEntity());

           }
            deliveryDetailsEntity.setStatus(status);
            deliveryDetailsEntity.setLastModifiedDateTime(System.currentTimeMillis());
            deliveryDetailsEntity.setSync(false);
            cartonbookDao.updateDeliveryDetailsEntity(deliveryDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int intValueOf(String value){
        if(value != null && !value.trim().isEmpty()){
           return Integer.valueOf(value);
        }
        return 0;
    }

    private String calculate(String value,int val){
        if(value != null){
          return String.valueOf(Integer.valueOf(value) - val);
        }
        return null;
    }


    public void createDelivery(DeliveryDetailsEntity deliveryDetailsEntity){
        cartonbookDao.createDeliveryDetailsEntity(deliveryDetailsEntity);
    }


    public void updateOrderUpdates(OrderEntity orderEntity){
        cartonbookDao.updateCortonbookEntity(orderEntity);
    }


    public void updateCartonDetailsEntity(CartonDetailsEntity cartonDetailsEntity){
        cartonbookDao.updateCartonDetailsEntity(cartonDetailsEntity);
    }


    public List<CartonDetailsEntity> getUnDeliveryCartonDetailsEntity(OrderEntity orderEntity){
       return cartonbookDao.getUnDeliveredCartonDetailsList(orderEntity);
    }
}
