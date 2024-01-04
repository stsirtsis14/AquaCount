package com.aquacount.aquacount.model.measurement.dto;

import jakarta.persistence.Column;

public class RegisterRoute {
    private Long routeid;

    private Long counterid;

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }
}
