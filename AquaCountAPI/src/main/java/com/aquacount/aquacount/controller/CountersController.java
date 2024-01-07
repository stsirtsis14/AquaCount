package com.aquacount.aquacount.controller;

import com.aquacount.aquacount.model.measurement.dto.*;
import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import com.aquacount.aquacount.service.measurement.AuthService;
import com.aquacount.aquacount.service.measurement.CounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@RestController
@RequestMapping("/counters")
public class CountersController {


    private AuthService authService;
    private  CounterService counterService;
    public CountersController(AuthService authService, CounterService counterService) {
        this.authService = authService;
        this.counterService = counterService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginCounter( @RequestBody LoginCounter loginCounter) {
        LoginResponseWrapper loginResponseWrapper = authService.login(loginCounter.getUserCredentials());
        // Επιστρέψτε το token ως απάντηση μαζί με τον κωδικό 200 OK.
        return ResponseEntity.ok().header("Authorization", "" + loginResponseWrapper.getToken()).body(loginResponseWrapper.getLoginResponse());
    }
    @GetMapping("/allCounters")
    public ResponseEntity<List<CountersEntity>> getAllCounters(){
        List<CountersEntity> counterList = counterService.getAllCounters();
        return ResponseEntity.ok(counterList);
    }

    @GetMapping("/CounterByRouteid/{routeid}")
    public ResponseEntity<FindCounter> getCounter(@PathVariable Long routeid){
        FindCounter counter = counterService.getCounter(routeid);
        return  ResponseEntity.ok(counter);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerCounter(@RequestBody RegisterCounterRequest registerCounterRequest) {
        // Log the received JSON payload
        System.out.println(registerCounterRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            counterService.addCounter(registerCounterRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/update/{counterid}")
    public  ResponseEntity<Void> updateCounter(@PathVariable Long counterid, @RequestBody UpdCounter updatedCounter){
        counterService.updateCounter(counterid,updatedCounter);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{counterid}")
    public ResponseEntity<Void> deleteCounter(@PathVariable Long counterid){
        counterService.deleteCounter(counterid);
        return ResponseEntity.ok().build();
    }
}
