package com.aquacount.aquacount.model.measurement.dto;

import jakarta.persistence.Column;

public class RegisterClock {
    private Long clockid;

    private Long routeid;

    private String coordinates;

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
