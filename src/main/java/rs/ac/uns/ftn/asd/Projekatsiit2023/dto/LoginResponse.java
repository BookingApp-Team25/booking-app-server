package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.UUID;

public class LoginResponse {
    private UUID id;
    private String username;
    private Boolean blocked;
    private Role role;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    public LoginResponse(){

    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
