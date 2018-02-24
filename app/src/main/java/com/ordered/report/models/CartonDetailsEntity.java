package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ordered.report.json.models.CartonDetailsJson;

/**
 * Created by Nithish on 24/02/18.
 */
@DatabaseTable(tableName = "CartonDetails")
public class CartonDetailsEntity {

    public static final String CARTON_GUID = "CartonGuid";
    public static final String ORDER_ENTITY = "orderEntity";

    @DatabaseField(columnName = "CartonId", generatedId = true)
    private int cartonId;
    @DatabaseField(columnName = "CartonGuid")
    private String cartonGuid;
    @DatabaseField(columnName = "CartonNumber")
    private String cartonNumber;
    @DatabaseField(columnName = "CreatedDateTime")
    private long createdDateTime;
    @DatabaseField(columnName = "LastModifiedTime")
    private long lastModifiedTime;

    @DatabaseField(columnName = "OrderId", foreign = true, foreignAutoRefresh = true)
    private OrderEntity orderEntity;

    @DatabaseField(columnName = "CreatedBy")
    private String createdBy;
    @DatabaseField(columnName = "LastModifiedBy")
    private String lastModifiedBy;


    public CartonDetailsEntity(){

    }


    public CartonDetailsEntity(CartonDetailsJson cartonDetailsJson){
this.cartonGuid = cartonDetailsJson.getCartonGuid();
this.cartonNumber = cartonDetailsJson.getCartonNumber();
this.createdDateTime = cartonDetailsJson.getCreatedDateTime();
this.lastModifiedTime = cartonDetailsJson.getLastModifiedTime();
this.createdBy = cartonDetailsJson.getCreatedBy();
this.lastModifiedBy = cartonDetailsJson.getLastModifiedBy();
    }

    public int getCartonId() {
        return cartonId;
    }

    public void setCartonId(int cartonId) {
        this.cartonId = cartonId;
    }

    public String getCartonGuid() {
        return cartonGuid;
    }

    public void setCartonGuid(String cartonGuid) {
        this.cartonGuid = cartonGuid;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String toString() {
        return "CartonDetailsEntity{" +
                "cartonId=" + cartonId +
                ", cartonGuid='" + cartonGuid + '\'' +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", orderEntity=" + orderEntity +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }
}
