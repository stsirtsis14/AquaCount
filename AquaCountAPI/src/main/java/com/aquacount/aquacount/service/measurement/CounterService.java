package com.aquacount.aquacount.service.measurement;

import com.aquacount.aquacount.model.measurement.dto.FindCounter;
import com.aquacount.aquacount.model.measurement.dto.RegisterCounterRequest;
import com.aquacount.aquacount.model.measurement.dto.UpdCounter;
import com.aquacount.aquacount.model.measurement.entity.CountersEntity;


import java.util.List;
import java.util.Optional;

public interface CounterService {
    List getAllCounters();

    FindCounter getCounter(Long routeid);

    void deleteCounter(Long counterid);

    void addCounter(RegisterCounterRequest registerCounterRequest);

    void updateCounter(Long counterid, UpdCounter updatedCounter);
}
