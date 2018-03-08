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

    private int totalCartonCount = 0;
    private int totalProductsCount = 0;
    private double totalWeight = 0;
    private double totalGrossWeight = 0;

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

    public int getTotalCartonCount() {
        return totalCartonCount;
    }

    public void setTotalCartonCount(int totalCartonCount) {
        this.totalCartonCount = totalCartonCount;
    }

    public int getTotalProductsCount() {
        return totalProductsCount;
    }

    public void setTotalProductsCount(int totalProductsCount) {
        this.totalProductsCount = totalProductsCount;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalGrossWeight() {
        return totalGrossWeight;
    }

    public void setTotalGrossWeight(double totalGrossWeight) {
        this.totalGrossWeight = totalGrossWeight;
    }

    public InvoiceReportJson getInvoiceReportJson() {
        return invoiceReportJson;
    }

    public void setInvoiceReportJson(InvoiceReportJson invoiceReportJson) {
        this.invoiceReportJson = invoiceReportJson;
    }
}
