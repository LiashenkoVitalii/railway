package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains first end last terminal stations for the train route
public class FullRouteDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstTerminalStation;
    private String lastTerminalStation;

    public FullRouteDto() {
    }

    public FullRouteDto(String firstTerminalStation, String lastTerminalStation) {
        this.firstTerminalStation = firstTerminalStation;
        this.lastTerminalStation = lastTerminalStation;
    }

    public String getFirstTerminalStation() {
        return firstTerminalStation;
    }

    public void setFirstTerminalStation(String firstTerminalStation) {
        this.firstTerminalStation = firstTerminalStation;
    }

    public String getLastTerminalStation() {
        return lastTerminalStation;
    }

    public void setLastTerminalStation(String lastTerminalStation) {
        this.lastTerminalStation = lastTerminalStation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FullRouteDto)) return false;

        FullRouteDto that = (FullRouteDto) o;

        if (firstTerminalStation != null ? !firstTerminalStation.equals(that.firstTerminalStation) : that.firstTerminalStation != null)
            return false;
        return lastTerminalStation != null ? lastTerminalStation.equals(that.lastTerminalStation) : that.lastTerminalStation == null;
    }

    @Override
    public int hashCode() {
        int result = firstTerminalStation != null ? firstTerminalStation.hashCode() : 0;
        result = 31 * result + (lastTerminalStation != null ? lastTerminalStation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FullRouteDto{");
        sb.append("firstTerminalStation='").append(firstTerminalStation).append('\'');
        sb.append(", lastTerminalStation='").append(lastTerminalStation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
