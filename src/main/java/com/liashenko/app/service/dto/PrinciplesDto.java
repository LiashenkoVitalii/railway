package com.liashenko.app.service.dto;

import java.io.Serializable;
import java.util.Arrays;

//Contains entered by user info to sign in
public class PrinciplesDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private char[] password;

    public PrinciplesDto() {
    }

    public PrinciplesDto(String email, char[] password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrinciplesDto)) return false;

        PrinciplesDto that = (PrinciplesDto) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return Arrays.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrinciplesDto{");
        sb.append("email='").append(email).append('\'');
        sb.append(", password=").append(Arrays.toString(password));
        sb.append('}');
        return sb.toString();
    }
}
