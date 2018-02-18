package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains required data for calculation the price of ticket for
//specified route, type of vagon and distance
public class PriceForVagonDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer vagonTypeId;
    private String vagonTypeName;
    private Float ticketPrice;
    private Float distance;
    private Float routeRate;

    public PriceForVagonDto() {
    }

    public Integer getVagonTypeId() {
        return vagonTypeId;
    }

    public void setVagonTypeId(Integer vagonTypeId) {
        this.vagonTypeId = vagonTypeId;
    }

    public String getVagonTypeName() {
        return vagonTypeName;
    }

    public void setVagonTypeName(String vagonTypeName) {
        this.vagonTypeName = vagonTypeName;
    }

    public Float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Float getRouteRate() {
        return routeRate;
    }

    public void setRouteRate(Float routeRate) {
        this.routeRate = routeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceForVagonDto)) return false;

        PriceForVagonDto that = (PriceForVagonDto) o;

        if (vagonTypeId != null ? !vagonTypeId.equals(that.vagonTypeId) : that.vagonTypeId != null) return false;
        if (vagonTypeName != null ? !vagonTypeName.equals(that.vagonTypeName) : that.vagonTypeName != null)
            return false;
        if (ticketPrice != null ? !ticketPrice.equals(that.ticketPrice) : that.ticketPrice != null) return false;
        if (distance != null ? !distance.equals(that.distance) : that.distance != null) return false;
        return routeRate != null ? routeRate.equals(that.routeRate) : that.routeRate == null;
    }

    @Override
    public int hashCode() {
        int result = vagonTypeId != null ? vagonTypeId.hashCode() : 0;
        result = 31 * result + (vagonTypeName != null ? vagonTypeName.hashCode() : 0);
        result = 31 * result + (ticketPrice != null ? ticketPrice.hashCode() : 0);
        result = 31 * result + (distance != null ? distance.hashCode() : 0);
        result = 31 * result + (routeRate != null ? routeRate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PriceForVagonDto{");
        sb.append("vagonTypeId=").append(vagonTypeId);
        sb.append(", vagonTypeName='").append(vagonTypeName).append('\'');
        sb.append(", ticketPrice=").append(ticketPrice);
        sb.append(", distance=").append(distance);
        sb.append(", routeRate=").append(routeRate);
        sb.append('}');
        return sb.toString();
    }
}
