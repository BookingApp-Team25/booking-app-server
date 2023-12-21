package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.MessageResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.ReservationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.DatePeriodRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImplementation implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;

    private final DatePeriodRepository datePeriodRepository;

    @Autowired
    public ReservationServiceImplementation(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository, DatePeriodRepository datePeriodRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
        this.datePeriodRepository = datePeriodRepository;
    }

    @Override
    public MessageResponse createReservation(ReservationRequest reservationRequest) {
        DatePeriod datePeriod = new DatePeriod(
                reservationRequest.getReservedDate().getStartDate(),
                reservationRequest.getReservedDate().getEndDate()
        );

        datePeriodRepository.save(datePeriod);

        Accommodation accommodation = accommodationRepository.findById(reservationRequest.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Accommodation not found with id: " + reservationRequest.getAccommodationId()));

        Reservation reservation = new Reservation(
                reservationRequest.getGuestId(),
                reservationRequest.getHostId(),
                accommodation,
                ReservationStatus.Ongoing,
                datePeriod
        );

        reservationRepository.save(reservation);

        //return convertToDto(reservation);
        return new MessageResponse(true,"succesfuly added new reservation");
    }

    @Override
    public ReservationResponse getReservation() {
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllHostReservations(UUID hostId) {
        List<Reservation> hostReservations = reservationRepository.findAllByHostId(hostId);

        return hostReservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public Collection<ReservationResponse> getFilteredHostReservations(int hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus){
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllAccommodationReservations(UUID accommodationId) {
        List<Reservation> accommodationReservations = reservationRepository.findAllByAccommodationId(accommodationId);

        return accommodationReservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ReservationResponse> getAllGuestReservations(int guestId) {
        return null;
    }

    @Override
    public boolean deleteReservation(UUID reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    }

    @Override
    public boolean cancelReservation(UUID reservationId) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(ReservationStatus.Canceled);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    @Override
    public boolean acceptReservation(UUID reservationId){
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(ReservationStatus.Accepted);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    // Helper method to convert Reservation entity to Response DTO
    private ReservationResponse convertToDto(Reservation reservation) {
        return new ReservationResponse(
                reservation.getGuestId(),
                reservation.getHostId(),
                reservation.getAccommodation().getId(),
                reservation.getReservationStatus(),
                reservation.getReservedDate()
        );
    }
}
