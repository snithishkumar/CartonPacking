package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.ordered.report.json.models.ProductDetailsJson;
import com.ordered.report.view.models.OrderDetailsListViewModel;

import java.util.UUID;

/**
 * Created by Admin on 2/14/2018.
 */
@DatabaseTable(tableName = "ProductDetails")
public class ProductDetailsEntity {

    public static final String PRODUCT_GUID = "productGuid";
    public static final String ORDER_ENTITY = "orderEntity";
    public static final String CARTON_NUMBER = "cartonNumber";

    @DatabaseField(columnName = "ProductId", generatedId = true)
    private int productId;
    @DatabaseField(columnName = "productGuid")
    private String productGuid;
    @DatabaseField(columnName = "orderItemGuid")
    private String orderItemGuid;

    @DatabaseField(columnName = "ProductName")
    private String productName;
    @DatabaseField(columnName = "CreatedBy")
    private  String createdBy;

    @DatabaseField(columnName = "Id", foreign = true, foreignAutoRefresh = true)
    private OrderEntity orderEntity;

    @DatabaseField(columnName = "productCategory")
    private String productCategory;

    @DatabaseField(columnName = "colorStyle")
    private String colorStyle;

    @DatabaseField(columnName = "oneSize")
    private String oneSize;
    @DatabaseField(columnName = "xs")
    private String xs;
    @DatabaseField(columnName = "s")
    private String s;
    @DatabaseField(columnName = "m")
    private String m;
    @DatabaseField(columnName = "l")
    private String l;
    @DatabaseField(columnName = "xl")
    private String xl;
    @DatabaseField(columnName = "xxl")
    private String xxl;
    @DatabaseField(columnName = "xxxl")
    private String xxxl;
    @DatabaseField(columnName = "cartonNumber", foreign = true, foreignAutoRefresh = true)
    private CartonDetailsEntity cartonNumber;
    @DatabaseField(columnName = "createdDateTime")
    private long createdDateTime;
    @DatabaseField(columnName = "lastModifiedDateTime")
    private long lastModifiedDateTime;

    public ProductDetailsEntity(){

    }

    public ProductDetailsEntity(ProductDetailsJson productDetailsJson){
        this.productGuid = productDetailsJson.getProductGuid();
        this.productName = productDetailsJson.getProductStyle();
        this.orderItemGuid = productDetailsJson.getOrderItemGuid();
       this.productCategory = productDetailsJson.getProductCategory();
       this.colorStyle = productDetailsJson.getColorStyle();
       this.oneSize = productDetailsJson.getOneSize();
       this.xs = productDetailsJson.getXs();
       this.s = productDetailsJson.getS();
       this.m = productDetailsJson.getM();
       this.l = productDetailsJson.getL();
       this.xl = productDetailsJson.getXl();
       this.xxl = productDetailsJson.getXxl();
       this.xxxl = productDetailsJson.getXxxl();
       this.createdDateTime = productDetailsJson.getCreatedDateTime();
       this.lastModifiedDateTime = productDetailsJson.getLastModifiedDateTime();
    }


    public ProductDetailsEntity(OrderDetailsListViewModel orderDetailsListViewModel){
        this.productGuid = UUID.randomUUID().toString();
        this.productName = orderDetailsListViewModel.getOrderItemName();
        this.orderItemGuid = orderDetailsListViewModel.getOrderItemGuid();
        this.productCategory = orderDetailsListViewModel.getOrderItemCategory();
        this.colorStyle = orderDetailsListViewModel.getOrderItemColor();
        this.oneSize = orderDetailsListViewModel.getProductOneSize();
        this.xs = orderDetailsListViewModel.getProductXS();
        this.s = orderDetailsListViewModel.getProductS();
        this.m = orderDetailsListViewModel.getProductM();
        this.l = orderDetailsListViewModel.getProductL();
        this.xl = orderDetailsListViewModel.getProductXl();
        this.xxl = orderDetailsListViewModel.getProductXxl();
        this.xxxl = orderDetailsListViewModel.getProductXxxl();
        this.createdDateTime = System.currentTimeMillis();
        this.lastModifiedDateTime = createdDateTime;
    }


    public String getOrderItemGuid() {
        return orderItemGuid;
    }

    public void setOrderItemGuid(String orderItemGuid) {
        this.orderItemGuid = orderItemGuid;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductGuid() {
        return productGuid;
    }

    public void setProductGuid(String productGuid) {
        this.productGuid = productGuid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
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

    public CartonDetailsEntity getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(CartonDetailsEntity cartonNumber) {
        this.cartonNumber = cartonNumber;
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

    @Override
    public String toString() {
        return "ProductDetailsEntity{" +
                "productId=" + productId +
                ", productGuid='" + productGuid + '\'' +
                ", productName='" + productName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", orderEntity=" + orderEntity +
                ", productCategory='" + productCategory + '\'' +
                ", colorStyle='" + colorStyle + '\'' +
                ", oneSize='" + oneSize + '\'' +
                ", xs='" + xs + '\'' +
                ", s='" + s + '\'' +
                ", m='" + m + '\'' +
                ", l='" + l + '\'' +
                ", xl='" + xl + '\'' +
                ", xxl='" + xxl + '\'' +
                ", xxxl='" + xxxl + '\'' +
                ", cartonNumber='" + cartonNumber + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", lastModifiedDateTime=" + lastModifiedDateTime +
                '}';
    }
}
