package com.example.aquacount.models;

public class LoginResponse {
    private String username;
    private Long counterid;
//    private Long routeid;

    public LoginResponse() {
    }

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

//    public Long getRouteid() {
//        return routeid;
//    }
//
//    public void setRouteid(Long routeid) {
//        this.routeid = routeid;
//    }
}
