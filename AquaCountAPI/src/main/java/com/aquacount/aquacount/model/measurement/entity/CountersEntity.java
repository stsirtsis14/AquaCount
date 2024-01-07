package com.aquacount.aquacount.model.measurement.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Counters")
public class CountersEntity {

        @Id
        //@GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false,updatable = false)
        private Long counterid;
        @Column(nullable = false)
        private String firstName;
        @Column(nullable = false)
        private String lastName;
        @Column(nullable = false)
        private String username;
        @Column(nullable = false)
        private String password;
        @Column(nullable = false)
        private String authority;


    public CountersEntity(Long counterid, String firstName, String lastName, String username, String password,String authority) {
        this.counterid = counterid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    public CountersEntity(){

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
