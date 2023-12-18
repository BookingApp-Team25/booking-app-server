package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
public class Guest extends User {

    public Guest() {
    }

    public Guest(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
    }

}
