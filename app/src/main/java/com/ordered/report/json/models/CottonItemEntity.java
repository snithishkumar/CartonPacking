package com.ordered.report.json.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Admin on 1/26/2017.
 */
public class CottonItemEntity {

    private int cottonBookItemId;
    private String cottonBookItemGuid;
    private String productStyle;
    private String cotonBookItemColor;
    private int size_xs;
    private int Size_s;
    private int size_m;
    private int size_l;
    private int size_xl;
    private int size_xxl;
    private int size_xxxl;
    private long netWeight;
    private long grossWeight;
    private String cottonBookListGuid;
    private String cottonBookGuid;
    private long createdDateAndTime;
    private int quantity;
    private long rate=1;
    private long amount;
    private String productStyleCategory;
    private int cartonNumber;
    private int s_no;
    public int getCottonBookItemId() {
        return cottonBookItemId;
    }

    public void setCottonBookItemId(int cottonBookItemId) {
        this.cottonBookItemId = cottonBookItemId;
    }

    public String getCottonBookItemGuid() {
        return cottonBookItemGuid;
    }

    public void setCottonBookItemGuid(String cottonBookItemGuid) {
        this.cottonBookItemGuid = cottonBookItemGuid;
    }

    public String getProductStyle() {
        return productStyle;
    }

    public void setProductStyle(String productStyle) {
        this.productStyle = productStyle;
    }

    public String getCotonBookItemColor() {
        return cotonBookItemColor;
    }

    public void setCotonBookItemColor(String cotonBookItemColor) {
        this.cotonBookItemColor = cotonBookItemColor;
    }

    public int getSize_xs() {
        return size_xs;
    }

    public void setSize_xs(int size_xs) {
        this.size_xs = size_xs;
    }

    public int getSize_s() {
        return Size_s;
    }

    public void setSize_s(int size_s) {
        Size_s = size_s;
    }

    public int getSize_m() {
        return size_m;
    }

    public void setSize_m(int size_m) {
        this.size_m = size_m;
    }

    public int getSize_l() {
        return size_l;
    }

    public void setSize_l(int size_l) {
        this.size_l = size_l;
    }

    public int getSize_xl() {
        return size_xl;
    }

    public void setSize_xl(int size_xl) {
        this.size_xl = size_xl;
    }

    public int getSize_xxl() {
        return size_xxl;
    }

    public void setSize_xxl(int size_xxl) {
        this.size_xxl = size_xxl;
    }

    public int getSize_xxxl() {
        return size_xxxl;
    }

    public void setSize_xxxl(int size_xxxl) {
        this.size_xxxl = size_xxxl;
    }

    public long getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(long netWeight) {
        this.netWeight = netWeight;
    }

    public long getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(long grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getCottonBookListGuid() {
        return cottonBookListGuid;
    }

    public void setCottonBookListGuid(String cottonBookListGuid) {
        this.cottonBookListGuid = cottonBookListGuid;
    }

    public String getCottonBookGuid() {
        return cottonBookGuid;
    }

    public void setCottonBookGuid(String cottonBookGuid) {
        this.cottonBookGuid = cottonBookGuid;
    }

    public long getCreatedDateAndTime() {
        return createdDateAndTime;
    }

    public void setCreatedDateAndTime(long createdDateAndTime) {
        this.createdDateAndTime = createdDateAndTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getProductStyleCategory() {
        return productStyleCategory;
    }

    public void setProductStyleCategory(String productStyleCategory) {
        this.productStyleCategory = productStyleCategory;
    }

    public int getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(int cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    public int getS_no() {
        return s_no;
    }

    public void setS_no(int s_no) {
        this.s_no = s_no;
    }

    @Override
    public String toString() {
        return "CottonItemEntity{" +
                "cottonBookItemId=" + cottonBookItemId +
                ", cottonBookItemGuid='" + cottonBookItemGuid + '\'' +
                ", productStyle='" + productStyle + '\'' +
                ", cotonBookItemColor='" + cotonBookItemColor + '\'' +
                ", size_xs=" + size_xs +
                ", Size_s=" + Size_s +
                ", size_m=" + size_m +
                ", size_l=" + size_l +
                ", size_xl=" + size_xl +
                ", size_xxl=" + size_xxl +
                ", size_xxxl=" + size_xxxl +
                ", netWeight='" + netWeight + '\'' +
                ", grossWeight='" + grossWeight + '\'' +
                ", cottonBookListGuid='" + cottonBookListGuid + '\'' +
                ", cottonBookGuid='" + cottonBookGuid + '\'' +
                ", createdDateAndTime=" + createdDateAndTime +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", amount=" + amount +
                ", productStyleCategory='" + productStyleCategory + '\'' +
                ", cartonNumber=" + cartonNumber +
                ", s_no=" + s_no +
                '}';
    }
}
