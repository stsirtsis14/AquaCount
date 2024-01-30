package com.aquacount.aquacount.controller;

import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurementRequest;
import com.aquacount.aquacount.model.measurement.dto.RegisterRouteRequest;
import com.aquacount.aquacount.model.measurement.entity.RouteEntity;
import com.aquacount.aquacount.service.measurement.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/allRoutes")
    public ResponseEntity<List<RouteEntity>> getAllRoutes(){
        List<RouteEntity> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{counterid}")
    public ResponseEntity<List<RouteEntity>> getRouteFromCounterid(@PathVariable Long counterid){
        List<RouteEntity> routes = routeService.getRoutesFromCounterid(counterid);
        return ResponseEntity.ok(routes);
    }
    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long routeId){
        routeService.deleteRoute(routeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerRoute(@RequestBody RegisterRouteRequest registerRouteRequest) {
        // Log the received JSON payload
        System.out.println("Received JSON payload: " + registerRouteRequest);
        routeService.addRoute(registerRouteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{routeid}")
    public  ResponseEntity<Void> updateRoute(@PathVariable Long routeid ,@RequestBody Long newCounterid){
       routeService.updateRoute(routeid,newCounterid);
       return ResponseEntity.ok().build();
    }
}
