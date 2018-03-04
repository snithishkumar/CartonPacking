package com.ordered.report.view.models;

import com.ordered.report.json.models.OrderCreationDetailsJson;
import com.ordered.report.json.models.ProductDetailsJson;
import com.ordered.report.models.ProductDetailsEntity;

/**
 * Created by Nithish on 24/02/18.
 */

public class OrderDetailsListViewModel {

    private String orderGuid;
    private String productGuid;
    private String orderItemName;
    private String orderItemGroup;
    private String orderItemGuid;
    private String orderItemCategory;
    private String orderItemColor;
    private String cartonNumber;

    private String orderItemOneSize;
    private String orderItemXS;
    private String orderItemS;
    private String orderItemM;
    private String orderItemL;
    private String orderItemXl;
    private String orderItemXxl;
    private String orderItemXxxl;
    private long orderItemCreatedDateTime;
    private long orderItemLastModifiedDateTime;


    private String productOneSize = "0";
    private String productXS = "0";
    private String productS = "0";
    private String productM = "0";
    private String productL = "0";
    private String productXl = "0";
    private String productXxl = "0";
    private String productXxxl = "0";
    private long productCreatedDateTime;

    private boolean isEdited = false;


    public void loadOrderItemsDetails(ProductDetailsEntity productDetailsJson){
        this.productGuid = productDetailsJson.getProductGuid();
        this.orderItemName = productDetailsJson.getProductName();
        this.orderItemGroup = productDetailsJson.getProductGroup();
        this.orderItemColor = productDetailsJson.getColorStyle();
        this.productOneSize = productDetailsJson.getOneSize();
        this.productXS = productDetailsJson.getXs();
        this.productS = productDetailsJson.getS();
        this.productM = productDetailsJson.getM();
        this.productL = productDetailsJson.getL();
        this.productXl = productDetailsJson.getXl();
        this.productXxl = productDetailsJson.getXxl();
        this.productXxxl = productDetailsJson.getXxxl();
        this.orderItemCreatedDateTime = productDetailsJson.getCreatedDateTime();
        this.orderItemLastModifiedDateTime = productDetailsJson.getLastModifiedDateTime();
        this.orderItemGuid = productDetailsJson.getOrderItemGuid();
        this.orderItemCategory = productDetailsJson.getProductCategory();



    }


    public void loadOrderItemsDetails(OrderCreationDetailsJson orderDetailsJson) {
        this.orderItemName = orderDetailsJson.getProductStyle();
        this.orderItemGroup = orderDetailsJson.getProductGroup();
        this.orderItemColor = orderDetailsJson.getColorStyle();
        this.orderItemOneSize = orderDetailsJson.getOneSize();
        this.orderItemXS = orderDetailsJson.getXs();
        this.orderItemS = orderDetailsJson.getS();
        this.orderItemM = orderDetailsJson.getM();
        this.orderItemL = orderDetailsJson.getL();
        this.orderItemXl = orderDetailsJson.getXl();
        this.orderItemXxl = orderDetailsJson.getXxl();
        this.orderItemXxxl = orderDetailsJson.getXxxl();
        this.orderItemCreatedDateTime = orderDetailsJson.getCreatedDateTime();
        this.orderItemLastModifiedDateTime = orderDetailsJson.getLastModifiedDateTime();
        this.orderItemGuid = orderDetailsJson.getOrderItemGuid();
        this.orderItemCategory = orderDetailsJson.getProductCategory();
    }


    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public String getOrderItemGuid() {
        return orderItemGuid;
    }

    public void setOrderItemGuid(String orderItemGuid) {
        this.orderItemGuid = orderItemGuid;
    }

    public String getOrderItemCategory() {
        return orderItemCategory;
    }

    public void setOrderItemCategory(String orderItemCategory) {
        this.orderItemCategory = orderItemCategory;
    }

    public String getOrderGuid() {
        return orderGuid;
    }

    public void setOrderGuid(String orderGuid) {
        this.orderGuid = orderGuid;
    }

    public String getOrderItemName() {
        return orderItemName;
    }

    public void setOrderItemName(String orderItemName) {
        this.orderItemName = orderItemName;
    }

    public String getOrderItemGroup() {
        return orderItemGroup;
    }

    public void setOrderItemGroup(String orderItemGroup) {
        this.orderItemGroup = orderItemGroup;
    }

    public String getOrderItemColor() {
        return orderItemColor;
    }

