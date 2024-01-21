package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;

import java.util.UUID;

public class AccommodationUpdateResponse {
    private UUID id;
    private Accommodation accommodation;
    private AccommodationUpdateStatus status;

    public AccommodationUpdateResponse(){

    }

    public AccommodationUpdateResponse(UUID id, Accommodation accommodation, AccommodationUpdateStatus status) {
        this.id = id;
        this.accommodation = accommodation;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public AccommodationUpdateStatus getStatus() {
        return status;
    }

    public void setStatus(AccommodationUpdateStatus status) {
        this.status = status;
    }
}
