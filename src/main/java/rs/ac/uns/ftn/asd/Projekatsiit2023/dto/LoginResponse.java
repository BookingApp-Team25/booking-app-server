package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import jakarta.persistence.Transient;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.UUID;

public class LoginResponse {

    private String jwt;

    public LoginResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
