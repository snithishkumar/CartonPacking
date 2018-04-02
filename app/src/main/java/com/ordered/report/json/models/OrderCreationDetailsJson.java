package com.ordered.report.json.models;

import java.util.Objects;

/**
 * Created by Nithish on 11/02/18.
 */

public class OrderCreationDetailsJson {
    private String productGroup;
    private String productCategory;
    private String productStyle;
    private String productStyleGuid;
    private String orderItemGuid;
    private String colorStyle;
    private String oneSize;
    private String xs;
    private String s;
    private String m;
    private String l;
    private String xl;
    private String xxl;
    private String xxxl;
    private long createdDateTime;
    private long lastModifiedDateTime;
    private String cartonNumber;
    private double unitPrice;
    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }



    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getProductStyleGuid() {
        return productStyleGuid;
    }

    public void setProductStyleGuid(String productStyleGuid) {
        this.productStyleGuid = productStyleGuid;
    }

    public String getOrderItemGuid() {
        return orderItemGuid;
    }

    public void setOrderItemGuid(String orderItemGuid) {
        this.orderItemGuid = orderItemGuid;
    }

    public String getColorStyle() {
        return colorStyle;
    }

    public void setColorStyle(String colorStyle) {
        this.colorStyle = colorStyle;
    }

    public String getOneSize() {
        return oneSize;
    }

    public void setOneSize(String oneSize) {
        this.oneSize = oneSize;
    }

    public String getXs() {
        return xs;
    }

    public void setXs(String xs) {
        this.xs = xs;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getXxl() {
        return xxl;
    }

    public void setXxl(String xxl) {
        this.xxl = xxl;
    }

    public String getXxxl() {
        return xxxl;
    }

    public void setXxxl(String xxxl) {
        this.xxxl = xxxl;
    }



    public long getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(long createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderCreationDetailsJson{" +
                "productGroup='" + productGroup + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productStyle='" + productStyle + '\'' +
                ", productStyleGuid='" + productStyleGuid + '\'' +
                ", orderItemGuid='" + orderItemGuid + '\'' +
                ", colorStyle='" + colorStyle + '\'' +
                ", oneSize='" + oneSize + '\'' +
                ", xs='" + xs + '\'' +
                ", s='" + s + '\'' +
                ", m='" + m + '\'' +
                ", l='" + l + '\'' +
                ", xl='" + xl + '\'' +
                ", xxl='" + xxl + '\'' +
                ", xxxl='" + xxxl + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDateTime=" + lastModifiedDateTime +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreationDetailsJson that = (OrderCreationDetailsJson) o;
        return Objects.equals(orderItemGuid, that.orderItemGuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemGuid);
    }
}
