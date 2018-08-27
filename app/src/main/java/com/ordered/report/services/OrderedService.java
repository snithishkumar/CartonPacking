package com.ordered.report.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.OrderDAO;
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
import com.ordered.report.view.models.OrderViewListModel;
import com.ordered.report.view.models.OrderViewOrderedItemsListModel;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private OrderDAO orderDAO = null;
    private LoginEvent loginEvent = null;
    public static final String AUTHORITY = "com.ordered.report.SyncAdapter";
    private Gson gson;

    public OrderedService(Context context) {
        try {
            this.context = context;
            orderDAO = new OrderDAO(context);
            gson = new Gson();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderEntity> getCartonBookEntityList(String userName) {
        try {
            return orderDAO.getAllCartonbokEntityList(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public OrderEntity getOrderEntityByGuid(String orderGuid) {
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);
        return orderEntity;
    }

    public void createProductEntity(ProductDetailsEntity productDetailsEntity) {
        try {
            orderDAO.saveProductEntity(productDetailsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductDetailsEntity> getProductEntityList(OrderEntity orderEntity) {
        List<ProductDetailsEntity> productEntities = orderDAO.getProductEntityList(orderEntity);
        return productEntities;
    }


    public List<ProductDetailsEntity> getProductEntityList(String cartonGuid) {
        List<ProductDetailsEntity> productEntities = orderDAO.getProductEntityList(cartonGuid);
        return productEntities;
    }


    public List<ProductDetailsEntity> getDeliveredProduct(String deliveryGuid) {
        List<ProductDetailsEntity> productList = new ArrayList<>();
        DeliveryDetailsEntity deliveryDetailsEntity = orderDAO.getDeliveryDetailsEntity(deliveryGuid);
        List<CartonDetailsEntity> cartonDetailsEntities = orderDAO.getCartonDetailsList(deliveryDetailsEntity);
        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntities){
            List<ProductDetailsEntity> productEntities = orderDAO.getProductEntityList(cartonDetailsEntity.getCartonGuid());
            productList.addAll(productEntities);
        }

        return productList;
    }


    public List<OrderEntity> getOrdersList(){
        return orderDAO.getOrders();
    }


    public List<OrderEntity> getPackingOrdersList(){
        return orderDAO.getPackingOrders();
    }


    public List<OrderEntity> getDeliveryOrdersList(){
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderEntity> orderEntityList = orderDAO.getPackingOrders();
        for(OrderEntity orderEntity : orderEntityList){
            List<CartonDetailsEntity> detailsEntities =  orderDAO.getUnDeliveredCartonDetailsList(orderEntity);
            if(detailsEntities.size() > 0){
                orderList.add(orderEntity);
            }
        }
        return orderList;
    }


    public List<DeliveryDetailsEntity> getDeliveredOrdersList(){
        return orderDAO.getDeliveryDetailsEntity();
    }


    public List<DeliveryDetailsEntity> getDeliveredOrdersList(OrderEntity orderEntity){
        return orderDAO.getDeliveryList(orderEntity);
    }

    public List<DeliveryDetailsEntity> getCompletedDeliveryDetailsEntity(){
        return orderDAO.getCompletedDeliveryDetailsEntity();
    }


    public List<CartonDetailsEntity> getCartonDetailsEntities(int deliveryId){
        DeliveryDetailsEntity deliveryDetailsEntity = orderDAO.getDeliveryDetailsEntity(deliveryId);
        return  orderDAO.getCartonDetailsList(deliveryDetailsEntity);
    }

    public DeliveryDetailsEntity getDeliveryDetailsEntity(int deliveryId){
        DeliveryDetailsEntity deliveryDetailsEntity = orderDAO.getDeliveryDetailsEntity(deliveryId);
        return deliveryDetailsEntity;
    }

    public List<OrderEntity> getCartonBookEntityByType(OrderStatus orderType) {
        if (orderType.toString().equals(OrderStatus.ORDERED.toString())) {
            return orderDAO.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderStatus.PACKING.toString())) {
            return orderDAO.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderType.DELIVERED.toString())) {
            return orderDAO.getCartonBookByOrderType(orderType);
        }
        return new ArrayList<>();
    }





    public Map<String,List> getOrderItems(String orderGuid){
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);
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
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(cartonbookGuid);
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
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(cartonbookGuid);
       String orderItems =  orderEntity.getOrderedItems();
        List<OrderCreationDetailsJson>  orderCreationDetailsJsons =  getOrderedItems(orderItems);
        List<CartonDetailsEntity> cartonDetailsEntityList =  orderDAO.getCartonDetailsList(orderEntity);
        List<CartonDetailsJson> cartonDetailsJsonList = new ArrayList<>();
        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntityList){
            CartonDetailsJson cartonDetailsJson = new CartonDetailsJson(cartonDetailsEntity);
            List<ProductDetailsEntity> productDetailsEntities =  orderDAO.getProductDetailsEntityList(orderEntity,cartonDetailsEntity);
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


    public boolean saveProductDetails(String orderGuid,List<CartonDetailsJson> cartonDetailsJsonList,OrderStatus orderStatus, String totalCartonCount){
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);
        boolean isEdited = false;
       for(CartonDetailsJson cartonDetailsJson : cartonDetailsJsonList){
           CartonDetailsEntity cartonDetailsEntity =  orderDAO.getCartonDetailsEntity(cartonDetailsJson.getCartonGuid());
           if(cartonDetailsEntity == null){
               cartonDetailsEntity = new CartonDetailsEntity(cartonDetailsJson);
               cartonDetailsEntity.setOrderEntity(orderEntity);
               orderDAO.createCartonDetailsEntity(cartonDetailsEntity);
           }else{
               cartonDetailsEntity.setLastModifiedBy(cartonDetailsJson.getLastModifiedBy());
               cartonDetailsEntity.setLastModifiedTime(cartonDetailsJson.getLastModifiedTime());
               cartonDetailsEntity.setTotalWeight(cartonDetailsJson.getTotalWeight());
               orderDAO.updateCartonDetailsEntity(cartonDetailsEntity);
           }
           List<OrderDetailsListViewModel> orderDetailsListViewModels = cartonDetailsJson.getOrderDetailsListViewModels();
           for(OrderDetailsListViewModel orderDetailsListViewModel : orderDetailsListViewModels){
               isEdited = true;
               ProductDetailsEntity productDetailsEntity =  orderDAO.getProductDetails(orderDetailsListViewModel.getProductGuid());
               if(productDetailsEntity == null){
                   productDetailsEntity = new ProductDetailsEntity(orderDetailsListViewModel);
                   productDetailsEntity.setCartonNumber(cartonDetailsEntity);
                   productDetailsEntity.setOrderEntity(orderEntity);
                   productDetailsEntity.setCreatedBy(Constants.getLoginUser());
                   productDetailsEntity.setModifiedBy(Constants.getLoginUser());
                   productDetailsEntity.setLastModifiedDateTime(productDetailsEntity.getCreatedDateTime());
                   orderDAO.createProductDetailsEntity(productDetailsEntity);
               }
           }

       }


        if(isEdited){
            orderEntity.setSync(false);
            orderEntity.setCartonCounts(String.valueOf(totalCartonCount));
            orderEntity.setLastModifiedDate(System.currentTimeMillis());
            orderEntity.setOrderStatus(OrderStatus.PACKING);

            orderDAO.updateCortonbookEntity(orderEntity);
        }
        return isEdited;

    }

    public void calcAvailableCount(List<OrderDetailsListViewModel> orderDetailsListViewModelList,List<CartonDetailsJson> cartonDetailsJsonsList){
        for(OrderDetailsListViewModel orderDetailsListViewModel : orderDetailsListViewModelList){
            calcAvailableCount(orderDetailsListViewModel,cartonDetailsJsonsList);
        }
    }

    public boolean checkAvailability(List<CartonDetailsJson> cartonDetailsJsonsList,String orderGuid){
        OrderEntity orderEntity = orderDAO.getOrderEntityByGuid(orderGuid);
        String orderItems = orderEntity.getOrderedItems();
        List<OrderCreationDetailsJson> creationDetailsJsonList =  getOrderedItems(orderItems);
       // List<ProductDetailsEntity> productDetailsEntityList = orderDAO.getProductEntityList(orderEntity);
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
        return  totalCaptured  < totalOrder;
    }


    public void calcAvailableCount(OrderDetailsListViewModel orderDetailsListViewModel,List<CartonDetailsJson> cartonDetailsJsonsList){
        List<ProductDetailsEntity> productDetailsEntityList = orderDAO.getOrderItem(orderDetailsListViewModel.getOrderItemGuid());
        int oneSize = 0;
        int xs = 0;
        int s = 0;
        int m = 0;
        int l = 0;
        int xl = 0;
        int xxl = 0;
        int xxxl = 0;

       /* for(ProductDetailsEntity productDetailsEntity : productDetailsEntityList){
            oneSize = oneSize + intValueOf(productDetailsEntity.getOneSize());
            xs = xs + intValueOf(productDetailsEntity.getXs());
            s = s + intValueOf(productDetailsEntity.getS());
            m = m + intValueOf(productDetailsEntity.getM());
            l = l + intValueOf(productDetailsEntity.getL());
            xl = xl + intValueOf(productDetailsEntity.getXl());
            xxl = xxl + intValueOf(productDetailsEntity.getXxl());
            xxxl = xxxl + intValueOf(productDetailsEntity.getXxxl());
        }*/

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
           DeliveryDetailsEntity deliveryDetailsEntity =  orderDAO.getDeliveryDetailsEntity(deliveryId);
            JsonArray orderGuids = new JsonArray();
           for(Integer cartonsId : cartonsIds){
               CartonDetailsEntity cartonDetailsEntity = orderDAO.getCartonDetailsEntity(cartonsId);
               cartonDetailsEntity.setDeliveryDetails(deliveryDetailsEntity);
               cartonDetailsEntity.setLastModifiedTime(System.currentTimeMillis());
               orderDAO.updateCartonDetailsEntity(cartonDetailsEntity);

               cartonDetailsEntity.getOrderEntity().setLastModifiedDate(cartonDetailsEntity.getLastModifiedTime());
               cartonDetailsEntity.getOrderEntity().setSync(false);

               boolean isAvailable = checkAvailability(cartonDetailsEntity.getOrderEntity());
               if(!isAvailable){
                   cartonDetailsEntity.getOrderEntity().setOrderStatus(OrderStatus.DELIVERED);
               }
               orderDAO.updateCortonbookEntity(cartonDetailsEntity.getOrderEntity());
               orderGuids.add(cartonDetailsEntity.getOrderEntity().getOrderGuid());

           }
            String orderIds = deliveryDetailsEntity.getOrderGuids();
            JsonParser jsonParser = new JsonParser();
            if(orderIds != null){
             JsonArray jsonArray =   (JsonArray)jsonParser.parse(orderIds);
             orderGuids.addAll(jsonArray);
            }
            String orderGuidsTemp = orderGuids.toString();
            deliveryDetailsEntity.setOrderGuids(orderGuidsTemp);
            deliveryDetailsEntity.setStatus(status);
            deliveryDetailsEntity.setLastModifiedDateTime(System.currentTimeMillis());
            deliveryDetailsEntity.setSync(false);
            orderDAO.updateDeliveryDetailsEntity(deliveryDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public Map<String,Long> getCartonCount(DeliveryDetailsEntity deliveryDetailsEntity){
        List<CartonDetailsEntity> cartonDetailsEntities =  orderDAO.getCartonDetailsList(deliveryDetailsEntity);
        long productCounts = 0;
        for(CartonDetailsEntity cartonDetailsEntity : cartonDetailsEntities){
            productCounts = productCounts + orderDAO.getProductDetailsCount(cartonDetailsEntity);
        }
        long cartonCounts= cartonDetailsEntities.size();
        Map<String,Long> countDetails = new HashMap<>();
        countDetails.put("productCounts",productCounts);
        countDetails.put("cartonCounts",cartonCounts);
        return countDetails;
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
        orderDAO.createDeliveryDetailsEntity(deliveryDetailsEntity);
    }


    public void updateOrderUpdates(OrderEntity orderEntity){
        orderDAO.updateCortonbookEntity(orderEntity);
    }


    public void updateCartonDetailsEntity(CartonDetailsEntity cartonDetailsEntity){
        orderDAO.updateCartonDetailsEntity(cartonDetailsEntity);
    }


    public List<CartonDetailsEntity> getUnDeliveryCartonDetailsEntity(OrderEntity orderEntity){
       return orderDAO.getUnDeliveredCartonDetailsList(orderEntity);
    }



    public boolean checkAvailability(OrderEntity orderEntity){
        String orderItems = orderEntity.getOrderedItems();
        List<OrderCreationDetailsJson> creationDetailsJsonList =  getOrderedItems(orderItems);
         List<ProductDetailsEntity> productDetailsEntityList = orderDAO.getProductEntityList(orderEntity);
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




        for(ProductDetailsEntity productDetailsEntity : productDetailsEntityList) {
            oneSizeCaptured = oneSizeCaptured + intValueOf(productDetailsEntity.getOneSize());
            xsCaptured = xsCaptured + intValueOf(productDetailsEntity.getXs());
            sCaptured = sCaptured + intValueOf(productDetailsEntity.getS());
            mCaptured = mCaptured + intValueOf(productDetailsEntity.getM());
            lCaptured = lCaptured + intValueOf(productDetailsEntity.getL());
            xlCaptured = xlCaptured + intValueOf(productDetailsEntity.getXl());
            xxlCaptured = xxlCaptured + intValueOf(productDetailsEntity.getXxl());
            xxxlCaptured = xxxlCaptured + intValueOf(productDetailsEntity.getXxxl());
        }

        int totalCaptured = oneSizeCaptured + xsCaptured + sCaptured + mCaptured + lCaptured + xlCaptured +xxlCaptured + xxxlCaptured;
        return  totalCaptured  < totalOrder;
    }


    public List<OrderViewListModel> getOrderViewListModels(){
        List<OrderEntity> orderEntityList = orderDAO.getOrderList();
        List<OrderViewListModel> orderViewListModelList = new ArrayList<>();
        for(OrderEntity orderEntity : orderEntityList){

            OrderViewListModel orderViewListModel = new OrderViewListModel(orderEntity);
            orderViewListModel.setOrderDateTime(formatDate(orderEntity.getOrderedDate()));
            long cartonCount = orderDAO.getCartonsCount(orderEntity);
            orderViewListModel.setCartonCount(String.valueOf(cartonCount));
            long deliveryCount = orderDAO.getDeliveryCounts(orderEntity);
            orderViewListModel.setDeliveryCount(String.valueOf(deliveryCount));

            List<OrderCreationDetailsJson> creationDetailsJsons =   getOrderedItems(orderEntity.getOrderedItems());
            orderViewListModel.setProductCount(String.valueOf(creationDetailsJsons.size()));

            orderViewListModelList.add(orderViewListModel);
        }
        return orderViewListModelList;
    }


    public List<OrderViewOrderedItemsListModel> getOrderViewOrderedItemsListModels(OrderEntity orderEntity){
        Type listType = new TypeToken<ArrayList<OrderViewOrderedItemsListModel>>() {
        }.getType();
        List<OrderViewOrderedItemsListModel> orderViewOrderedItemsListModels =  gson.fromJson(orderEntity.getOrderedItems(), listType);

       for(OrderViewOrderedItemsListModel orderViewOrderedItemsListModel : orderViewOrderedItemsListModels){
           String orderItemGuid = orderViewOrderedItemsListModel.getOrderItemGuid();

           long cartonCount =  orderDAO.getCartonCount(orderItemGuid);
           long deliveryCount = orderDAO.getDeliveryCount(orderItemGuid);
           orderViewOrderedItemsListModel.setCartonCounts(String.valueOf(cartonCount));
           orderViewOrderedItemsListModel.setDeliveryCounts(String.valueOf(deliveryCount));

           int totalOneSize = Integer.valueOf(orderViewOrderedItemsListModel.getOneSize());
           int totalXS = Integer.valueOf(orderViewOrderedItemsListModel.getXs());
           int totalS = Integer.valueOf(orderViewOrderedItemsListModel.getS());

           int totalM = Integer.valueOf(orderViewOrderedItemsListModel.getM());
           int totalL = Integer.valueOf(orderViewOrderedItemsListModel.getL());
           int totalXL = Integer.valueOf(orderViewOrderedItemsListModel.getXl());

           int totalXXL = Integer.valueOf(orderViewOrderedItemsListModel.getXxl());
           int totalXXXL = Integer.valueOf(orderViewOrderedItemsListModel.getXxl());


           int processedOneSize = 0;
           int processedXS = 0;
           int processedS = 0;

           int processedM = 0;
           int processedL = 0;
           int processedXL = 0;

           int processedXXL = 0;
           int processedXXXL = 0;


           int deliveredOneSize = 0;
           int deliveredXS = 0;
           int deliveredS = 0;

           int deliveredM = 0;
           int deliveredL = 0;
           int deliveredXL = 0;

           int deliveredXXL = 0;
           int deliveredXXXL = 0;


           List<ProductDetailsEntity> productDetailsEntityList = orderDAO.getOrderItem(orderItemGuid);
           for(ProductDetailsEntity productDetailsEntity : productDetailsEntityList){
               if(productDetailsEntity.getCartonNumber().getDeliveryDetails() != null){
                   deliveredOneSize = deliveredOneSize + Integer.valueOf(productDetailsEntity.getOneSize());
                   deliveredXS = deliveredXS + Integer.valueOf(productDetailsEntity.getXs());
                   deliveredS = deliveredS + Integer.valueOf(productDetailsEntity.getS());

                   deliveredM = deliveredM + Integer.valueOf(productDetailsEntity.getM());
                   deliveredL = deliveredL + Integer.valueOf(productDetailsEntity.getL());
                   deliveredXL = deliveredXL + Integer.valueOf(productDetailsEntity.getXl());

                   deliveredXXL = deliveredXXL + Integer.valueOf(productDetailsEntity.getXxl());
                   deliveredXXXL = deliveredXXXL + Integer.valueOf(productDetailsEntity.getXxxl());
               }
               processedOneSize = processedOneSize + Integer.valueOf(productDetailsEntity.getOneSize());
               processedXS = processedXS + Integer.valueOf(productDetailsEntity.getXs());
               processedS = processedS + Integer.valueOf(productDetailsEntity.getS());

               processedM = processedM + Integer.valueOf(productDetailsEntity.getM());
               processedL = processedL + Integer.valueOf(productDetailsEntity.getL());
               processedXL = processedXL + Integer.valueOf(productDetailsEntity.getXl());

               processedXXL = processedXXL + Integer.valueOf(productDetailsEntity.getXxl());
               processedXXXL = processedXXXL + Integer.valueOf(productDetailsEntity.getXxxl());

           }
           orderViewOrderedItemsListModel.setDeliveryL(String.valueOf(deliveredL));
           orderViewOrderedItemsListModel.setDeliveryM(String.valueOf(deliveredM));
           orderViewOrderedItemsListModel.setDeliveryOneSize(String.valueOf(deliveredOneSize));
           orderViewOrderedItemsListModel.setDeliveryS(String.valueOf(deliveredS));
           orderViewOrderedItemsListModel.setDeliveryXL(String.valueOf(deliveredXL));
           orderViewOrderedItemsListModel.setDeliveryXS(String.valueOf(deliveredXS));
           orderViewOrderedItemsListModel.setDeliveryXXL(String.valueOf(deliveredXXL));
           orderViewOrderedItemsListModel.setDeliveryXXXL(String.valueOf(deliveredXXXL));

           orderViewOrderedItemsListModel.setProcessedL(String.valueOf(processedL));
           orderViewOrderedItemsListModel.setProcessedM(String.valueOf(processedM));
           orderViewOrderedItemsListModel.setProcessedOneSize(String.valueOf(processedOneSize));
           orderViewOrderedItemsListModel.setProcessedS(String.valueOf(processedS));
           orderViewOrderedItemsListModel.setProcessedXL(String.valueOf(processedXL));
           orderViewOrderedItemsListModel.setProcessedXS(String.valueOf(processedXS));
           orderViewOrderedItemsListModel.setProcessedXXL(String.valueOf(processedXXL));
           orderViewOrderedItemsListModel.setProcessedXXXL(String.valueOf(processedXXXL));

           orderViewOrderedItemsListModel.setUnProcessedL(String.valueOf(totalL - processedL));
           orderViewOrderedItemsListModel.setUnProcessedM(String.valueOf(totalM - processedM));
           orderViewOrderedItemsListModel.setUnProcessedOneSize(String.valueOf(totalOneSize - processedOneSize));
           orderViewOrderedItemsListModel.setUnProcessedS(String.valueOf(totalS - processedS));
           orderViewOrderedItemsListModel.setUnProcessedXL(String.valueOf(totalXL - processedXL));
           orderViewOrderedItemsListModel.setUnProcessedXXL(String.valueOf(totalXXL - processedXXL));
           orderViewOrderedItemsListModel.setUnProcessedXXXL(String.valueOf(totalXXXL - processedXXXL));
           orderViewOrderedItemsListModel.setUnProcessedXS(String.valueOf(totalXS - processedXS));
       }

return orderViewOrderedItemsListModels;

    }


    private String formatDate(long dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date date = new Date(dateTime);
        String val = simpleDateFormat.format(date);
        return val;
    }
}
