package com.liashenko.app.persistance.domain;

import com.liashenko.app.persistance.dao.Identified;
import com.liashenko.app.persistance.result_parser.Column;

import java.io.Serializable;
import java.util.Arrays;

//Entity for the table with name "password"
public class Password implements Identified, Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private byte[] password;

    @Column(name = "salt")
    private byte[] salt;

    @Column(name = "iterations")
    private Integer iterations;

    @Column(name = "algorithm")
    private String algorithm;

    public Password() {
    }

    public Password(byte[] password, byte[] salt, Integer iterations, String algorithm) {
        this.password = password;
        this.salt = salt;
        this.iterations = iterations;
        this.algorithm = algorithm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password)) return false;

        Password password1 = (Password) o;

        if (id != null ? !id.equals(password1.id) : password1.id != null) return false;
        if (!Arrays.equals(password, password1.password)) return false;
        if (!Arrays.equals(salt, password1.salt)) return false;
        if (iterations != null ? !iterations.equals(password1.iterations) : password1.iterations != null) return false;
        return algorithm != null ? algorithm.equals(password1.algorithm) : password1.algorithm == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(password);
        result = 31 * result + Arrays.hashCode(salt);
        result = 31 * result + (iterations != null ? iterations.hashCode() : 0);
        result = 31 * result + (algorithm != null ? algorithm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Password{");
        sb.append("id=").append(id);
        sb.append(", password=").append(Arrays.toString(password));
        sb.append(", salt=").append(Arrays.toString(salt));
        sb.append(", iterations=").append(iterations);
        sb.append(", algorithm='").append(algorithm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
