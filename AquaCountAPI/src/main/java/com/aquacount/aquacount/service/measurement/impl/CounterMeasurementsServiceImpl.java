package com.aquacount.aquacount.service.measurement.impl;


import com.aquacount.aquacount.model.measurement.view.CounterMeasurements;
import com.aquacount.aquacount.repository.ClocksRepository;
import com.aquacount.aquacount.repository.CounterMeasurementsRepository;
import com.aquacount.aquacount.service.measurement.CounterMeasurementsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CounterMeasurementsServiceImpl implements CounterMeasurementsService {

    private final CounterMeasurementsRepository counterMeasurementsRepository;

    public CounterMeasurementsServiceImpl(CounterMeasurementsRepository counterMeasurementsRepository) {
        this.counterMeasurementsRepository = counterMeasurementsRepository;
    }

    public List<CounterMeasurements> getAllMeasurementsByCounterId(Long counterid){
        //return counterMeasurementsRepository.findAll();
        return counterMeasurementsRepository.findByCounterid(counterid);
    }

}
