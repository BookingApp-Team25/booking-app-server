package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;

public class AccommodationUpdateResponse {
    private Accommodation accommodation;
    private AccommodationUpdateStatus status;

    public AccommodationUpdateResponse(){

    }

    public AccommodationUpdateResponse(Accommodation accommodation, AccommodationUpdateStatus status) {
        this.accommodation = accommodation;
        this.status = status;
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
