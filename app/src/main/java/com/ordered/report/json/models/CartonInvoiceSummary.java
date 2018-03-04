package com.ordered.report.json.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 8/12/2017.
 */

public class CartonInvoiceSummary {
    private String exporterAddress;
    private String invoiceWithDate;
    private String exporteRef;
    private String orderNo;
    private String tintNo;
    private String consigneAddress;
    private String vessels;
    private String termsAndConditions;
    private String country;
    private String finalDestination;
    private String loadingCity;
    private String placeofDelivery;
    private int cartonCount;
   private InvoiceReportJson invoiceReportJson;

    public String getExporterAddress() {
        return exporterAddress;
    }

    public void setExporterAddress(String exporterAddress) {
        this.exporterAddress = exporterAddress;
    }

    public String getInvoiceWithDate() {
        return invoiceWithDate;
    }

    public void setInvoiceWithDate(String invoiceWithDate) {
        this.invoiceWithDate = invoiceWithDate;
    }

    public String getExporteRef() {
        return exporteRef;
    }

    public void setExporteRef(String exporteRef) {
        this.exporteRef = exporteRef;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTintNo() {
        return tintNo;
    }

    public void setTintNo(String tintNo) {
        this.tintNo = tintNo;
    }

    public String getConsigneAddress() {
        return consigneAddress;
    }

    public void setConsigneAddress(String consigneAddress) {
        this.consigneAddress = consigneAddress;
    }

    public String getVessels() {
        return vessels;
    }

    public void setVessels(String vessels) {
        this.vessels = vessels;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public String getLoadingCity() {
        return loadingCity;
    }

    public void setLoadingCity(String loadingCity) {
        this.loadingCity = loadingCity;
    }

    public String getPlaceofDelivery() {
        return placeofDelivery;
    }

    public void setPlaceofDelivery(String placeofDelivery) {
        this.placeofDelivery = placeofDelivery;
    }

    public int getCartonCount() {
        return cartonCount;
    }

    public void setCartonCount(int cartonCount) {
        this.cartonCount = cartonCount;
    }

    public InvoiceReportJson getInvoiceReportJson() {
        return invoiceReportJson;
    }

    public void setInvoiceReportJson(InvoiceReportJson invoiceReportJson) {
        this.invoiceReportJson = invoiceReportJson;
    }

    @Override
    public String toString() {
        return "CartonInvoiceSummary{" +
                "exporterAddress='" + exporterAddress + '\'' +
                ", invoiceWithDate='" + invoiceWithDate + '\'' +
                ", exporteRef='" + exporteRef + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", tintNo='" + tintNo + '\'' +
                ", consigneAddress='" + consigneAddress + '\'' +
                ", vessels='" + vessels + '\'' +
                ", termsAndConditions='" + termsAndConditions + '\'' +
                ", country='" + country + '\'' +
                ", finalDestination='" + finalDestination + '\'' +
                ", loadingCity='" + loadingCity + '\'' +
                ", placeofDelivery='" + placeofDelivery + '\'' +
                ", cartonCount=" + cartonCount +
                ", invoiceReportJson=" + invoiceReportJson +
                '}';
    }
}
