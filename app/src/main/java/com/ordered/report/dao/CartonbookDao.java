package com.ordered.report.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.ordered.report.db.DatabaseHelper;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.OrderType;
import com.ordered.report.models.CartonItemEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class CartonbookDao {
    DatabaseHelper databaseHelper = null;
    Dao<OrderEntity, String> cartonbookDao = null;
    Dao<CartonItemEntity, String> cartonItemDao = null;
    Dao<ProductEntity, String> productDao = null;

    public CartonbookDao(Context context) throws Exception {
        this.databaseHelper = DatabaseHelper.getInstance(context);
        initDaos();
    }

    private void initDaos() throws Exception {
        try {
            cartonbookDao = getCartonbookDao();
            cartonItemDao = databaseHelper.getDao(CartonItemEntity.class);
            productDao = databaseHelper.getDao(ProductEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dao<OrderEntity, String> getCartonbookDao() throws SQLException {
        return databaseHelper.getDao(OrderEntity.class);
    }

    public List<OrderEntity> getAllCartonbokEntityList(String userName) {
        try {
            return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateCortonbookEntity(OrderEntity orderEntity) {
        try {
            cartonbookDao.update(orderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void savCartonbookEntity(OrderEntity orderEntity) {
        try {
            cartonbookDao.create(orderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProductEntity(ProductEntity productEntity) {
        try {
            productDao.create(productEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductEntity> getProductEntityList(OrderEntity orderEntity) {
        try {
            return productDao.queryBuilder().where().eq(ProductEntity.PRODICT_ID,orderEntity).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public OrderEntity getCartonBookEntityByGuid(String cartonbookGuid) {
        try {
            return cartonbookDao.queryBuilder().where().eq(OrderEntity.ORDER_GUID, cartonbookGuid).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderEntity> getCartonBookEntityByGuid() {
        try {
            return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<OrderEntity> getUnSyncedOrderDetails() {
        try {
            return cartonbookDao.queryBuilder().where().eq(OrderEntity.IS_SYNC, false).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OrderEntity> getCartonBookByOrderType(OrderStatus orderType) {
        try {
            return cartonbookDao.queryBuilder().where().eq(OrderEntity.ORDER_STATUS, orderType).query();
            //return cartonbookDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateCortonbookItemEntity(CartonItemEntity cartonItemEntity) {
        try {
            cartonItemDao.update(cartonItemEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveCartonItemEntity(CartonItemEntity cartonItemEntity) {
        try {
            cartonItemDao.create(cartonItemEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CartonItemEntity getCartonItemEntityByGuid(String cartonItemGuid) {
        try {
            return cartonItemDao.queryBuilder().where().eq(CartonItemEntity.CARTON_TEM_GUID, cartonItemGuid).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrderEntity getMaxSyncCartonBook() {
        try {
            QueryBuilder<OrderEntity, String> queryBuilder = cartonbookDao.queryBuilder();
            queryBuilder.where().eq(OrderEntity.IS_SYNC, true);
            queryBuilder.orderBy(OrderEntity.SERVER_TIME, false);
            queryBuilder.limit(1L);
            return queryBuilder.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updateSyncStatus(String orderGuid) {
        try {
            UpdateBuilder<OrderEntity, String> updateBuilder = cartonbookDao.updateBuilder();
            updateBuilder.updateColumnValue(OrderEntity.IS_SYNC, true);
            updateBuilder.where().eq(OrderEntity.ORDER_GUID, orderGuid);
            updateBuilder.update();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
