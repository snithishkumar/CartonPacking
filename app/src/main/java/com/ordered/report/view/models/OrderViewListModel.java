package com.ordered.report.view.models;

import com.ordered.report.models.OrderEntity;

public class OrderViewListModel {

    private String orderGuid;
    private String orderId;
    private String orderStatus;
    private String productCount = "0";
    private String cartonCount = "0";
    private String deliveryCount = "0";

    private String orderBy;
    private String orderFrom;
    private String orderDateTime;

    public OrderViewListModel(OrderEntity orderEntity){
        this.orderGuid = orderEntity.getOrderGuid();
        this.orderId = orderEntity.getOrderId();
        this.orderStatus = orderEntity.getOrderStatus().toString();
        this.orderFrom = orderEntity.getClientDetailsEntity().getConsigneeName();
        this.orderBy = orderEntity.getClientDetailsEntity().getConsigneeAuthorityPerson() != null ? orderEntity.getClientDetailsEntity().getConsigneeAuthorityPerson() : "Not Available";
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public void setOrderGuid(String orderGuid) {
        this.orderGuid = orderGuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(String cartonCount) {
        this.cartonCount = cartonCount;
    }

    public String getDeliveryCount() {
        return deliveryCount;
    }

    public void setDeliveryCount(String deliveryCount) {
        this.deliveryCount = deliveryCount;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    @Override
    public String toString() {
        return "OrderViewListModel{" +
                "orderGuid='" + orderGuid + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", productCount='" + productCount + '\'' +
                ", cartonCount='" + cartonCount + '\'' +
                ", deliveryCount='" + deliveryCount + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", orderFrom='" + orderFrom + '\'' +
                ", orderDateTime='" + orderDateTime + '\'' +
                '}';
    }
}
