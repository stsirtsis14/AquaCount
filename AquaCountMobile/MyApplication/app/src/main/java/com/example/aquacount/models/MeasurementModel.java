package com.example.aquacount.models;

import java.sql.Timestamp;

public class MeasurementModel {
    private Long clockId;
    private String value;
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getClockId() {
        return clockId;
    }

    public void setClockId(Long clockId) {
        this.clockId = clockId;
    }


    public String getNewMeasurement() {
        return value;
    }

    public void setNewMeasurement(String value) {
        this.value = value;
    }
}