    public void setOrderItemColor(String orderItemColor) {
        this.orderItemColor = orderItemColor;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public String getOrderItemOneSize() {
        return orderItemOneSize;
    }

    public void setOrderItemOneSize(String orderItemOneSize) {
        this.orderItemOneSize = orderItemOneSize;
    }

    public String getOrderItemXS() {
        return orderItemXS;
    }

    public void setOrderItemXS(String orderItemXS) {
        this.orderItemXS = orderItemXS;
    }

    public String getOrderItemS() {
        return orderItemS;
    }

    public void setOrderItemS(String orderItemS) {
        this.orderItemS = orderItemS;
    }

    public String getOrderItemM() {
        return orderItemM;
    }

    public void setOrderItemM(String orderItemM) {
        this.orderItemM = orderItemM;
    }

    public String getOrderItemL() {
        return orderItemL;
    }

    public void setOrderItemL(String orderItemL) {
        this.orderItemL = orderItemL;
    }

    public String getOrderItemXl() {
        return orderItemXl;
    }

    public void setOrderItemXl(String orderItemXl) {
        this.orderItemXl = orderItemXl;
    }

    public String getOrderItemXxl() {
        return orderItemXxl;
    }

    public void setOrderItemXxl(String orderItemXxl) {
        this.orderItemXxl = orderItemXxl;
    }

    public String getOrderItemXxxl() {
        return orderItemXxxl;
    }

    public void setOrderItemXxxl(String orderItemXxxl) {
        this.orderItemXxxl = orderItemXxxl;
    }

    public long getOrderItemCreatedDateTime() {
        return orderItemCreatedDateTime;
    }

    public void setOrderItemCreatedDateTime(long orderItemCreatedDateTime) {
        this.orderItemCreatedDateTime = orderItemCreatedDateTime;
    }

    public long getOrderItemLastModifiedDateTime() {
        return orderItemLastModifiedDateTime;
    }

    public void setOrderItemLastModifiedDateTime(long orderItemLastModifiedDateTime) {
        this.orderItemLastModifiedDateTime = orderItemLastModifiedDateTime;
    }

    public String getProductOneSize() {
        return productOneSize;
    }

    public void setProductOneSize(String productOneSize) {
        this.productOneSize = productOneSize;
    }

    public String getProductXS() {
        return productXS;
    }

    public void setProductXS(String productXS) {
        this.productXS = productXS;
    }

    public String getProductS() {
        return productS;
    }

    public void setProductS(String productS) {
        this.productS = productS;
    }

    public String getProductM() {
        return productM;
    }

    public void setProductM(String productM) {
        this.productM = productM;
    }

    public String getProductL() {
        return productL;
    }

    public void setProductL(String productL) {
        this.productL = productL;
    }

    public String getProductXl() {
        return productXl;
    }

    public void setProductXl(String productXl) {
        this.productXl = productXl;
    }

    public String getProductXxl() {
        return productXxl;
    }

    public void setProductXxl(String productXxl) {
        this.productXxl = productXxl;
    }

    public String getProductXxxl() {
        return productXxxl;
    }

    public void setProductXxxl(String productXxxl) {
        this.productXxxl = productXxxl;
    }

    public long getProductCreatedDateTime() {
        return productCreatedDateTime;
    }

    public void setProductCreatedDateTime(long productCreatedDateTime) {
        this.productCreatedDateTime = productCreatedDateTime;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    @Override
    public String toString() {
        return "OrderDetailsListViewModel{" +
                "orderGuid='" + orderGuid + '\'' +
                ", orderItemName='" + orderItemName + '\'' +
                ", orderItemGroup='" + orderItemGroup + '\'' +
                ", orderItemColor='" + orderItemColor + '\'' +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", orderItemOneSize='" + orderItemOneSize + '\'' +
                ", orderItemXS='" + orderItemXS + '\'' +
                ", orderItemS='" + orderItemS + '\'' +
                ", orderItemM='" + orderItemM + '\'' +
                ", orderItemL='" + orderItemL + '\'' +
                ", orderItemXl='" + orderItemXl + '\'' +
                ", orderItemXxl='" + orderItemXxl + '\'' +
                ", orderItemXxxl='" + orderItemXxxl + '\'' +
                ", orderItemCreatedDateTime=" + orderItemCreatedDateTime +
                ", orderItemLastModifiedDateTime=" + orderItemLastModifiedDateTime +
                ", productOneSize='" + productOneSize + '\'' +
                ", productXS='" + productXS + '\'' +
                ", productS='" + productS + '\'' +
                ", productM='" + productM + '\'' +
                ", productL='" + productL + '\'' +
                ", productXl='" + productXl + '\'' +
                ", productXxl='" + productXxl + '\'' +
                ", productXxxl='" + productXxxl + '\'' +
                ", productCreatedDateTime=" + productCreatedDateTime +
                '}';
    }
}
