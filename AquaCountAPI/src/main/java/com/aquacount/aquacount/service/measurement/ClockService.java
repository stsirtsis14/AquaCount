package com.aquacount.aquacount.service.measurement;

import com.aquacount.aquacount.model.measurement.dto.RegisterClockRequest;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;

import java.util.List;
import java.util.Optional;

public interface ClockService {
    List getClocks(Long routeid);

    List getCoordinates(Long routeid);

    List getAllClocks();

    Optional<ClockEntity> findByClockId(Long clockid);

    List getClockMeasurements(Long clockid);

    void deleteClock(Long clockid);

    void addClock(RegisterClockRequest registerClockRequest);

    void updateClock(Long clockid,Long newRouteid);
}
