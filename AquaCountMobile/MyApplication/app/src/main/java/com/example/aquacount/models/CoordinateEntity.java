package com.example.aquacount.models;

import java.io.Serializable;

public class CoordinateEntity implements Serializable {

    private double latitude;

    private double longitude;

    public CoordinateEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongititude(double longitude) {
        this.longitude = longitude;
    }
}
