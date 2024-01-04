package com.aquacount.aquacount.service.measurement;

import com.aquacount.aquacount.model.measurement.dto.RegisterClockRequest;
import com.aquacount.aquacount.model.measurement.dto.RegisterRouteRequest;
import com.aquacount.aquacount.model.measurement.entity.RouteEntity;

import java.util.List;

public interface RouteService {
    List getAllRoutes();

    void deleteRoute(Long routeid);

    public List<RouteEntity> getRoutesFromCounterid(Long counterid);

    void addRoute(RegisterRouteRequest registerRouteRequest);
}
