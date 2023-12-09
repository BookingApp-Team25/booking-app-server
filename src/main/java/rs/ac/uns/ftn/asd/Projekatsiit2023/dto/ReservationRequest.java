package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.UUID;

public class ReservationRequest {
    private UUID guestId;

    private UUID hostId;

    private Accommodation accommodation;

    private ReservationStatus reservationStatus;

    private DatePeriod reservedDate;

    public ReservationRequest() {
    }

    public ReservationRequest(UUID guestId, UUID hostId, Accommodation accommodation, ReservationStatus reservationStatus, DatePeriod reservedDate) {
        this.guestId = guestId;
        this.hostId = hostId;
        this.accommodation = accommodation;
        this.reservationStatus = reservationStatus;
        this.reservedDate = reservedDate;
    }

    public UUID getGuestId() {
        return guestId;
    }

    public UUID getHostId() {
        return hostId;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public DatePeriod getReservedDate() {
        return reservedDate;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setReservedDate(DatePeriod reservedDate) {
        this.reservedDate = reservedDate;
    }

    public void setGuestId(UUID guestId) {
        this.guestId = guestId;
    }

    public void setHostId(UUID hostId) {
        this.hostId = hostId;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
