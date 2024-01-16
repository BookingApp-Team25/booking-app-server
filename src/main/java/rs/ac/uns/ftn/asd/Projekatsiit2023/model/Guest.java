package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.*;

@Entity
@Table(name = "guest")
public class Guest extends User {

    public Guest() {
    }

    public Guest(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
    }

}
