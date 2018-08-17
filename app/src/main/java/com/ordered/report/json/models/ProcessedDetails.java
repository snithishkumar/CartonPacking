package com.ordered.report.json.models;

public class ProcessedDetails {
    private String uuid;
    private long dateTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "ProcessedDetails{" +
                "uuid='" + uuid + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
