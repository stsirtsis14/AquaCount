package com.aquacount.aquacount.service.measurement.impl;

import com.aquacount.aquacount.JwtUtil;
import com.aquacount.aquacount.model.measurement.dto.LoginResponse;
import com.aquacount.aquacount.model.measurement.dto.LoginResponseWrapper;
import com.aquacount.aquacount.model.measurement.dto.UserCredentials;
import com.aquacount.aquacount.model.measurement.entity.ClockEntity;
import com.aquacount.aquacount.model.measurement.entity.CountersEntity;
import com.aquacount.aquacount.model.security.UserDetailsImpl;
import com.aquacount.aquacount.repository.CountersRepository;
import com.aquacount.aquacount.service.measurement.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CountersRepository countersRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseWrapper login(UserCredentials userCredentials) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword()));
        LoginResponse loginResponse = null;
        LoginResponseWrapper loginResponseWrapper = null;
        CountersEntity countersEntity = countersRepository.findByUsername(userCredentials.getUsername());

        if (countersEntity != null) {
            loginResponse = new LoginResponse();
            loginResponse.setUsername(userCredentials.getUsername());
            loginResponse.setCounterid(countersEntity.getCounterid());


            String token = jwtUtil.generateToken(new UserDetailsImpl(countersEntity));
            loginResponseWrapper = new LoginResponseWrapper(loginResponse, token);
        }
        return loginResponseWrapper;
    }
}
