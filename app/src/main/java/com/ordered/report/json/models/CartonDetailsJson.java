package com.ordered.report.json.models;

import com.ordered.report.models.CartonDetailsEntity;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 24/02/18.
 */

public class CartonDetailsJson {

    private String cartonGuid;
    private String cartonNumber;
    private long createdDateTime;
    private long lastModifiedTime;
    private String createdBy;
    private String totalWeight;
    private String lastModifiedBy;
    private int noOfProducts;
    private List<ProductDetailsJson> productDetailsJsonList = new ArrayList<>();
    List<OrderDetailsListViewModel>  orderDetailsListViewModels = new ArrayList<>();

    public CartonDetailsJson(){

    }

    public CartonDetailsJson(CartonDetailsEntity cartonDetailsEntity){
        this.cartonGuid = cartonDetailsEntity.getCartonGuid();
        this.cartonNumber = cartonDetailsEntity.getCartonNumber();
        this.lastModifiedTime = cartonDetailsEntity.getLastModifiedTime();
        this.createdDateTime = cartonDetailsEntity.getCreatedDateTime();
        this.createdBy = cartonDetailsEntity.getCreatedBy();
        this.lastModifiedBy = cartonDetailsEntity.getLastModifiedBy();
        this.totalWeight = cartonDetailsEntity.getTotalWeight();
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getNoOfProducts() {
        return noOfProducts;
    }

    public void setNoOfProducts(int noOfProducts) {
        this.noOfProducts = noOfProducts;
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

    public List<ProductDetailsJson> getProductDetailsJsonList() {
        return productDetailsJsonList;
    }

    public void setProductDetailsJsonList(List<ProductDetailsJson> productDetailsJsonList) {
        this.productDetailsJsonList = productDetailsJsonList;
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

    public List<OrderDetailsListViewModel> getOrderDetailsListViewModels() {
        return orderDetailsListViewModels;
    }

    public void setOrderDetailsListViewModels(List<OrderDetailsListViewModel> orderDetailsListViewModels) {
        this.orderDetailsListViewModels = orderDetailsListViewModels;
    }

    @Override
    public String toString() {
        return "CartonDetailsJson{" +
                "cartonGuid='" + cartonGuid + '\'' +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", productDetailsJsonList=" + productDetailsJsonList +
                '}';
    }
}
