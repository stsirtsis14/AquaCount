package com.aquacount.aquacount.repository;

import com.aquacount.aquacount.model.measurement.entity.MeasurementEntity;
import com.aquacount.aquacount.model.measurement.view.CounterMeasurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CounterMeasurementsRepository extends JpaRepository<CounterMeasurements,Long> {

    List<CounterMeasurements> findByCounterid(Long counterid);

}