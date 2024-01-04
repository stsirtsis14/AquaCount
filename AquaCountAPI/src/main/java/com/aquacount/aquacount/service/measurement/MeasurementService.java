package com.aquacount.aquacount.service.measurement;

import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurement;
import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurementRequest;
import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;

import java.util.List;

public interface MeasurementService {
    void registerMeasurement(RegisterMeasurementRequest registerMeasurementRequest);
    MeasurementEntity findMeasurement(Long id);

    void deleteMeasurement(Long id);

    List getAllMeasurements();

    List getAllMeasurementsByCounter(Long counterid);

    void updateMeasurement(Long id,MeasurementEntity updatedMeasurement);

    void updateMeasurementValue(Long measurementId,Long newValue);
}
