package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import ch.qos.logback.core.joran.sanity.Pair;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
public class AccommodationReservedDates {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @OneToMany(mappedBy = "accommodationReservedDates",cascade = CascadeType.ALL)
    private List<AccommodationReservationPair> reservations; // dateperiod and guest pair

    public AccommodationReservedDates(){
        this.reservations = new ArrayList<>();
    }
    public AccommodationReservedDates(List<AccommodationReservationPair> reservedDates) {
        this.reservations = reservedDates;
    }

    public List<AccommodationReservationPair> getReservations() {
        return reservations;
    }

    public void setReservations(List<AccommodationReservationPair> reservations) {
        this.reservations = reservations;
    }
}

