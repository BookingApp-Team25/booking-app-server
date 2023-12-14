package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.Entity;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

@Entity
public class Host extends User{
    public Host() {
    }

    public Host(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
    }
}
