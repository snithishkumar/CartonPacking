package com.ordered.report.json.models;

import com.ordered.report.models.DeliveryDetailsEntity;

import java.util.ArrayList;
import java.util.List;

public class ServerSyncModel {
    private List<OrderDetailsJson> orderDetailsJsonList = new ArrayList<>();
    private List<DeliveryDetailsEntity> deliveryDetailsEntities = new ArrayList<>();

    private List<ProcessedDetails> orderGuids = new ArrayList<>();
    private List<ProcessedDetails> deliveryGuids = new ArrayList<>();

    public List<ProcessedDetails> getOrderGuids() {
        return orderGuids;
    }

    public void setOrderGuids(List<ProcessedDetails> orderGuids) {
        this.orderGuids = orderGuids;
    }

    public List<ProcessedDetails> getDeliveryGuids() {
        return deliveryGuids;
    }

    public void setDeliveryGuids(List<ProcessedDetails> deliveryGuids) {
        this.deliveryGuids = deliveryGuids;
    }

    public List<OrderDetailsJson> getOrderDetailsJsonList() {
        return orderDetailsJsonList;
    }

    public void setOrderDetailsJsonList(List<OrderDetailsJson> orderDetailsJsonList) {
        this.orderDetailsJsonList = orderDetailsJsonList;
    }

    public List<DeliveryDetailsEntity> getDeliveryDetailsEntities() {
        return deliveryDetailsEntities;
    }

    public void setDeliveryDetailsEntities(List<DeliveryDetailsEntity> deliveryDetailsEntities) {
        this.deliveryDetailsEntities = deliveryDetailsEntities;
    }

    @Override
    public String toString() {
        return "ServerSyncModel{" +
                "orderDetailsJsonList=" + orderDetailsJsonList +
                ", deliveryDetailsEntities=" + deliveryDetailsEntities +
                ", orderGuids=" + orderGuids +
                ", deliveryGuids=" + deliveryGuids +
                '}';
    }
}
