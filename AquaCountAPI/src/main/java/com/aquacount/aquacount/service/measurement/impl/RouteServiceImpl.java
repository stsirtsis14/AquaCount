package com.aquacount.aquacount.service.measurement.impl;

import com.aquacount.aquacount.model.measurement.dto.RegisterClock;
import com.aquacount.aquacount.model.measurement.dto.RegisterClockRequest;
import com.aquacount.aquacount.model.measurement.dto.RegisterRoute;
import com.aquacount.aquacount.model.measurement.dto.RegisterRouteRequest;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;
import com.aquacount.aquacount.model.measurement.entity.RouteEntity;
import com.aquacount.aquacount.repository.RouteRepository;
import com.aquacount.aquacount.service.measurement.RouteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteEntity> getAllRoutes(){
        return routeRepository.findAll();
    }

    public List<RouteEntity> getRoutesFromCounterid(Long counterid){
        return routeRepository.findByCounterid(counterid);
    }

    @Override
    public void deleteRoute(Long routeid) {
        routeRepository.deleteById(routeid);
    }

    @Override
    public void addRoute(RegisterRouteRequest registerRouteRequest) {
        for(RegisterRoute registerRoute: registerRouteRequest.getRoutesList()) {
            RouteEntity routeEntity = new RouteEntity();
            routeEntity.setRouteid(registerRoute.getRouteid());
            routeEntity.setCounterid(registerRoute.getCounterid());
            routeRepository.save(routeEntity);
        }
    }
}
