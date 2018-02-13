package com.ordered.report.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/4/2018.
 */

public enum OrderType {
    ORDERED(1, "ORDERED"), PACKING(2, "PACKING"), DELIVERED(3, "DELIVERED");
    private int orderId;
    private String orderName;

    private OrderType(int orderId, String orderName) {
        this.orderId = orderId;
        this.orderName = orderName;
    }

    public static List<String> getDailyLogTypesAsString() {
        OrderType[] dailyLogTypes = OrderType.values();
        List<String> values = new ArrayList<>(dailyLogTypes.length);
        for (int i = 0; i < dailyLogTypes.length; i++) {
            values.add(dailyLogTypes[i].getOrderName());
        }
        return values;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getOrderName() {
        return orderName;
    }

}
