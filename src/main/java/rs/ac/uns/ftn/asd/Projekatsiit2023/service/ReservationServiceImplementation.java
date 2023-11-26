package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;
@Service
public class ReservationServiceImplementation implements ReservationService{
    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest) { return new ReservationResponse();
    }

    @Override
    public ReservationResponse getReservation() {
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllHostReservations(int hostId) {
        return null;
    }
    public Collection<ReservationResponse> getFilteredHostReservations(int hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus){
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllAccommodationReservations(int accommodationId) {
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllGuestReservations(int guestId) {
        return null;
    }

    @Override
    public boolean deleteReservation(int reservationId) { return false; } //treba namestiti brisanje rezervacije

    @Override
    public boolean cancelReservation(int reservationId) { return false; } //treba namestiti otkaz rezervacije

    public ReservationResponse acceptReservation(int reservationId){
        return null;
    }
}
