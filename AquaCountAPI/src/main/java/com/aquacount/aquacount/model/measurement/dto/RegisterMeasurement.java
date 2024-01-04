package com.aquacount.aquacount.model.measurement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class RegisterMeasurement {
    @JsonProperty("clockId")
    private Long clockid;
    private String value;
    @JsonFormat(pattern = "MMM d, yyyy h:mm:ss a")
    private Timestamp timestamp;

    public Long getClockid() {
        return clockid;
    }

    public void setClockid(Long clockid) {
        this.clockid = clockid;
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
