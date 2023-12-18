package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.time.LocalDateTime;
import java.util.Date;
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
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private UUID activationCode;
    private LocalDateTime activationTime;
    @Enumerated(EnumType.STRING)
    private Role role;
    public User(){

    }

    public User(String username, String password, String firstName, String lastName, String address, String phoneNumber,Role role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.blocked = true;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.activationCode=UUID.randomUUID();
        this.activationTime= LocalDateTime.now();
    }

    public UUID getId() {
        return id;
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

    public void setId(UUID id) {
        this.id = id;
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

    public Role getRole() {
        return this.role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UUID getActivationCode() {
        return activationCode;
    }

    public LocalDateTime getActivationTime() {
        return activationTime;
    }
}
