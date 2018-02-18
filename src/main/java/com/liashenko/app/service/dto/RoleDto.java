package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains list of role's ids
public class RoleDto implements Serializable {
    public static final long ADMIN_ROLE_ID = 1;
    public static final long USER_ROLE_ID = 2;
    public static final long GUEST_ROLE_ID = 0;
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;

    public RoleDto() {
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof RoleDto)) return false;

        RoleDto roleDto = (RoleDto) o;

        if (id != null ? !id.equals(roleDto.id) : roleDto.id != null) return false;
        return name != null ? name.equals(roleDto.name) : roleDto.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RoleDto{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
