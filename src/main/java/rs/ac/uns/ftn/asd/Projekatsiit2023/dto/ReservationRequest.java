package rs.ac.uns.ftn.asd.Projekatsiit2023.dto;

import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.UUID;

public class ReservationRequest {
    private UUID guestId;

    private UUID hostId;

    private UUID accommodationId;

    private ReservationStatus reservationStatus;

    private DatePeriod reservedDate;

    private long price;

    public ReservationRequest() {
    }

    public ReservationRequest(UUID guestId, UUID hostId, UUID accommodationId, ReservationStatus reservationStatus, DatePeriod reservedDate, long price) {
        this.guestId = guestId;
        this.hostId = hostId;
        this.accommodationId = accommodationId;
        this.reservationStatus = reservationStatus;
        this.reservedDate = reservedDate;
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public UUID getGuestId() {
        return guestId;
    }

    public UUID getHostId() {
        return hostId;
    }

    public UUID getAccommodationId() {
        return accommodationId;
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

    public void setAccommodationId(UUID accommodationId) {
        this.accommodationId = accommodationId;
    }
}
