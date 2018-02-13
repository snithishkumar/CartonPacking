package com.ordered.report.json.models;

/**
 * Created by Admin on 1/2/2018.
 */

public class CartonItemModel {
    int id;
    String itemGuid;
    String name;
    String size;
    int quantity;
    String category;
    int rate;
    double totalCost;
    String cartonNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemGuid() {
        return itemGuid;
    }

    public void setItemGuid(String itemGuid) {
        this.itemGuid = itemGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getCartonNumber() {
        return cartonNumber;
    }

    public void setCartonNumber(String cartonNumber) {
        this.cartonNumber = cartonNumber;
    }

    @Override
    public String toString() {
        return "CartonItemModel{" +
                "id=" + id +
                ", itemGuid='" + itemGuid + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", category='" + category + '\'' +
                ", rate=" + rate +
                ", totalCost=" + totalCost +
                ", cartonNumber='" + cartonNumber + '\'' +
                '}';
    }
}
