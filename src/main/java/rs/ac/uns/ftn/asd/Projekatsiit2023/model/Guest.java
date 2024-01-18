package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guest")
public class Guest extends User {

    @ManyToMany
    @JoinTable(
            name = "favorite_accommodations",
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id")
    )
    private List<Accommodation> favoriteAccommodations;

    public Guest() {
        this.favoriteAccommodations = new ArrayList<>();
    }

    public Guest(String username, String password, String firstName, String lastName, String address, String phoneNumber, Role role) {
        super(username, password, firstName, lastName, address, phoneNumber, role);
        this.favoriteAccommodations = new ArrayList<>();
    }

    public List<Accommodation> getFavoriteAccommodations() {
        return favoriteAccommodations;
    }

    public void addFavoriteAccommodation(Accommodation accommodation) {
        this.favoriteAccommodations.add(accommodation);
    }

    public void removeFavoriteAccommodation(Accommodation accommodation) {
        this.favoriteAccommodations.remove(accommodation);
    }
}
