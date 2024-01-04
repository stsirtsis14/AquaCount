package com.aquacount.aquacount.model.measurement.entity;

import jakarta.persistence.*;


@Entity
@Table(name="clocks")
public class ClockEntity {

    @Id
    @Column(name="clockid",nullable = false,updatable = false)
    private Long clockid;

    @Column(name="routeid")
    private Long routeid;

    @Column(name="coordinates")
    private String coordinates;

    public ClockEntity() {
    }

    public ClockEntity(Long clockid, Long routeid, String  coordinates) {
        this.clockid = clockid;
        this.routeid = routeid;
        this.coordinates = coordinates;
    }

    public Long getClockid() {
        return clockid;
    }

    public void setClockid(Long clockid) {
        this.clockid = clockid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
