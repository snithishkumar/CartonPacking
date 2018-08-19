package com.ordered.report.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Nithish on 05/03/18.
 */
@DatabaseTable(tableName = "ClientDetails")
public class ClientDetailsEntity {

    public static final String CLIENT_DETAILS_ID = "ClientDetailsId";
    public static final String CLIENT_DETAILS_UUID = "clientDetailsUUID";

    @DatabaseField(columnName = "ClientDetailsId", generatedId = true)
    private int clientDetailsId;
    @DatabaseField(columnName = "ClientDetailsUUID")
    private String clientDetailsUUID;
    @DatabaseField(columnName = "ExporterDetails")
    private String exporterDetails;
    @DatabaseField(columnName = "ExporterRef")
    private String exporterRef;
    @DatabaseField(columnName = "ConsigneeDetails")
    private String consigneeDetails;
    @DatabaseField(columnName = "TinNumber")
    private String tinNumber;
    @DatabaseField(columnName = "ExporterCountry")
    private String exporterCountry;
    @DatabaseField(columnName = "ConsigneeCountry")
    private String consigneeCountry;





    public int getClientDetailsId() {
        return clientDetailsId;
    }

    public void setClientDetailsId(int clientDetailsId) {
        this.clientDetailsId = clientDetailsId;
    }

    public String getClientDetailsUUID() {
        return clientDetailsUUID;
    }

    public void setClientDetailsUUID(String clientDetailsUUID) {
        this.clientDetailsUUID = clientDetailsUUID;
    }

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
        return "ClientDetailsEntity{" +
                "clientDetailsId=" + clientDetailsId +
                ", clientDetailsUUID='" + clientDetailsUUID + '\'' +
                ", exporterDetails='" + exporterDetails + '\'' +
                ", exporterRef='" + exporterRef + '\'' +
                ", consigneeDetails='" + consigneeDetails + '\'' +
                ", tinNumber='" + tinNumber + '\'' +
                ", exporterCountry='" + exporterCountry + '\'' +
                ", consigneeCountry='" + consigneeCountry + '\'' +
                '}';
    }
}
