package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Admin on 2/14/2018.
 */
@DatabaseTable(tableName = "product")
public class ProductEntity {
    public static final String ID = "ProductID";
    public static final String CARTONITEM_ID = "ItemId";
    public static final String CARTON_TEM_GUID = "ItemGuid";
    public static final String CARTON_ITEM_NAME = "Name";
    public static final String PRODICT_ID= "Id";

    @DatabaseField(columnName = "ProductId", generatedId = true)
    int ProductId;
    @DatabaseField(columnName = "productGuid")
    String itemGuid;
    @DatabaseField(columnName = "ProductName")
    String productName;
    @DatabaseField(columnName = "CreatedBy")
    String createdBy;
    @DatabaseField(columnName = "CreatedTime")
    private long createdTime;
    @DatabaseField(columnName = "Id", foreign = true, foreignAutoRefresh = true)
    private OrderEntity orderEntity;

    public int getId() {
        return ProductId;
    }

    public void setId(int ProductId) {
        this.ProductId = ProductId;
    }

    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        createdBy = createdBy;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "ProductId=" + ProductId +
                ", itemGuid='" + itemGuid + '\'' +
                ", productName='" + productName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdTime=" + createdTime +
                ", orderEntity=" + orderEntity +
                '}';
    }
}
