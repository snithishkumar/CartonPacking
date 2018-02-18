package com.ordered.report.services;

import android.content.Context;

import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductEntity;

import java.util.ArrayList;
import java.util.List;

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

    public OrderedService(Context context) {
        try {
            this.context = context;
            cartonbookDao = new CartonbookDao(context);
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

    public void createProductEntity(ProductEntity productEntity) {
        try {
            cartonbookDao.saveProductEntity(productEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductEntity> getProductEntityList(OrderEntity orderEntity) {
        List<ProductEntity> productEntities = cartonbookDao.getProductEntityList(orderEntity);
        return productEntities;
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


    public void updateOrderUpdates(OrderEntity orderEntity){
        cartonbookDao.updateCortonbookEntity(orderEntity);
    }
}
