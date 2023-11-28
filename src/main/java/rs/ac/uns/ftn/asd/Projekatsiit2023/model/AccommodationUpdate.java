package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;

import java.util.UUID;

public class AccommodationUpdate {

    private UUID id;
    private Accommodation accommodation;

    private AccommodationUpdateStatus status;

    public AccommodationUpdate(Accommodation accommodation, AccommodationUpdateStatus status) {
        this.accommodation = accommodation;
        this.status = status;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public AccommodationUpdateStatus getStatus() {
        return status;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setStatus(AccommodationUpdateStatus status) {
        this.status = status;
    }
}
