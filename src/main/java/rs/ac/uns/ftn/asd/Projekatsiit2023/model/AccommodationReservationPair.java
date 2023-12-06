package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class AccommodationReservationPair {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @Column(name = "datePeriod", updatable = false)
    private UUID datePeriod;
    @Column(name = "guest", updatable = false)
    private UUID guest;
    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private AccommodationReservedDates accommodationReservedDates;
    public AccommodationReservationPair() {
    }

    public AccommodationReservationPair(UUID id, UUID datePeriod, UUID guest) {
        this.id = id;
        this.datePeriod = datePeriod;
        this.guest = guest;
    }

    public UUID getId() {
        return id;
    }

    public UUID getDatePeriod() {
        return datePeriod;
    }

    public UUID getGuest() {
        return guest;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDatePeriod(UUID datePeriod) {
        this.datePeriod = datePeriod;
    }

    public void setGuest(UUID guest) {
        this.guest = guest;
    }
}
