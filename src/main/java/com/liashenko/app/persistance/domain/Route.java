package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;

//Entity for the table with name "route"
public class Route implements Serializable, Identified {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "rout_number_id")
    private Long routeNumberId;

    @Column(name = "distance")
    private Float distance;

    public Route() {
    }

    public Route(Long id, Long stationId, Long routeNumberId, Float distance) {
        this.id = id;
        this.stationId = stationId;
        this.routeNumberId = routeNumberId;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getRouteNumberId() {
        return routeNumberId;
    }

    public void setRouteNumberId(Long routeNumberId) {
        this.routeNumberId = routeNumberId;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;

        Route route = (Route) o;

        if (id != null ? !id.equals(route.id) : route.id != null) return false;
        if (stationId != null ? !stationId.equals(route.stationId) : route.stationId != null) return false;
        if (routeNumberId != null ? !routeNumberId.equals(route.routeNumberId) : route.routeNumberId != null)
            return false;
        return distance != null ? distance.equals(route.distance) : route.distance == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (routeNumberId != null ? routeNumberId.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Route{");
        sb.append("id=").append(id);
        sb.append(", stationId=").append(stationId);
        sb.append(", routeNumberId=").append(routeNumberId);
        sb.append(", distance=").append(distance);
        sb.append('}');
        return sb.toString();
    }
}
