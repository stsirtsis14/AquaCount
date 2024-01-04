package com.example.aquacount.models;

import java.sql.Timestamp;

public class MeasurementUpdateEntity {

    private Long counterid;
    private Long routeid;
    private Long clockid;
    private Long value;
    private Timestamp timestamp;
    private Long measurementid;

    public MeasurementUpdateEntity(Long counterid, Long routeid, Long clockid, Long value, Timestamp timestamp, Long measurementid) {
        this.counterid = counterid;
        this.routeid = routeid;
        this.clockid = clockid;
        this.value = value;
        this.timestamp = timestamp;
        this.measurementid = measurementid;
    }

    public MeasurementUpdateEntity() {
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    public Long getClockid() {
        return clockid;
    }

    public void setClockid(Long clockid) {
        this.clockid = clockid;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMeasurementid() {
        return measurementid;
    }

    public void setMeasurementid(Long measurementid) {
        this.measurementid = measurementid;
    }
}
