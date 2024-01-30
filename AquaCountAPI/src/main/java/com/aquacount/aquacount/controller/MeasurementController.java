package com.aquacount.aquacount.controller;

import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurementRequest;
import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;
import com.aquacount.aquacount.model.measurement.view.CounterMeasurements;
import com.aquacount.aquacount.repository.MeasurementRepository;
import com.aquacount.aquacount.service.measurement.CounterMeasurementsService;
import com.aquacount.aquacount.service.measurement.MeasurementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final MeasurementRepository measurementRepository;
    private final CounterMeasurementsService counterMeasurementsService;

    public MeasurementController(MeasurementService measurementService, MeasurementRepository measurementRepository, CounterMeasurementsService counterMeasurementsService) {
        this.measurementService = measurementService;
        this.measurementRepository = measurementRepository;
        this.counterMeasurementsService = counterMeasurementsService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerMeasurement(@RequestBody RegisterMeasurementRequest registerMeasurementRequest) {
        // Log the received JSON payload
        System.out.println(registerMeasurementRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            measurementService.registerMeasurement(registerMeasurementRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/allmeasurements")
    public ResponseEntity<List<MeasurementEntity>> getMeasurement2() {
        List<MeasurementEntity> measurements = measurementService.getAllMeasurements();
        if (measurements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/update/{counterId}")
    public ResponseEntity<List<CounterMeasurements>> getMeasurement(@PathVariable Long counterId) {
        List<CounterMeasurements> measurements = counterMeasurementsService.getAllMeasurementsByCounterId(counterId);
        if (measurements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(measurements);
    }

    @PutMapping("/updateValue/{measurementId}")
    public ResponseEntity<Void> updateMeasurementValue(@PathVariable Long measurementId, @RequestBody Long newValue) {
        measurementService.updateMeasurementValue(measurementId, newValue);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{measurementId}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable int measurementId) {
        measurementService.deleteMeasurement(Long.valueOf(measurementId));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/measurementsOfaCounter/{counterId}")
    public ResponseEntity<List<MeasurementEntity>> getMeasurementsByCounter(@PathVariable Long counterId) {
        List<MeasurementEntity> measurements = measurementRepository.findByCounterid(counterId);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @GetMapping("/measurementsOfClock/{clockid}")
    public ResponseEntity<List<MeasurementEntity>> getMeasurementsOfClock(@PathVariable Long clockid){
        List<MeasurementEntity> measurements = measurementRepository.findByClockid(clockid);
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }
}
