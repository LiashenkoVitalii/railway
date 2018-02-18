package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;

//Entity for the table with name "route_rate"
public class RouteRate implements Serializable, Identified {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Integer id;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "route_id")
    private Long routeId;

    public RouteRate() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteRate)) return false;

        RouteRate routeRate = (RouteRate) o;

        if (id != null ? !id.equals(routeRate.id) : routeRate.id != null) return false;
        if (rate != null ? !rate.equals(routeRate.rate) : routeRate.rate != null) return false;
        return routeId != null ? routeId.equals(routeRate.routeId) : routeRate.routeId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RouteRate{");
        sb.append("id=").append(id);
        sb.append(", rate=").append(rate);
        sb.append(", routeId=").append(routeId);
        sb.append('}');
        return sb.toString();
    }
}
