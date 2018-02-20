package com.ordered.report.json.models;

/**
 * Created by Nithish on 11/02/18.
 */

public class OrderCreationDetailsJson {
    private String productGroupGuid;
    private String productGroup;
    private String productCategoryGuid;
    private String productCategory;
    private String productStyleGuid;
    private String productStyle;
    private String colorStyleGuid;
    private String colorStyle;
    private String oneSize;
    private String xs;
    private String m;
    private String l;
    private String xl;
    private String xxl;
    private String xxxl;
    private String cartonNumber;

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public String getProductGroupGuid() {
        return productGroupGuid;
    }

    public void setProductGroupGuid(String productGroupGuid) {
        this.productGroupGuid = productGroupGuid;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductCategoryGuid() {
        return productCategoryGuid;
    }

    public void setProductCategoryGuid(String productCategoryGuid) {
        this.productCategoryGuid = productCategoryGuid;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductStyleGuid() {
        return productStyleGuid;
    }

    public void setProductStyleGuid(String productStyleGuid) {
        this.productStyleGuid = productStyleGuid;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getColorStyleGuid() {
        return colorStyleGuid;
    }

    public void setColorStyleGuid(String colorStyleGuid) {
        this.colorStyleGuid = colorStyleGuid;
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

    @Override
    public String toString() {
        return "OrderCreationDetailsJson{" +
                "productGroupGuid='" + productGroupGuid + '\'' +
                ", productGroup='" + productGroup + '\'' +
                ", productCategoryGuid='" + productCategoryGuid + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productStyleGuid='" + productStyleGuid + '\'' +
                ", productStyle='" + productStyle + '\'' +
                ", colorStyleGuid='" + colorStyleGuid + '\'' +
                ", colorStyle='" + colorStyle + '\'' +
                ", oneSize='" + oneSize + '\'' +
                ", xs='" + xs + '\'' +
                ", m='" + m + '\'' +
                ", l='" + l + '\'' +
                ", xl='" + xl + '\'' +
                ", xxl='" + xxl + '\'' +
                ", xxxl='" + xxxl + '\'' +
                ", cartonNumber='" + cartonNumber + '\'' +
                '}';
    }
}
