package com.aquacount.aquacount.model.measurement.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class FindCounter {


    private Long counterid;

    private String firstName;

    private String lastName;

    private String username;

    private String authority;

    public FindCounter(Long counterid, String firstName, String lastName, String username, String authority) {
        this.counterid = counterid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.authority = authority;
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
