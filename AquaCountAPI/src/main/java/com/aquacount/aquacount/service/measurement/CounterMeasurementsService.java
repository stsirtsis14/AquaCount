package com.aquacount.aquacount.service.measurement;

import com.aquacount.aquacount.model.measurement.view.CounterMeasurements;

import java.util.List;

public interface CounterMeasurementsService {

    List<CounterMeasurements> getAllMeasurementsByCounterId(Long counterid);

}
