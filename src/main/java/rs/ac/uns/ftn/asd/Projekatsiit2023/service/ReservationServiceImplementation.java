package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.ReservationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.Repository.DatePeriodRepository;
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
    private final AccommodationRepository accommodationRepository;  // Assuming you have an Accommodation repository

    private final DatePeriodRepository datePeriodRepository;

    @Autowired
    public ReservationServiceImplementation(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository, DatePeriodRepository datePeriodRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
        this.datePeriodRepository = datePeriodRepository;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        // Create DatePeriod entity
        DatePeriod datePeriod = new DatePeriod(
                reservationRequest.getReservedDate().getStartDate(),
                reservationRequest.getReservedDate().getEndDate()
        );

        // Save DatePeriod entity to the database
        datePeriodRepository.save(datePeriod);

        // Fetch the Accommodation entity from the database based on the provided accommodationId
        Accommodation accommodation = accommodationRepository.findById(reservationRequest.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Accommodation not found with id: " + reservationRequest.getAccommodationId()));

        // Convert DTO to entity
        Reservation reservation = new Reservation(
                reservationRequest.getGuestId(),
                reservationRequest.getHostId(),
                accommodation,  // Use the fetched Accommodation entity
                ReservationStatus.Ongoing,  // Set initial status
                datePeriod  // Use the persisted DatePeriod entity
        );

        // Save the reservation entity to the database
        reservationRepository.save(reservation);

        // Convert the created reservation entity to a response DTO
        return convertToDto(reservation);
    }

    @Override
    public ReservationResponse getReservation() {
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllHostReservations(UUID hostId) {
        // Retrieve all reservations for the given host
        List<Reservation> hostReservations = reservationRepository.findAllByHostId(hostId);

        // Convert the list of reservation entities to a list of response DTOs
        return hostReservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public Collection<ReservationResponse> getFilteredHostReservations(int hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus){
        return null;
    }

    @Override
    public Collection<ReservationResponse> getAllAccommodationReservations(UUID accommodationId) {
        // Retrieve all reservations for the given accommodation
        List<Reservation> accommodationReservations = reservationRepository.findAllByAccommodationId(accommodationId);

        // Convert the list of reservation entities to a list of response DTOs
        return accommodationReservations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ReservationResponse> getAllGuestReservations(int guestId) {
        return null;
    }

    @Override
    public boolean deleteReservation(UUID reservationId) { // Check if the reservation exists
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            // Delete the reservation
            reservationRepository.deleteById(reservationId);
            return true;
        }
        return false;
    } //treba namestiti brisanje rezervacije

    @Override
    public boolean cancelReservation(UUID reservationId) {
        // Check if the reservation exists
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            // Cancel the reservation (update status to CANCELED, for example)
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(ReservationStatus.Canceled);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    } //treba namestiti otkaz rezervacije

    @Override
    public String acceptReservation(int reservationId){
        return "uspesno izmenjena rezervacija";
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
