package com.aquacount.aquacount.model.measurement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="routes")
public class RouteEntity {

    @Id
    @Column(name="routeid",nullable = false,updatable = false)
    private Long routeid;

    @Column(name="counterid")
    private Long counterid;


    public RouteEntity(Long routeid, Long counterid) {
        this.routeid = routeid;
        this.counterid = counterid;
    }

    public RouteEntity() {
    }

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }

    public Long getCounterid() {
        return counterid;
    }

    public void setCounterid(Long counterid) {
        this.counterid = counterid;
    }
}
