package com.ordered.report.json.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithish on 05/03/18.
 */

public class InvoiceReportCategoryDetailsJson {

    private String categoryName;
    private List<InvoiceReportOrderDetailsJson> invoiceReportOrder = new ArrayList<>();

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<InvoiceReportOrderDetailsJson> getInvoiceReportOrder() {
        return invoiceReportOrder;
    }

    public void setInvoiceReportOrder(List<InvoiceReportOrderDetailsJson> invoiceReportOrder) {
        this.invoiceReportOrder = invoiceReportOrder;
    }
}
