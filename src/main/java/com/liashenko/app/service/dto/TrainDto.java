package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains all info about train according to user's requirements
public class TrainDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long stationFromId;
    private Long stationToId;
    private Long trainId;
    private Long routeId;
    private String fromStation;
    private String toStation;
    private String trainNumber;
    private String leavingDate;
    private String arrivalDate;

    public TrainDto() {
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getStationFromId() {
        return stationFromId;
    }

    public void setStationFromId(Long stationFromId) {
        this.stationFromId = stationFromId;
    }

    public Long getStationToId() {
        return stationToId;
    }

    public void setStationToId(Long stationToId) {
        this.stationToId = stationToId;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getLeavingDate() {
        return leavingDate;
    }

    public void setLeavingDate(String leavingDate) {
        this.leavingDate = leavingDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainDto)) return false;

        TrainDto trainDto = (TrainDto) o;

        if (stationFromId != null ? !stationFromId.equals(trainDto.stationFromId) : trainDto.stationFromId != null)
            return false;
        if (stationToId != null ? !stationToId.equals(trainDto.stationToId) : trainDto.stationToId != null)
            return false;
        if (trainId != null ? !trainId.equals(trainDto.trainId) : trainDto.trainId != null) return false;
        if (routeId != null ? !routeId.equals(trainDto.routeId) : trainDto.routeId != null) return false;
        if (fromStation != null ? !fromStation.equals(trainDto.fromStation) : trainDto.fromStation != null)
            return false;
        if (toStation != null ? !toStation.equals(trainDto.toStation) : trainDto.toStation != null) return false;
        if (trainNumber != null ? !trainNumber.equals(trainDto.trainNumber) : trainDto.trainNumber != null)
            return false;
        if (leavingDate != null ? !leavingDate.equals(trainDto.leavingDate) : trainDto.leavingDate != null)
            return false;
        return arrivalDate != null ? arrivalDate.equals(trainDto.arrivalDate) : trainDto.arrivalDate == null;
    }

    @Override
    public int hashCode() {
        int result = stationFromId != null ? stationFromId.hashCode() : 0;
        result = 31 * result + (stationToId != null ? stationToId.hashCode() : 0);
        result = 31 * result + (trainId != null ? trainId.hashCode() : 0);
        result = 31 * result + (routeId != null ? routeId.hashCode() : 0);
        result = 31 * result + (fromStation != null ? fromStation.hashCode() : 0);
        result = 31 * result + (toStation != null ? toStation.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (leavingDate != null ? leavingDate.hashCode() : 0);
        result = 31 * result + (arrivalDate != null ? arrivalDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TrainDto{");
        sb.append("stationFromId=").append(stationFromId);
        sb.append(", stationToId=").append(stationToId);
        sb.append(", trainId=").append(trainId);
        sb.append(", routeId=").append(routeId);
        sb.append(", fromStation='").append(fromStation).append('\'');
        sb.append(", toStation='").append(toStation).append('\'');
        sb.append(", trainNumber='").append(trainNumber).append('\'');
        sb.append(", leavingDate='").append(leavingDate).append('\'');
        sb.append(", arrivalDate='").append(arrivalDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
