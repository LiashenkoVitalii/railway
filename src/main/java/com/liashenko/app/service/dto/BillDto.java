package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains all required data to show the bill on the front-end
public class BillDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String fromStationName;
    private Long fromStationId;
    private String toStationName;
    private String fromCityName;
    private Long toStationId;
    private String fromStationLeavingDate;
    private String toStationArrivalDate;
    private String vagonTypeName;
    private Integer vagonTypeId;
    private Integer vagonNumber;
    private String trainNumber;
    private Integer placeNumber;
    private Float ticketPrice;
    private String ticketNumber;
    private String ticketDate;

    public BillDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFromStationName() {
        return fromStationName;
    }

    public void setFromStationName(String fromStationName) {
        this.fromStationName = fromStationName;
    }

    public Long getFromStationId() {
        return fromStationId;
    }

    public void setFromStationId(Long fromStationId) {
        this.fromStationId = fromStationId;
    }

    public String getToStationName() {
        return toStationName;
    }

    public void setToStationName(String toStationName) {
        this.toStationName = toStationName;
    }

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public Long getToStationId() {
        return toStationId;
    }

    public void setToStationId(Long toStationId) {
        this.toStationId = toStationId;
    }

    public String getFromStationLeavingDate() {
        return fromStationLeavingDate;
    }

    public void setFromStationLeavingDate(String fromStationLeavingDate) {
        this.fromStationLeavingDate = fromStationLeavingDate;
    }

    public String getToStationArrivalDate() {
        return toStationArrivalDate;
    }

    public void setToStationArrivalDate(String toStationArrivalDate) {
        this.toStationArrivalDate = toStationArrivalDate;
    }

    public String getVagonTypeName() {
        return vagonTypeName;
    }

    public void setVagonTypeName(String vagonTypeName) {
        this.vagonTypeName = vagonTypeName;
    }

    public Integer getVagonTypeId() {
        return vagonTypeId;
    }

    public void setVagonTypeId(Integer vagonTypeId) {
        this.vagonTypeId = vagonTypeId;
    }

    public Integer getVagonNumber() {
        return vagonNumber;
    }

    public void setVagonNumber(Integer vagonNumber) {
        this.vagonNumber = vagonNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Integer getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(Integer placeNumber) {
        this.placeNumber = placeNumber;
    }

    public Float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(String ticketDate) {
        this.ticketDate = ticketDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillDto)) return false;

        BillDto billDto = (BillDto) o;

        if (firstName != null ? !firstName.equals(billDto.firstName) : billDto.firstName != null) return false;
        if (lastName != null ? !lastName.equals(billDto.lastName) : billDto.lastName != null) return false;
        if (fromStationName != null ? !fromStationName.equals(billDto.fromStationName) : billDto.fromStationName != null)
            return false;
        if (fromStationId != null ? !fromStationId.equals(billDto.fromStationId) : billDto.fromStationId != null)
            return false;
        if (toStationName != null ? !toStationName.equals(billDto.toStationName) : billDto.toStationName != null)
            return false;
        if (fromCityName != null ? !fromCityName.equals(billDto.fromCityName) : billDto.fromCityName != null)
            return false;
        if (toStationId != null ? !toStationId.equals(billDto.toStationId) : billDto.toStationId != null) return false;
        if (fromStationLeavingDate != null ? !fromStationLeavingDate.equals(billDto.fromStationLeavingDate) : billDto.fromStationLeavingDate != null)
            return false;
        if (toStationArrivalDate != null ? !toStationArrivalDate.equals(billDto.toStationArrivalDate) : billDto.toStationArrivalDate != null)
            return false;
        if (vagonTypeName != null ? !vagonTypeName.equals(billDto.vagonTypeName) : billDto.vagonTypeName != null)
            return false;
        if (vagonTypeId != null ? !vagonTypeId.equals(billDto.vagonTypeId) : billDto.vagonTypeId != null) return false;
        if (vagonNumber != null ? !vagonNumber.equals(billDto.vagonNumber) : billDto.vagonNumber != null) return false;
        if (trainNumber != null ? !trainNumber.equals(billDto.trainNumber) : billDto.trainNumber != null) return false;
        if (placeNumber != null ? !placeNumber.equals(billDto.placeNumber) : billDto.placeNumber != null) return false;
        if (ticketPrice != null ? !ticketPrice.equals(billDto.ticketPrice) : billDto.ticketPrice != null) return false;
        if (ticketNumber != null ? !ticketNumber.equals(billDto.ticketNumber) : billDto.ticketNumber != null)
            return false;
        return ticketDate != null ? ticketDate.equals(billDto.ticketDate) : billDto.ticketDate == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (fromStationName != null ? fromStationName.hashCode() : 0);
        result = 31 * result + (fromStationId != null ? fromStationId.hashCode() : 0);
        result = 31 * result + (toStationName != null ? toStationName.hashCode() : 0);
        result = 31 * result + (fromCityName != null ? fromCityName.hashCode() : 0);
        result = 31 * result + (toStationId != null ? toStationId.hashCode() : 0);
        result = 31 * result + (fromStationLeavingDate != null ? fromStationLeavingDate.hashCode() : 0);
        result = 31 * result + (toStationArrivalDate != null ? toStationArrivalDate.hashCode() : 0);
        result = 31 * result + (vagonTypeName != null ? vagonTypeName.hashCode() : 0);
        result = 31 * result + (vagonTypeId != null ? vagonTypeId.hashCode() : 0);
        result = 31 * result + (vagonNumber != null ? vagonNumber.hashCode() : 0);
        result = 31 * result + (trainNumber != null ? trainNumber.hashCode() : 0);
        result = 31 * result + (placeNumber != null ? placeNumber.hashCode() : 0);
        result = 31 * result + (ticketPrice != null ? ticketPrice.hashCode() : 0);
        result = 31 * result + (ticketNumber != null ? ticketNumber.hashCode() : 0);
        result = 31 * result + (ticketDate != null ? ticketDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BillDto{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", fromStationName='").append(fromStationName).append('\'');
        sb.append(", fromStationId=").append(fromStationId);
        sb.append(", toStationName='").append(toStationName).append('\'');
        sb.append(", fromCityName='").append(fromCityName).append('\'');
        sb.append(", toStationId=").append(toStationId);
        sb.append(", fromStationLeavingDate='").append(fromStationLeavingDate).append('\'');
        sb.append(", toStationArrivalDate='").append(toStationArrivalDate).append('\'');
        sb.append(", vagonTypeName='").append(vagonTypeName).append('\'');
        sb.append(", vagonTypeId=").append(vagonTypeId);
        sb.append(", vagonNumber=").append(vagonNumber);
        sb.append(", trainNumber='").append(trainNumber).append('\'');
        sb.append(", placeNumber=").append(placeNumber);
        sb.append(", ticketPrice=").append(ticketPrice);
        sb.append(", ticketNumber='").append(ticketNumber).append('\'');
        sb.append(", ticketDate=").append(ticketDate);
        sb.append('}');
        return sb.toString();
    }
}
