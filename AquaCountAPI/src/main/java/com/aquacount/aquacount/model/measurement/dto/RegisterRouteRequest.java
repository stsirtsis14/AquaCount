package com.aquacount.aquacount.model.measurement.dto;

import java.util.List;

public class RegisterRouteRequest {

    private List<RegisterRoute> routeList;

    public List<RegisterRoute> getRoutesList() {
        return routeList;
    }

    public void setRouteList(List<RegisterRoute> routeList) {
        this.routeList = routeList;
    }

}
