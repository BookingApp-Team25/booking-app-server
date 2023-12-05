package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.util.Collection;

public interface ReservationService {
    public ReservationResponse createReservation(ReservationRequest reservationRequest);
    public ReservationResponse getReservation();
    public Collection<ReservationResponse> getAllHostReservations(int hostId);
    public Collection<ReservationResponse> getAllAccommodationReservations(int accommodationId);
    public Collection<ReservationResponse> getAllGuestReservations(int guestId);
    public Collection<ReservationResponse> getFilteredHostReservations(int hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus);
    public String acceptReservation(int reservationId);
    public boolean deleteReservation(int reservationId);
    public boolean cancelReservation(int reservationId);

}
