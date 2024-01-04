package com.aquacount.aquacount.model.measurement.dto;

import java.util.List;

public class RegisterMeasurementRequest {
    private List<RegisterMeasurement> measurementsList;

    public List<RegisterMeasurement> getMeasurementsList() {
        return measurementsList;
    }

    public void setMeasurementsList(List<RegisterMeasurement> measurementsList) {
        this.measurementsList = measurementsList;
    }
}
