package com.aquacount.aquacount.service.measurement.impl;

import com.aquacount.aquacount.model.measurement.dto.FindCounter;
import com.aquacount.aquacount.model.measurement.dto.RegisterCounter;
import com.aquacount.aquacount.model.measurement.dto.RegisterCounterRequest;
import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import com.aquacount.aquacount.repository.CountersRepository;
import com.aquacount.aquacount.service.measurement.CounterService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CounterServiceImpl implements CounterService {

    private final CountersRepository countersRepository;

    public CounterServiceImpl(CountersRepository countersRepository) {
        this.countersRepository = countersRepository;
    }

    public List<CountersEntity> getAllCounters(){
        return countersRepository.findAll();
    }

    public FindCounter getCounter(Long routeid){
        return countersRepository.findByRouteid(routeid);
    }

    @Override
    public void deleteCounter(Long counterid) {
        countersRepository.deleteById(counterid);
    }

    @Override
    public void addCounter(RegisterCounterRequest registerCounterRequest) {
        for(RegisterCounter registerCounter: registerCounterRequest.getCounterList()) {
            CountersEntity countersEntity = new CountersEntity();
            countersEntity.setCounterid(registerCounter.getCounterid());
            countersEntity.setFirstName(registerCounter.getFirstName());
            countersEntity.setLastName(registerCounter.getLastName());
            countersEntity.setUsername(registerCounter.getUsername());
            countersEntity.setPassword(registerCounter.getPassword());
            countersEntity.setAuthority(registerCounter.getAuthority());
            countersRepository.save(countersEntity);
        }
    }
}
