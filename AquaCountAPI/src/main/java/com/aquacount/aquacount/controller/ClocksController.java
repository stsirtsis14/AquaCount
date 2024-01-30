package com.aquacount.aquacount.controller;

import com.aquacount.aquacount.model.measurement.dto.RegisterClockRequest;
import com.aquacount.aquacount.model.measurement.dto.RegisterMeasurementRequest;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;
import com.aquacount.aquacount.service.measurement.ClockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/clocks")
public class ClocksController {

    private final ClockService clockService;

    public ClocksController(ClockService clockService) {
        this.clockService = clockService;
    }

    @GetMapping("/{routeid}")
    public ResponseEntity<List<Point>> getCoordinates(@PathVariable("routeid") Long routeid) {
        List<Point> coordinates = clockService.getCoordinates(routeid);
        return ResponseEntity.ok(coordinates);
    }

    @GetMapping("/getClockids/{routeid2}")
    public ResponseEntity<List<Long>> getClocksFromRoute(@PathVariable("routeid2") Long routeid2){
        List<Long> clocks = clockService.getClocks(routeid2);
        return ResponseEntity.ok(clocks);
    }

    @GetMapping("/allclocks")
    public ResponseEntity<List<ClockEntity>> getAllClocks(){
        List<ClockEntity> clocks = clockService.getAllClocks();
        return ResponseEntity.ok(clocks);
    }

    @DeleteMapping("/deleteClock/{clockid}")
    public ResponseEntity<Void> deleteClock(@PathVariable Long clockid) {
        clockService.deleteClock(clockid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerClock(@RequestBody RegisterClockRequest registerClockRequest) {
        // Log the received JSON payload
        System.out.println(registerClockRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            clockService.addClock(registerClockRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    @PutMapping("/update/{clockid}")
    public ResponseEntity<Void> updateClock(@PathVariable Long clockid,@RequestBody Long newRouteid){
        clockService.updateClock(clockid,newRouteid);
        return ResponseEntity.ok().build();
    }
}
