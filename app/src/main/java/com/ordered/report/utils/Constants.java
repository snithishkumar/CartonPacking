package com.ordered.report.utils;

/**
 * Created by Admin on 1/1/2018.
 */

public class Constants {
    public static final int SUCCESS = 200;
    public static final int INVALID_USERNAME = 401;
    public static final int NO_INTERNET = 303;
    public static final int INTERNAL_SERVER_ERROR=500;
    public static final String NO_OF_COTTON="Cotton";
    public static final String ORDER="ORDER";

    public static final String ORDER_DETAILS_POS="ORDERPOS";

    public static final String VIEW_ORDER = "order";
    public static final String VIEW_PACKING = "packing";
    public static final String VIEW_DELIVERY = "delivery";

    public static final String VIEW_ADD_SHIPPING = "addShipping";
    public static final String VIEW_CARTON_LIST = "cartonList";

    private static String loginUser;

    public static String getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(String loginUser) {
        Constants.loginUser = loginUser;
    }
}
