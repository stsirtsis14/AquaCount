package com.example.aquacount.models;

import com.google.gson.annotations.SerializedName;

public class RouteEntity {

    @SerializedName("routeid")
    private Long routeId;
    @SerializedName("counterid")
    private Long counterid;

    public RouteEntity(Long routeid, Long counterid) {
        this.routeId = routeid;
        this.counterid = counterid;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }
}
