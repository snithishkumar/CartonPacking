package com.ordered.report.enumeration;

/**
 * Created by Nithish on 11/02/18.
 */

public enum OrderStatus {
    ORDERED(0),PACKING(1),PARTIAL_DELIVERED(2),DELIVERED(3);
private int orderStatusId;
    OrderStatus(int orderStatusId){
        this.orderStatusId = orderStatusId;
    }

    public int getOrderStatusId(){
        return orderStatusId;
    }
}
