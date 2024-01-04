package com.example.aquacount.models;

import java.sql.Timestamp;

public class MeasurementEntity {
        private Long id;
        private Long clockId;
        private String value;
        private Timestamp timestamp;


    public MeasurementEntity(Long id, Long clockId, String value, Timestamp timestamp) {
        this.id = id;
        this.clockId = clockId;
        this.value = value;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClockId() {
        return clockId;
    }

    public void setClockId(Long clockId) {
        this.clockId = clockId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

