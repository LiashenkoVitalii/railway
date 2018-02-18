package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;

//Entity for the table with name "price_per_km_for_vagon"
public class PricePerKmForVagon implements Serializable, Identified {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "vagon_type_id")
    private Integer vagonTypeId;

    public PricePerKmForVagon() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getVagonTypeId() {
        return vagonTypeId;
    }

    public void setVagonTypeId(Integer vagonTypeId) {
        this.vagonTypeId = vagonTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PricePerKmForVagon)) return false;

        PricePerKmForVagon that = (PricePerKmForVagon) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return vagonTypeId != null ? vagonTypeId.equals(that.vagonTypeId) : that.vagonTypeId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (vagonTypeId != null ? vagonTypeId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PricePerKmForVagon{");
        sb.append("id=").append(id);
        sb.append(", price=").append(price);
        sb.append(", vagonTypeId=").append(vagonTypeId);
        sb.append('}');
        return sb.toString();
    }
}
