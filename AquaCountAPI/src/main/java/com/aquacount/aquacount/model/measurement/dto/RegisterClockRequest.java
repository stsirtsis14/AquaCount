package com.aquacount.aquacount.model.measurement.dto;

import com.aquacount.aquacount.model.measurement.entity.ClockEntity;

import java.util.List;

public class RegisterClockRequest {

    private List<RegisterClock> clocksList;

    public List<RegisterClock> getClocksList() {
        return clocksList;
    }

    public void setcClocksList(List<RegisterClock> clocksList) {
        this.clocksList = clocksList;
    }
}
