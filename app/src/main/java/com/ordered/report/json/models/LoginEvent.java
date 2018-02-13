package com.ordered.report.json.models;

/**
 * Created by selvaraj.
 */
public class LoginEvent {
    int statusCode;
    String status;
    boolean isRemove = false;

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public void setRemove(boolean remove) {
        isRemove = remove;
    }

    public String getAirportname() {
        return status;
    }

    public void setAirportname(String status) {
        this.status = status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
