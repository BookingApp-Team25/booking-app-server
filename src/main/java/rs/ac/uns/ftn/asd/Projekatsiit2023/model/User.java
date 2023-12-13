package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private Boolean blocked;

    public User() {}

    public User(UUID id, String username, String password, Boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.blocked = blocked;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }
}
