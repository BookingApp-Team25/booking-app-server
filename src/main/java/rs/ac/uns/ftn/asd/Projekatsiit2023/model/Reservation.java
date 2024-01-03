package rs.ac.uns.ftn.asd.Projekatsiit2023.model;

import jakarta.persistence.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.controller.AccommodationController;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;

import java.util.UUID;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID guestId;
    private UUID hostId;
    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    private ReservationStatus reservationStatus;
    @ManyToOne
    @JoinColumn(name = "reserved_date_id")
    private DatePeriod reservedDate;

    public Reservation() {
    }

    public Reservation(UUID guestId, UUID hostId, Accommodation accommodation, ReservationStatus reservationStatus, DatePeriod reservedDate) {
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

    public Boolean isFinished(){
        if(reservationStatus.equals(ReservationStatus.Finished)){
            return true;
        }
        return false;
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
