package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;

//Entity for the table with name "vagon_type"
public class VagonType implements Serializable, Identified {

    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Integer id;

    @Column(name = "type_name", useLocaleSuffix = true)
    private String typeName;

    @Column(name = "places_count")
    private Integer placesCount;

    public VagonType() {
    }

    public VagonType(Integer id, String typeName, Integer placesCount) {
        this.id = id;
        this.typeName = typeName;
        this.placesCount = placesCount;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(Integer placesCount) {
        this.placesCount = placesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VagonType)) return false;

        VagonType vagonType = (VagonType) o;

        if (id != null ? !id.equals(vagonType.id) : vagonType.id != null) return false;
        if (typeName != null ? !typeName.equals(vagonType.typeName) : vagonType.typeName != null) return false;
        return placesCount != null ? placesCount.equals(vagonType.placesCount) : vagonType.placesCount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (placesCount != null ? placesCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VagonType{");
        sb.append("id=").append(id);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", placesCount=").append(placesCount);
        sb.append('}');
        return sb.toString();
    }
}
