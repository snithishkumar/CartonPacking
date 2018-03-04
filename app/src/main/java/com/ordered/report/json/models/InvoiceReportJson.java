package com.ordered.report.json.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 05/03/18.
 */

public class InvoiceReportJson {
    private int totalQuantity = 0;
    private double totalAmount = 0;
    private List<InvoiceReportCategoryDetailsJson> invoiceReportCategoryDetailsJsons = new ArrayList<>();

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<InvoiceReportCategoryDetailsJson> getInvoiceReportCategoryDetailsJsons() {
        return invoiceReportCategoryDetailsJsons;
    }

    public void setInvoiceReportCategoryDetailsJsons(List<InvoiceReportCategoryDetailsJson> invoiceReportCategoryDetailsJsons) {
        this.invoiceReportCategoryDetailsJsons = invoiceReportCategoryDetailsJsons;
    }

    @Override
    public String toString() {
        return "InvoiceReportJson{" +
                "totalQuantity=" + totalQuantity +
                ", totalAmount=" + totalAmount +
                ", invoiceReportCategoryDetailsJsons=" + invoiceReportCategoryDetailsJsons +
                '}';
    }
}
