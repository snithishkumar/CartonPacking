package com.ordered.report.json.models;

/**
 * Created by Nithish on 05/03/18.
 */

public class OtherDetailsJson {

    private String exporterDetails;
    private String exporterRef;
    private String consigneeDetails;
    private String tinNumber;
    private String exporterCountry;
    private String consigneeCountry;


    public String getExporterDetails() {
        return exporterDetails;
    }

    public void setExporterDetails(String exporterDetails) {
        this.exporterDetails = exporterDetails;
    }

    public String getExporterRef() {
        return exporterRef;
    }

    public void setExporterRef(String exporterRef) {
        this.exporterRef = exporterRef;
    }

    public String getConsigneeDetails() {
        return consigneeDetails;
    }

    public void setConsigneeDetails(String consigneeDetails) {
        this.consigneeDetails = consigneeDetails;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getExporterCountry() {
        return exporterCountry;
    }

    public void setExporterCountry(String exporterCountry) {
        this.exporterCountry = exporterCountry;
    }

    public String getConsigneeCountry() {
        return consigneeCountry;
    }

    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }

    @Override
    public String toString() {
        return "OtherDetailsJson{" +
                "exporterDetails='" + exporterDetails + '\'' +
                ", exporterRef='" + exporterRef + '\'' +
                ", consigneeDetails='" + consigneeDetails + '\'' +
                ", tinNumber='" + tinNumber + '\'' +
                ", exporterCountry='" + exporterCountry + '\'' +
                ", consigneeCountry='" + consigneeCountry + '\'' +
                '}';
    }
}
