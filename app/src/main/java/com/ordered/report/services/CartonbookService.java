package com.ordered.report.services;

import android.content.Context;

import com.ordered.report.SyncAdapter.SyncServiceApi;
import com.ordered.report.dao.CartonbookDao;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.json.models.LoginEvent;
import com.ordered.report.models.OrderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class CartonbookService {
    private Context context = null;
    private SyncServiceApi syncServiceApi = null;
    public final String LOG_TAG = LoginService.class.getSimpleName();
    private CartonbookDao cartonbookDao = null;
    private LoginEvent loginEvent = null;
    public static final String AUTHORITY = "com.ordered.report.SyncAdapter";

    public CartonbookService(Context context) {
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

    public List<OrderEntity> getCartonBookEntityByType(OrderType orderType) {
        if (orderType.toString().equals(OrderType.ORDERED.toString())) {
           return cartonbookDao.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderType.PACKING.toString())) {
           return cartonbookDao.getCartonBookByOrderType(orderType);
        } else if (orderType.toString().equals(OrderType.DELIVERED.toString())) {
           return cartonbookDao.getCartonBookByOrderType(orderType);
        }
        return new ArrayList<>();
    }
}
