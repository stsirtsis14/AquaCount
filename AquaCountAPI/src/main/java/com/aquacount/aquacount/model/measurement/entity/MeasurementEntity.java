package com.aquacount.aquacount.model.measurement.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="Measurements")
public class MeasurementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    //@Column(nullable = false)
    private Long clockid;
    private String value;
    private Timestamp timestamp;

    public MeasurementEntity(Long id, Long clockid, String value,Timestamp timestamp) {
        this.id = id;
        this.clockid = clockid;
        this.value = value;
        this.timestamp = timestamp;
    }

    public MeasurementEntity() {
    }

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
