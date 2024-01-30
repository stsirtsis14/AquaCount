package com.aquacount.aquacount.service.measurement.impl;

import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurement;
import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurementRequest;
import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;
import com.aquacount.aquacount.repository.MeasurementRepository;
import com.aquacount.aquacount.service.measurement.MeasurementService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    @Override
    public void registerMeasurement(RegisterMeasurementRequest registerMeasurementRequest) {
        for(RegisterMeasurement registerMeasurement: registerMeasurementRequest.getMeasurementsList()) {
            MeasurementEntity measurementEntity = new MeasurementEntity();
            measurementEntity.setClockid(registerMeasurement.getClockid());
            measurementEntity.setValue(registerMeasurement.getValue());
            measurementEntity.setTimestamp(registerMeasurement.getTimestamp());
            measurementRepository.save(measurementEntity);
        }
    }

    public void deleteMeasurement(Long id){

        measurementRepository.deleteById(id);
    }


    public MeasurementEntity findMeasurement(Long id){
        MeasurementEntity measurementEntity = measurementRepository.findById(id).orElseThrow();
        return measurementEntity;
    }

    public List<MeasurementEntity> getAllMeasurements(){
        return measurementRepository.findAll();
    }

    public List<MeasurementEntity> getAllMeasurementsOfClock(Long clockid){return measurementRepository.findByClockid(clockid);}

    public List<MeasurementEntity> getAllMeasurementsByCounter(Long counterid){
        return measurementRepository.findByCounterid(counterid);
    }

    public void updateMeasurement(Long id,MeasurementEntity updatedMeasurement){
        MeasurementEntity existingMeasurement = measurementRepository.findById(id).orElse(null);

        if (existingMeasurement != null) {

            existingMeasurement.setValue(updatedMeasurement.getValue());
            measurementRepository.save(existingMeasurement);
        }
    }
public void updateMeasurementValue(Long measurementId, Long newValue){
    MeasurementEntity existingMeasurement = measurementRepository.findById(measurementId).orElse(null);

    if (existingMeasurement != null) {
        existingMeasurement.setValue(String.valueOf(newValue));
        measurementRepository.save(existingMeasurement);
    }
}
}
