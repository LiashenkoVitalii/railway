package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;

//Entity for the table with name "station"
public class Station implements Serializable, Identified {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    @Column(name = "city", useLocaleSuffix = true)
    private String city;

    @Column(name = "name", useLocaleSuffix = true)
    private String name;

    public Station() {
    }

    public Station(Long id, String city, String name) {
        this.id = id;
        this.city = city;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;

        Station station1 = (Station) o;

        if (id != null ? !id.equals(station1.id) : station1.id != null) return false;
        if (city != null ? !city.equals(station1.city) : station1.city != null) return false;
        return name != null ? name.equals(station1.name) : station1.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Station{");
        sb.append("id=").append(id);
        sb.append(", city='").append(city).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
