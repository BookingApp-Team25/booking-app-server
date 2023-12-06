package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateType;

import java.util.UUID;

@Entity
public class AccommodationUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "accommodation_id", referencedColumnName = "id")
    private Accommodation accommodation;
    @Enumerated(EnumType.STRING)
    private AccommodationUpdateType updateType;
    @Enumerated(EnumType.STRING)
    private AccommodationUpdateStatus updateStatus;

    public AccommodationUpdate(Accommodation accommodation, AccommodationUpdateType updateType, AccommodationUpdateStatus updateStatus) {
        this.id = UUID.randomUUID();
        this.accommodation = accommodation;
        this.updateType = updateType;
        this.updateStatus = updateStatus;
    }

    public AccommodationUpdate() {
    }

    public UUID getId() {
        return id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public AccommodationUpdateType getUpdateType() {
        return updateType;
    }

    public AccommodationUpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setUpdateType(AccommodationUpdateType updateType) {
        this.updateType = updateType;
    }

    public void setUpdateStatus(AccommodationUpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }
}
