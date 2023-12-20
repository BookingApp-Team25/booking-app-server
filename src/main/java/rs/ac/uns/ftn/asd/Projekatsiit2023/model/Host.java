package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.List;

@Entity
public class Host extends User{

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Accommodation> accommodations;

    public Host() {
    }

    public Host(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }
}
