package com.example.aquacount.request;

import com.example.aquacount.models.MeasurementModel;

import java.util.List;

public class MeasurementRequest {
    private List<MeasurementModel> measurementsList;

    public MeasurementRequest(List<MeasurementModel> measurementsList) {
        this.measurementsList = measurementsList;
    }
}
