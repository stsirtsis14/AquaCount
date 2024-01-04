package com.aquacount.aquacount.model.measurement.view;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Immutable
//@Subselect("select * from counter_measurements")
@Table(name = "counter_measurements")
@IdClass(CounterMeasurements.class)
public class CounterMeasurements implements  Serializable{
    @Id
    private Long counterid;
    @Id
    private Long routeid;
    private Long clockid;
    private Long value;
    private Timestamp timestamp;

    private Long measurementid;

    public Long getMeasurementid() {
        return measurementid;
    }

    public CounterMeasurements() {
    }

    public CounterMeasurements(Long counterid, Long routeid, Long clockid, Long value, Timestamp timestamp,Long measurementid) {
        this.counterid = counterid;
        this.routeid = routeid;
        this.clockid = clockid;
        this.value = value;
        this.timestamp = timestamp;
        this.measurementid = measurementid;
    }

    public Long getCounterid() {
        return counterid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public Long getClockid() {
        return clockid;
    }

    public Long getValue() {
        return value;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
