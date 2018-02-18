package com.liashenko.app.service.dto;

import java.io.Serializable;
import java.util.Arrays;


//Contains user's personal info. Dto uset to remain info when user registers or updates his profile
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long roleId;
    private char[] password;
    private String language;
    private Boolean isBanned;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, Long roleId,
                   char[] password, Boolean isBanned, String language) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roleId = roleId;
        this.password = password;
        this.language = language;
        this.isBanned = isBanned;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getLanguage() {
        return language;
    }

    public char[] getPassword() {
        return password;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;

        UserDto that = (UserDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (!Arrays.equals(password, that.password)) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        return isBanned != null ? isBanned.equals(that.isBanned) : that.isBanned == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (isBanned != null ? isBanned.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", roleId=").append(roleId);
        sb.append(", password=").append(Arrays.toString(password));
        sb.append(", language='").append(language).append('\'');
        sb.append(", isBanned=").append(isBanned);
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private Long userId;
        private String firstName;
        private String lastName;
        private String email;
        private Long roleId;
        private String language;
        private char[] password;
        private Boolean isBanned;

        private Builder() {
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder roleId(Long roleId) {
            this.roleId = roleId;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder password(char[] password) {
            this.password = password;
            return this;
        }

        public Builder isBanned(Boolean isBanned) {
            this.isBanned = isBanned;
            return this;
        }

        public UserDto build() {
            return new UserDto(userId, firstName, lastName, email, roleId, password, isBanned, language);
        }
    }
}
