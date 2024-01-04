package com.example.aquacount;

public class AuthTokenResponse {
    private String authorization;

    public AuthTokenResponse() {
    }

    public AuthTokenResponse(String authorization) {
        this.authorization = authorization;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
