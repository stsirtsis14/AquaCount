package com.aquacount.aquacount.model.measurement.view;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MyId implements Serializable {
    @Column(name = "counterid")
    private Long _counterid;
    private Long routeid;

    public MyId(Long counterid, Long routeid) {
        this._counterid = counterid;
        this.routeid = routeid;
    }

    public Long getCounterid() {
        return _counterid;
    }

    public void setCounterid(Long counterid) {
        this._counterid = counterid;
    }

    public Long getRouteid() {
        return routeid;
    }

    public void setRouteid(Long routeid) {
        this.routeid = routeid;
    }


    // standard getters and setters
}
