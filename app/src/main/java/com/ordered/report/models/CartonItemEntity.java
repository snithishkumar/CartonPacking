package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Admin on 1/2/2018.
 */
@DatabaseTable(tableName = "CartonItem")
public class CartonItemEntity {
    public static final String ID = "ID";
    public static final String CARTONITEM_ID = "ItemId";
    public static final String CARTON_TEM_GUID = "ItemGuid";
    public static final String CARTON_ITEM_NAME = "Name";
    public static final String SIZE = "Size";
    public static final String QUANTITY = "Quantity";
    public static final String CATEGORY = "Category";
    public static final String RATE = "Rate";
    public static final String TOTAL_COST = "TotalCost";
    public static final String CARTON_NUMBER = "CartonNumber";
    @DatabaseField(columnName = "Id", generatedId = true)
    int id;
    @DatabaseField(columnName = "ItemGuid")
    String itemGuid;
    @DatabaseField(columnName = "Name")
    String name;
    @DatabaseField(columnName = "Size")
    String size;
    @DatabaseField(columnName = "Quantity")
    int quantity;
    @DatabaseField(columnName = "Category")
    String category;
    @DatabaseField(columnName = "Rate")
    int rate;
    @DatabaseField(columnName = "TotalCost")
    double totalCost;
    @DatabaseField(columnName = "CartonNumber")
    String cartonNumber;
    @DatabaseField(columnName = "OrderId", foreign = true, foreignAutoRefresh = true)
    private OrderEntity orderEntity;


    public CartonItemEntity() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }



    @Override
    public String toString() {
        return "CartonItemEntity{" +
                "id=" + id +
                ", itemGuid='" + itemGuid + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", rate=" + rate +
                ", totalCost=" + totalCost +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", orderEntity=" + orderEntity +
                '}';
    }
}
