package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationUpdateRequestStatus;

public class AccommodationUpdateRequest {

    private Accommodation accommodation;

    private AccommodationUpdateRequestStatus status;

    public AccommodationUpdateRequest(Accommodation accommodation, AccommodationUpdateRequestStatus status) {
        this.accommodation = accommodation;
        this.status = status;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public AccommodationUpdateRequestStatus getStatus() {
        return status;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public void setStatus(AccommodationUpdateRequestStatus status) {
        this.status = status;
    }
}
