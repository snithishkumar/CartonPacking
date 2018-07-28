package com.ordered.report.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.ordered.report.db.DatabaseHelper;
import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.models.ClientDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;
import com.ordered.report.models.ProductDetailsEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/1/2018.
 */

public class CartonbookDao {
    DatabaseHelper databaseHelper = null;
    Dao<OrderEntity, String> orderDao = null;
    Dao<CartonDetailsEntity, String> cartonItemDao = null;
    Dao<ProductDetailsEntity, String> productDao = null;
    Dao<ClientDetailsEntity,String> clientDetailsDao = null;
    Dao<DeliveryDetailsEntity,String> deliveryDetailsDao = null;

    public CartonbookDao(Context context) throws Exception {
        this.databaseHelper = DatabaseHelper.getInstance(context);
        initDaos();
    }

    private void initDaos() throws Exception {
        try {
            orderDao = getOrderDao();
            cartonItemDao = databaseHelper.getDao(CartonDetailsEntity.class);
            productDao = databaseHelper.getDao(ProductDetailsEntity.class);
            clientDetailsDao = databaseHelper.getDao(ClientDetailsEntity.class);
            deliveryDetailsDao = databaseHelper.getDao(DeliveryDetailsEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dao<OrderEntity, String> getOrderDao() throws SQLException {
        return databaseHelper.getDao(OrderEntity.class);
    }

    public List<OrderEntity> getAllCartonbokEntityList(String userName) {
        try {
            return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateCortonbookEntity(OrderEntity orderEntity) {
        try {
            orderDao.update(orderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void savCartonbookEntity(OrderEntity orderEntity) {
        try {
            orderDao.create(orderEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveProductEntity(ProductDetailsEntity productDetailsEntity) {
        try {
            productDao.create(productDetailsEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ProductDetailsEntity> getProductEntityList(OrderEntity orderEntity) {
        try {
            return productDao.queryBuilder().where().eq(ProductDetailsEntity.ORDER_ENTITY,orderEntity).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public OrderEntity getOrderEntityByGuid(String cartonbookGuid) {
        try {
            return orderDao.queryBuilder().where().eq(OrderEntity.ORDER_GUID, cartonbookGuid).queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public CartonDetailsEntity getCartonDetailsEntity(Integer cartonNumberId){
        try {
            return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.CARTON_ID, cartonNumberId).queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




    public List<OrderEntity> getUnSyncedOrderDetails() {
        try {
            return orderDao.queryBuilder().where().eq(OrderEntity.IS_SYNC, false).query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<OrderEntity> getCartonBookByOrderType(OrderStatus orderType) {
        try {
            return orderDao.queryBuilder().where().eq(OrderEntity.ORDER_STATUS, orderType).query();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }






    public OrderEntity getMaxSyncCartonBook() {
        try {
            QueryBuilder<OrderEntity, String> queryBuilder = orderDao.queryBuilder();
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
            UpdateBuilder<OrderEntity, String> updateBuilder = orderDao.updateBuilder();
            updateBuilder.updateColumnValue(OrderEntity.IS_SYNC, true);
            updateBuilder.where().eq(OrderEntity.ORDER_GUID, orderGuid);
            updateBuilder.update();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void createCartonDetailsEntity(CartonDetailsEntity cartonDetailsEntity){
        try{
            cartonItemDao.create(cartonDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void updateCartonDetailsEntity(CartonDetailsEntity cartonDetailsEntity){
        try{
            cartonItemDao.update(cartonDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public List<CartonDetailsEntity> getCartonDetailsList(OrderEntity orderEntity){
        try{
            return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.ORDER_ENTITY,orderEntity).query();
        }catch (Exception e){
            e.printStackTrace();
        }
       return new ArrayList<>();
    }


    public List<CartonDetailsEntity> getCartonDetailsList(OrderEntity orderEntity,DeliveryDetailsEntity deliveryDetailsEntity){
        try{
            return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.ORDER_ENTITY,orderEntity).and().eq(CartonDetailsEntity.DELIVERY_DETAILS_ENTITY,deliveryDetailsEntity).query();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<CartonDetailsEntity> getCartonDetailsList(DeliveryDetailsEntity deliveryDetailsEntity){
        try{
            return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.DELIVERY_DETAILS_ENTITY,deliveryDetailsEntity).query();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<CartonDetailsEntity> getUnDeliveredCartonDetailsList(OrderEntity orderEntity){
        try{
            return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.ORDER_ENTITY,orderEntity).and().isNull(CartonDetailsEntity.DELIVERY_DETAILS_ENTITY).query();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public CartonDetailsEntity getCartonDetailsEntity(String cartonGuid){
        try{
           return cartonItemDao.queryBuilder().where().eq(CartonDetailsEntity.CARTON_GUID,cartonGuid).queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public void createProductDetailsEntity(ProductDetailsEntity productDetailsEntity){
        try{
            productDao.create(productDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ProductDetailsEntity> getProductDetailsEntityList(OrderEntity orderEntity,CartonDetailsEntity cartonDetailsEntity){
        try{
            QueryBuilder<ProductDetailsEntity, String> queryBuilder = productDao.queryBuilder();
            queryBuilder.where().eq(ProductDetailsEntity.CARTON_NUMBER, cartonDetailsEntity).and().eq(ProductDetailsEntity.ORDER_ENTITY,orderEntity);
            return queryBuilder.query();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }






    public  List<ProductDetailsEntity> getCategoryList(List<CartonDetailsEntity> cartonDetailsEntityList){
        try{
            QueryBuilder<ProductDetailsEntity, String> queryBuilder = productDao.queryBuilder();
            queryBuilder.distinct().selectColumns(ProductDetailsEntity.PRODUCT_CATEGORY);
            queryBuilder.where().in(ProductDetailsEntity.CARTON_NUMBER,cartonDetailsEntityList);
           List<ProductDetailsEntity> productDetailsEntities = queryBuilder.query();
           return productDetailsEntities;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<ProductDetailsEntity> getProductDetailsByCategory(String categoryName,List<CartonDetailsEntity> cartonDetailsEntities){
        try{
            QueryBuilder<ProductDetailsEntity, String> queryBuilder = productDao.queryBuilder();
            queryBuilder.where().in(ProductDetailsEntity.CARTON_NUMBER,cartonDetailsEntities).and().eq(ProductDetailsEntity.PRODUCT_CATEGORY,categoryName);
            List<ProductDetailsEntity> productDetailsEntities = queryBuilder.query();
            return productDetailsEntities;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public ProductDetailsEntity getProductDetails(String productDetailsGuid){
        try {
            QueryBuilder<ProductDetailsEntity, String> queryBuilder = productDao.queryBuilder();
            queryBuilder.where().eq(ProductDetailsEntity.PRODUCT_GUID, productDetailsGuid);
            return queryBuilder.queryForFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<ProductDetailsEntity> getOrderItem(String orderItemGuid){
        try {
            QueryBuilder<ProductDetailsEntity, String> queryBuilder = productDao.queryBuilder();
            queryBuilder.where().eq(ProductDetailsEntity.ORDER_ITEM_GUID, orderItemGuid);
            return queryBuilder.query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }



    public List<OrderEntity> getOrders(){
        try {
            return orderDao.queryBuilder().where().eq(OrderEntity.ORDER_STATUS, OrderStatus.ORDERED).query();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<OrderEntity> getPackingOrders(){
        try {
            return orderDao.queryBuilder().where().eq(OrderEntity.ORDER_STATUS, OrderStatus.PACKING).or().eq(OrderEntity.ORDER_STATUS, OrderStatus.PARTIAL_DELIVERED).query();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<DeliveryDetailsEntity> getDeliveryDetailsEntity(){
        try {
            return deliveryDetailsDao.queryForAll();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<DeliveryDetailsEntity> getDeliveryDetailsEntity(OrderEntity orderEntity){
        try {
            return deliveryDetailsDao.queryBuilder().where().eq(DeliveryDetailsEntity.ORDER_ID, orderEntity).query();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public DeliveryDetailsEntity getDeliveryDetailsEntity(String  deliveryUUID){
        try {
            return deliveryDetailsDao.queryBuilder().where().eq(DeliveryDetailsEntity.DELIVERY_UUID, deliveryUUID).queryForFirst();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public DeliveryDetailsEntity getDeliveryDetailsEntity(int  deliveryId){
        try {
            return deliveryDetailsDao.queryBuilder().where().eq(DeliveryDetailsEntity.DELIVERY_DETAILS_ID, deliveryId).queryForFirst();
            //return orderDao.queryBuilder().query();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createDeliveryDetailsEntity(DeliveryDetailsEntity deliveryDetailsEntity){
        try{
            deliveryDetailsDao.create(deliveryDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void updateDeliveryDetailsEntity(DeliveryDetailsEntity deliveryDetailsEntity){
        try{
            deliveryDetailsDao.update(deliveryDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public ClientDetailsEntity getClientDetailsEntity(OrderEntity orderEntity){
        try{
          List<ClientDetailsEntity>  clientDetailsEntities =  clientDetailsDao.queryForAll();
           return  clientDetailsDao.queryBuilder().where().eq(ClientDetailsEntity.ORDER_ID,orderEntity).queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public ClientDetailsEntity getClientDetailsEntity(String clientUUID){
        try{
            return  clientDetailsDao.queryBuilder().where().eq(ClientDetailsEntity.CLIENT_DETAILS_UUID,clientUUID).queryForFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public void createClientDetails(ClientDetailsEntity clientDetailsEntity){
        try{
            clientDetailsDao.create(clientDetailsEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
