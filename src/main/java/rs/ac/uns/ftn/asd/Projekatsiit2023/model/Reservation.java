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
    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private Host host;
    @ManyToOne
    @JoinColumn(name = "guest_id", referencedColumnName = "id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    private ReservationStatus reservationStatus;
    @ManyToOne
    @JoinColumn(name = "reserved_date_id")
    private DatePeriod reservedDate;

    private long price;
    public Reservation() {
    }

    public Reservation(Accommodation accommodation, ReservationStatus reservationStatus, DatePeriod reservedDate, long price) {
        this.accommodation = accommodation;
        this.reservationStatus = reservationStatus;
        this.reservedDate = reservedDate;
        this.price = price;
    }

    public Reservation(Guest guest, Host host, Accommodation accommodation, ReservationStatus reservationStatus, DatePeriod reservedDate, long price) {
        this.guest = guest;
        this.host = host;
        this.accommodation = accommodation;
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

    public UUID getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Host getHost() {
        return host;
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
        if(reservationStatus.equals(ReservationStatus.FINISHED)){
            return true;
        }
        return false;
    }

    public void setReservedDate(DatePeriod reservedDate) {
        this.reservedDate = reservedDate;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
