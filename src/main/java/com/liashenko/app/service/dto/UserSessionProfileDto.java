package com.liashenko.app.service.dto;

import java.io.Serializable;

//Contains info about user shoud be stored in the http-session
public class UserSessionProfileDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long userRoleId;
    private String language;

    public UserSessionProfileDto() {
    }

    public UserSessionProfileDto(Long userId, Long userRoleId, String language) {
        this.userId = userId;
        this.userRoleId = userRoleId;
        this.language = language;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSessionProfileDto)) return false;

        UserSessionProfileDto that = (UserSessionProfileDto) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userRoleId != null ? !userRoleId.equals(that.userRoleId) : that.userRoleId != null) return false;
        return language != null ? language.equals(that.language) : that.language == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userRoleId != null ? userRoleId.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserSessionProfileDto{");
        sb.append("userId=").append(userId);
        sb.append(", userRoleId=").append(userRoleId);
        sb.append(", language='").append(language).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
