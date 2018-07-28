package com.ordered.report.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ordered.report.enumeration.DeliveringType;
import com.ordered.report.enumeration.Status;

/**
 * Created by Nithish on 05/03/18.
 */
@DatabaseTable(tableName = "DeliveryDetails")
public class DeliveryDetailsEntity {

    public static final String DELIVERY_DETAILS_ID = "DeliveryDetailsId";
    public static final String DELIVERY_UUID = "DeliveryUUID";
    public static final String ORDER_ID = "OrderId";
    public static final String STATUS = "Status";
    public static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
    public static final String IS_SYNC = "IsSync";


    @DatabaseField(columnName = "DeliveryDetailsId", generatedId = true)
    private int deliveryDetailsId;
    @DatabaseField(columnName = "DeliveryUUID")
    private String deliveryUUID;

    @DatabaseField(columnName = "DeliveryID")
    private String deliveryId;

    @DatabaseField(columnName = "DeliveringType", dataType = DataType.ENUM_INTEGER)
    private DeliveringType deliveringType;
    @DatabaseField(columnName = "PlaceOfLoading")
    private String placeOfLoading;
    @DatabaseField(columnName = "PlaceOfDelivery")
    private String placeOfDelivery;
    @DatabaseField(columnName = "PortOfDischarge")
    private String portOfDischarge;

    @DatabaseField(columnName = "Status", dataType = DataType.ENUM_INTEGER)
    private Status status;


    @DatabaseField(columnName = "LastModifiedDateTime")
    private long lastModifiedDateTime;

    @DatabaseField(columnName = "IsSync")
    private boolean isSync;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public OrderEntity getOrderEntity(){
        return null;
    }


    public String getDeliveryUUID() {
        return deliveryUUID;
    }

    public void setDeliveryUUID(String deliveryUUID) {
        this.deliveryUUID = deliveryUUID;
    }

    public int getDeliveryDetailsId() {
        return deliveryDetailsId;
    }

    public void setDeliveryDetailsId(int deliveryDetailsId) {
        this.deliveryDetailsId = deliveryDetailsId;
    }

    public DeliveringType getDeliveringType() {
        return deliveringType;
    }

    public void setDeliveringType(DeliveringType deliveringType) {
        this.deliveringType = deliveringType;
    }

    public String getPlaceOfLoading() {
        return placeOfLoading;
    }

    public void setPlaceOfLoading(String placeOfLoading) {
        this.placeOfLoading = placeOfLoading;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }





    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    @Override
    public String toString() {
        return "DeliveryDetailsEntity{" +
                "deliveryDetailsId=" + deliveryDetailsId +
                ", deliveryUUID='" + deliveryUUID + '\'' +
                ", deliveryId='" + deliveryId + '\'' +
                ", deliveringType=" + deliveringType +
                ", placeOfLoading='" + placeOfLoading + '\'' +
                ", placeOfDelivery='" + placeOfDelivery + '\'' +
                ", portOfDischarge='" + portOfDischarge + '\'' +
                '}';
    }
}
