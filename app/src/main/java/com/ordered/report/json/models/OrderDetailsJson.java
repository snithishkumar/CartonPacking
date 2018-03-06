package com.ordered.report.json.models;

import com.ordered.report.enumeration.OrderStatus;
import com.ordered.report.enumeration.PaymentStatus;
import com.ordered.report.models.ClientDetailsEntity;
import com.ordered.report.models.DeliveryDetailsEntity;
import com.ordered.report.models.OrderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 11/02/18.
 */

public class OrderDetailsJson {
    private String orderId;
    private String orderGuid;
    private String clientName;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private long orderedDate;
    private long lastModifiedDate;
    private long serverTime;
    private boolean isDeleted;
    private List<OrderCreationDetailsJson> orderedItems = new ArrayList<>();
    private List<CartonDetailsJson> productDetails = new ArrayList<>();
    private String cartonCounts;
    private String createdBy;
    private DeliveryDetailsEntity deliveryDetails;
    private ClientDetailsEntity clientDetails;

    public OrderDetailsJson(){

    }

    public OrderDetailsJson(OrderEntity orderEntity){
this.orderId = orderEntity.getOrderId();
this.orderGuid = orderEntity.getOrderGuid();
this.clientName = orderEntity.getClientName();
//this.orderStatus = orderEntity.getOrderType();
        this.paymentStatus = orderEntity.getPaymentStatus();
        this.orderedDate = orderEntity.getOrderedDate();
        this.lastModifiedDate= orderEntity.getLastModifiedDate();
        this.serverTime = orderEntity.getServerTime();
        this.cartonCounts = orderEntity.getCartonCounts();
        this.createdBy = orderEntity.getCreatedBy();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCartonCounts() {
        return cartonCounts;
    }

    public void setCartonCounts(String cartonCounts) {
        this.cartonCounts = cartonCounts;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public void setOrderGuid(String orderGuid) {
        this.orderGuid = orderGuid;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(long orderedDate) {
        this.orderedDate = orderedDate;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getServerTime() {
        return serverTime;
    }

    public void setServerTime(long serverTime) {
        this.serverTime = serverTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<OrderCreationDetailsJson> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderCreationDetailsJson> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public List<CartonDetailsJson> getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(List<CartonDetailsJson> productDetails) {
        this.productDetails = productDetails;
    }

    public DeliveryDetailsEntity getDeliveryDetails() {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetailsEntity deliveryDetails) {
        this.deliveryDetails = deliveryDetails;
    }

    public ClientDetailsEntity getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetailsEntity clientDetails) {
        this.clientDetails = clientDetails;
    }

    @Override
    public String toString() {
        return "OrderDetailsJson{" +
                "orderId='" + orderId + '\'' +
                ", orderGuid='" + orderGuid + '\'' +
                ", clientName='" + clientName + '\'' +
                ", orderStatus=" + orderStatus +
                ", paymentStatus=" + paymentStatus +
                ", orderedDate=" + orderedDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", serverTime=" + serverTime +
                ", isDeleted=" + isDeleted +
                ", orderedItems=" + orderedItems +
                ", productDetails=" + productDetails +
                ", cartonCounts='" + cartonCounts + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", deliveryDetails=" + deliveryDetails +
                ", clientDetails=" + clientDetails +
                '}';
    }
}
