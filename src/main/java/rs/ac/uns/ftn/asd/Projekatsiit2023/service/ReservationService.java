package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public interface ReservationService {
    public MessageResponse createReservation(ReservationRequest reservationRequest);
    public HostReservationCollectionResponse getAllUnresolvedHostReservations(UUID hostId, int page, int numberOfElements);
    public MessageResponse resolveReservation(UUID reservationId, boolean isAccepted);
    public ReservationResponse getReservation();
    public HostReservationCollectionResponse getAllHostReservations(UUID hostId, int page, int numberOfElements);
    public Collection<ReservationResponse> getAllAccommodationReservations(UUID accommodationId);
    public Collection<ReservationResponse> getAllGuestReservations(int guestId);
    public Collection<ReservationResponse> getFilteredHostReservations(int hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus);
    public boolean acceptReservation(UUID reservationId);
    public boolean rejectReservation(UUID reservationId);
    public boolean deleteReservation(UUID reservationId);
    public boolean cancelReservation(UUID reservationId);

    public ReservationSummaryCollectionResponse getFilteredGuestReservations(UUID hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus, int page, int numberOfElements) throws IOException;

}
