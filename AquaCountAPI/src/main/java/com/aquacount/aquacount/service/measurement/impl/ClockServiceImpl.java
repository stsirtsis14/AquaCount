package com.aquacount.aquacount.service.measurement.impl;


import com.aquacount.aquacount.model.measurement.dto.*;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;
import com.aquacount.aquacount.model.measurement.entity.Coordinate;
import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;
import com.aquacount.aquacount.repository.ClocksRepository;
import com.aquacount.aquacount.repository.MeasurementRepository;
import com.aquacount.aquacount.service.measurement.ClockService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClockServiceImpl implements ClockService {

    private final ClocksRepository clockRepository;
    private final MeasurementRepository measurementRepository;

    public ClockServiceImpl(ClocksRepository clockRepository, MeasurementRepository measurementRepository) {
        this.clockRepository = clockRepository;
        this.measurementRepository = measurementRepository;
    }

    public List<Long> getClocks(Long routeid){
        return clockRepository.findByRouteid(routeid);
    }

    public List<Coordinate> getCoordinates(Long routeid) {
        List<String> coordinateStrings = clockRepository.findCoordinatesByRouteid(routeid);
        return coordinateStrings.stream()
                .map(this::parseCoordinateString)
                .collect(Collectors.toList());
    }

    private Coordinate parseCoordinateString(String coordinateString) {
        // Remove parentheses and split into latitude and longitude parts
        String[] parts = coordinateString.replaceAll("[()]", "").split(",");
        if (parts.length == 2) {
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);
            return new Coordinate(latitude, longitude);
        } else {
            // Handle invalid coordinate strings
            return null;
        }
    }

    public List<ClockEntity> getAllClocks(){
        return clockRepository.findAll();
    }

    public Optional<ClockEntity> findByClockId(Long clockid){
        return clockRepository.findById(clockid);
    }

    public List<MeasurementEntity> getClockMeasurements(Long clockid){
        return measurementRepository.findByClockid(clockid);
    }

    public void deleteClock(Long clockid){
        clockRepository.deleteById(clockid);
    }

    @Override
    public void addClock(RegisterClockRequest registerClockRequest) {
        for(RegisterClock registerClock: registerClockRequest.getClocksList()) {
            ClockEntity clockEntity = new ClockEntity();
            clockEntity.setClockid(registerClock.getClockid());
            clockEntity.setRouteid(registerClock.getRouteid());
            clockEntity.setCoordinates(registerClock.getCoordinates());
            clockRepository.save(clockEntity);
        }
    }

    public void updateClock(Long clockid,Long newRouteid){
        ClockEntity existingClock = clockRepository.findById(clockid).orElse(null);

        if (existingClock != null) {
            existingClock.setRouteid(newRouteid);
            clockRepository.save(existingClock);
        }
    }

}
