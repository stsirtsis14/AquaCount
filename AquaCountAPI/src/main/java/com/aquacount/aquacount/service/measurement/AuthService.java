package com.aquacount.aquacount.service.measurement;


import com.aquacount.aquacount.model.measurement.dto.LoginResponseWrapper;
import com.aquacount.aquacount.model.measurement.dto.UserCredentials;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponseWrapper login(UserCredentials userCredentials);

}
