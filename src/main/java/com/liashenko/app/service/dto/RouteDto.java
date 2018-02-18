package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains info about user's rout
public class RouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long fromStationId;
    private Long toStationId;
    private String fromStationName;
    private String toStationName;
    private String dateString;

    public RouteDto() {
    }

    public RouteDto(Long fromStationId, Long toStationId, String fromStationName, String toStationName, String dateString) {
        this.fromStationId = fromStationId;
        this.toStationId = toStationId;
        this.fromStationName = fromStationName;
        this.toStationName = toStationName;
        this.dateString = dateString;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getFromStationId() {
        return fromStationId;
    }

    public Long getToStationId() {
        return toStationId;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public String getDateString() {
        return dateString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RouteDto)) return false;

        RouteDto routeDto = (RouteDto) o;

        if (fromStationId != null ? !fromStationId.equals(routeDto.fromStationId) : routeDto.fromStationId != null)
            return false;
        if (toStationId != null ? !toStationId.equals(routeDto.toStationId) : routeDto.toStationId != null)
            return false;
        if (fromStationName != null ? !fromStationName.equals(routeDto.fromStationName) : routeDto.fromStationName != null)
            return false;
        if (toStationName != null ? !toStationName.equals(routeDto.toStationName) : routeDto.toStationName != null)
            return false;
        return dateString != null ? dateString.equals(routeDto.dateString) : routeDto.dateString == null;
    }

    @Override
    public int hashCode() {
        int result = fromStationId != null ? fromStationId.hashCode() : 0;
        result = 31 * result + (toStationId != null ? toStationId.hashCode() : 0);
        result = 31 * result + (fromStationName != null ? fromStationName.hashCode() : 0);
        result = 31 * result + (toStationName != null ? toStationName.hashCode() : 0);
        result = 31 * result + (dateString != null ? dateString.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RouteDto{");
        sb.append("fromStationId=").append(fromStationId);
        sb.append(", toStationId=").append(toStationId);
        sb.append(", fromStationName='").append(fromStationName).append('\'');
        sb.append(", toStationName='").append(toStationName).append('\'');
        sb.append(", dateString='").append(dateString).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static final class Builder {
        private Long fromStationId;
        private Long toStationId;
        private String fromStationName;
        private String toStationName;
        private String dateString;

        private Builder() {
        }

        public Builder fromStationId(Long fromStationId) {
            this.fromStationId = fromStationId;
            return this;
        }

        public Builder toStationId(Long toStationId) {
            this.toStationId = toStationId;
            return this;
        }

        public Builder fromStationName(String fromStationName) {
            this.fromStationName = fromStationName;
            return this;
        }

        public Builder toStationName(String toStationName) {
            this.toStationName = toStationName;
            return this;
        }

        public Builder dateString(String dateString) {
            this.dateString = dateString;
            return this;
        }

        public RouteDto build() {
            return new RouteDto(fromStationId, toStationId, fromStationName, toStationName, dateString);
        }
    }
}
