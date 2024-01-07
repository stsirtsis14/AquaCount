package com.aquacount.aquacount.model.measurement.dto;

public class UpdCounter {
    private String firstName;
    private String lastName;
    private String username;
    private String authority;

    public UpdCounter(String firstName, String lastName, String username, String authority) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.authority = authority;
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
