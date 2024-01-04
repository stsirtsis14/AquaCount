package com.aquacount.aquacount.model.measurement.dto;

public class LoginResponseWrapper {
    private LoginResponse loginResponse;
    private String token;

    public LoginResponseWrapper(LoginResponse loginResponse, String token) {
        this.loginResponse = loginResponse;
        this.token = token;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
