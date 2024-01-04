package com.aquacount.aquacount.model.measurement.dto;

import java.util.List;

public class RegisterCounterRequest {

    private List<RegisterCounter> counterList;

    public List<RegisterCounter> getCounterList() {
        return counterList;
    }

    public void setCounterList(List<RegisterCounter> counterList) {
        this.counterList = counterList;
    }
}
