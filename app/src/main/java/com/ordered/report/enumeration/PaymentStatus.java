package com.ordered.report.enumeration;

/**
 * Created by Admin on 1/4/2018.
 */

public enum PaymentStatus {
    NOT_PAIED(1, "NOT_PAIED"), PAIED(2, "PAIED"), PARTIAL_PAIED(3, "PARTIAL_PAIED");
    private int paymentStatusId;
    private String paymentStatusName;

    private PaymentStatus(int paymentStatusId, String paymentStatusName) {
        this.paymentStatusId = paymentStatusId;
        this.paymentStatusName = paymentStatusName;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }
}

