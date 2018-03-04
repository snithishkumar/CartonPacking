package com.ordered.report.json.models;

import com.ordered.report.enumeration.DeliveringType;

/**
 * Created by Nithish on 05/03/18.
 */

public class DeliveringDetailsJson {
    private DeliveringType deliveringType;
    private String placeOfLoading;
    private String placeOfDelivery;
    private String portOfDischarge;


    public DeliveringType getDeliveringType() {
        return deliveringType;
    }

    public void setDeliveringType(DeliveringType deliveringType) {
        this.deliveringType = deliveringType;
    }

    public String getPlaceOfLoading() {
        return placeOfLoading;
    }

    public void setPlaceOfLoading(String placeOfLoading) {
        this.placeOfLoading = placeOfLoading;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    @Override
    public String toString() {
        return "DeliveringDetailsJson{" +
                "deliveringType=" + deliveringType +
                ", placeOfLoading='" + placeOfLoading + '\'' +
                ", placeOfDelivery='" + placeOfDelivery + '\'' +
                ", portOfDischarge='" + portOfDischarge + '\'' +
                '}';
    }
}
