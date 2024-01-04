package com.aquacount.aquacount.model.measurement.dto;

public class RegisterClockId {

    private Long clockid;

    public RegisterClockId(Long clockid) {
        this.clockid = clockid;
    }

    public Long getClockid() {
        return clockid;
    }

    public void setClockid(Long clockid) {
        this.clockid = clockid;
    }
}
