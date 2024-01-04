package com.aquacount.aquacount.model.measurement.dto;

public class LoginResponse {
    private String username;
    private Long counterid;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }
}
