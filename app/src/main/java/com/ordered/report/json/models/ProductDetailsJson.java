package com.ordered.report.json.models;

/**
 * Created by Nithish on 14/02/18.
 */

public class ProductDetailsJson {

    private String productName;
    private String productGuid;
    private String productGroup;
    private String productGroupGuid;
    private String oneSize;
    private String xs;
    private String s;
    private String m;
    private String l;
    private String xl;
    private String xxl;
    private String xxxl;
    private int cartonNumber;
    private long createdDateTime;
    private long lastModifiedDateTime;


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

    public int getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(int cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductGroupGuid() {
        return productGroupGuid;
    }

    public void setProductGroupGuid(String productGroupGuid) {
        this.productGroupGuid = productGroupGuid;
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
        return "ProductDetailsJson{" +
                "productName='" + productName + '\'' +
                ", productGuid='" + productGuid + '\'' +
                ", productGroup='" + productGroup + '\'' +
                ", productGroupGuid='" + productGroupGuid + '\'' +
                ", oneSize='" + oneSize + '\'' +
                ", xs='" + xs + '\'' +
                ", m='" + m + '\'' +
                ", l='" + l + '\'' +
                ", xl='" + xl + '\'' +
                ", xxl='" + xxl + '\'' +
                ", xxxl='" + xxxl + '\'' +
                '}';
    }
}
