package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;
import java.time.LocalDateTime;

//Entity for the table with name "timetable"
public class TimeTable implements Serializable, Identified {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    @Column(name = "station_id")
    private Long stationId;

    @Column(name = "departure")
    private LocalDateTime departure;

    @Column(name = "arrival")
    private LocalDateTime arrival;

    @Column(name = "route_number_id")
    private Long routeNumberId;

    public TimeTable() {
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

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public Long getRouteNumberId() {
        return routeNumberId;
    }

    public void setRouteNumberId(Long routeNumberId) {
        this.routeNumberId = routeNumberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeTable)) return false;

        TimeTable timeTable = (TimeTable) o;

        if (id != null ? !id.equals(timeTable.id) : timeTable.id != null) return false;
        if (stationId != null ? !stationId.equals(timeTable.stationId) : timeTable.stationId != null) return false;
        if (departure != null ? !departure.equals(timeTable.departure) : timeTable.departure != null) return false;
        if (arrival != null ? !arrival.equals(timeTable.arrival) : timeTable.arrival != null) return false;
        return routeNumberId != null ? routeNumberId.equals(timeTable.routeNumberId) : timeTable.routeNumberId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (stationId != null ? stationId.hashCode() : 0);
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        result = 31 * result + (arrival != null ? arrival.hashCode() : 0);
        result = 31 * result + (routeNumberId != null ? routeNumberId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeTable{");
        sb.append("id=").append(id);
        sb.append(", stationId=").append(stationId);
        sb.append(", departure=").append(departure);
        sb.append(", arrival=").append(arrival);
        sb.append(", routeNumberId=").append(routeNumberId);
        sb.append('}');
        return sb.toString();
    }
}
