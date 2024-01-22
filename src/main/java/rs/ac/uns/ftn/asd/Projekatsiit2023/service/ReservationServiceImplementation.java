package rs.ac.uns.ftn.asd.Projekatsiit2023.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationOnHoldStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.AccommodationReservationPolicy;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.AccommodationDatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Accommodation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.Reservation;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImplementation implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final AccommodationRepository accommodationRepository;
    private final HostRepository hostRepository;
    private final GuestRepository guestRepository;

    private final DatePeriodRepository datePeriodRepository;

    @Autowired
    public ReservationServiceImplementation(ReservationRepository reservationRepository, AccommodationRepository accommodationRepository, DatePeriodRepository datePeriodRepository, HostRepository hostRepository, GuestRepository guestRepository) {
        this.reservationRepository = reservationRepository;
        this.accommodationRepository = accommodationRepository;
        this.datePeriodRepository = datePeriodRepository;
        this.hostRepository = hostRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public MessageResponse createReservation(ReservationRequest reservationRequest) {
        DatePeriod datePeriod = new DatePeriod(
                reservationRequest.getReservedDate().getStartDate(),
                reservationRequest.getReservedDate().getEndDate()
        );
        Accommodation accommodation = accommodationRepository.findById(reservationRequest.getAccommodationId())
                .orElseThrow(() -> new EntityNotFoundException("Accommodation not found with id: " + reservationRequest.getAccommodationId()));

        DateManagementService dateManagementService = new DateManagementService(reservationRepository, accommodationRepository);
        if(!isReservationPossible(datePeriod, accommodation.getAvailability())){//dateManagementService.
            return new MessageResponse(false,"Reservation at that period is not possible");
        }
        long reservationPrice = reservationRequest.getPrice();//dateManagementService.calculatePriceForPeriod(datePeriod,accommodation);
        ReservationStatus reservationStatus;
        if(accommodation.getPolicy() == AccommodationReservationPolicy.Auto){
            reservationStatus = ReservationStatus.ACCEPTED;
            List<AccommodationDatePeriod> newAvailability = dateManagementService.calculateAvailabilityAfterReservation(reservationRequest.getReservedDate(),accommodation.getAvailability());
            accommodation.availability.clear();
            accommodation.availability.addAll(newAvailability);
            accommodationRepository.save(accommodation);

        }
        else{
            reservationStatus = ReservationStatus.WAITING_FOR_APPROVAL;
        }
        datePeriodRepository.save(datePeriod);
        Reservation reservation = new Reservation(
                guestRepository.getReferenceById(reservationRequest.getGuestId()),
                hostRepository.getReferenceById(reservationRequest.getHostId()),
                accommodation,
                reservationStatus,
                datePeriod,
                reservationPrice
        );
        reservationRepository.save(reservation);
        //return convertToDto(reservation);
        return new MessageResponse(true,"succesfully added new reservation");
    }

    private boolean isReservationPossible(DatePeriod reservationDatePeriod, List<AccommodationDatePeriod> availableDates){

        for( AccommodationDatePeriod accommodationDatePeriod : availableDates){
            if(!(reservationDatePeriod.getStartDate().isBefore(accommodationDatePeriod.getStartDate()) || reservationDatePeriod.getEndDate().isAfter(accommodationDatePeriod.getEndDate()))){
                return true;
            }
        }
        return false;
    }

    @Override
    public HostReservationCollectionResponse getAllUnresolvedHostReservations(UUID hostId, int page, int numberOfElements) throws IOException {
        long totalNumberOfReservations = reservationRepository.count();
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Reservation> fullList = new ArrayList<Reservation>(reservationRepository.findAllUnresolvedHostReservations(hostId,pageRequest).getContent());
        ArrayList<HostReservationResponse> hostReservations = new ArrayList<>();
        for (Reservation reservation : fullList){
            String guestName = reservation.getGuest().getUsername();
            String accommodationName = reservation.getAccommodation().getName();
            UUID accommodationId = reservation.getAccommodation().getId();
            String accommodationPhoto = "Random photo";
            UUID reservationId = reservation.getId();
            AccommodationReservationPolicy accommodationReservationPolicy = reservation.getAccommodation().getPolicy();
            hostReservations.add(new HostReservationResponse(reservationId,guestName,accommodationId,accommodationName,reservation.getAccommodation().getPhotosEncoded().get(0),reservation.getReservationStatus(),reservation.getReservedDate(),reservation.getPrice()));

        }
        return new HostReservationCollectionResponse(hostReservations,totalNumberOfReservations);
    }

    @Override
    public ReservationResponse getReservation() {
        return null;
    }

    @Override
    public HostReservationCollectionResponse getAllHostReservations(UUID hostId, int page, int numberOfElements) throws IOException {
        long totalNumberOfReservations = reservationRepository.count();
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        ArrayList<Reservation> fullList = new ArrayList<Reservation>(reservationRepository.findAllHostReservations(hostId,pageRequest).getContent());
        ArrayList<HostReservationResponse> hostReservations = new ArrayList<>();
        for (Reservation reservation : fullList){
                String guestName = reservation.getGuest().getUsername();
                String accommodationName = reservation.getAccommodation().getName();
                UUID accommodationId = reservation.getAccommodation().getId();
                String accommodationPhoto = "Random photo";
                UUID reservationId = reservation.getId();
            AccommodationReservationPolicy accommodationReservationPolicy = reservation.getAccommodation().getPolicy();
            hostReservations.add(new HostReservationResponse(reservationId,guestName,accommodationId,accommodationName,reservation.getAccommodation().getPhotosEncoded().get(0),reservation.getReservationStatus(),reservation.getReservedDate(),reservation.getPrice()));

        }
        return new HostReservationCollectionResponse(hostReservations,totalNumberOfReservations);

    }

    public HostReservationCollectionResponse getFilteredHostReservations(UUID hostId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus, int page, int numberOfElements) throws IOException {
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        List<Reservation> reservationsFilteredOnce = reservationRepository.findByIdAndAccommodationName(reservationName,hostId,pageRequest).getContent();
        DateManagementService dateManagementService = new DateManagementService(reservationRepository,accommodationRepository);
        List<Reservation> reservationsFilteredTwice = new ArrayList<>();
        for(Reservation reservation : reservationsFilteredOnce){
            if(dateManagementService.isPeriodInside(reservation.getReservedDate(),reservationPeriod) && reservation.getReservationStatus() == reservationStatus){
                reservationsFilteredTwice.add(reservation);
            }
        }
        long totalNumberOfReservations = reservationsFilteredTwice.size();
        List<HostReservationResponse> hostReservationResponses = new ArrayList<>();
        for(Reservation reservation: reservationsFilteredTwice){
            HostReservationResponse hostReservationResponse = new HostReservationResponse();
            hostReservationResponse.setReservationId(reservation.getId());
            hostReservationResponse.setAccommodationPhoto(reservation.getAccommodation().getPhotosEncoded().get(0));
            hostReservationResponse.setReservedDate(reservation.getReservedDate());
            hostReservationResponse.setPrice(reservation.getPrice());
            hostReservationResponse.setAccommodationId(reservation.getAccommodation().getId());
            hostReservationResponse.setGuestName(reservation.getGuest().getUsername());
            hostReservationResponses.add(hostReservationResponse);
            hostReservationResponse.setAccommodationName(reservation.getAccommodation().getName());
        }
        return new HostReservationCollectionResponse(hostReservationResponses,totalNumberOfReservations);
    }

    public ReservationSummaryCollectionResponse getFilteredGuestReservations(UUID guestId, DatePeriod reservationPeriod, String reservationName, ReservationStatus reservationStatus, int page, int numberOfElements) throws IOException {
        Pageable pageRequest = PageRequest.of(page, numberOfElements);
        List<Reservation> reservationsFilteredOnce = reservationRepository.findByIdAndAccommodationNameGuest(reservationName,guestId,pageRequest).getContent();
        DateManagementService dateManagementService = new DateManagementService(reservationRepository,accommodationRepository);
        List<Reservation> reservationsFilteredTwice = new ArrayList<>();
        for(Reservation reservation : reservationsFilteredOnce){
            if(dateManagementService.isPeriodInside(reservation.getReservedDate(),reservationPeriod) && reservation.getReservationStatus() == reservationStatus){
                reservationsFilteredTwice.add(reservation);
            }
        }
        long totalNumberOfReservations = reservationsFilteredTwice.size();
        List<ReservationRequest> guestReservationResponses = new ArrayList<>();
        for(Reservation reservation: reservationsFilteredTwice){
            ReservationRequest guestReservationResponse = new ReservationRequest(reservation.getId() , reservation.getGuest().getId(), reservation.getHost().getId(),
                    reservation.getAccommodation().getId(), reservation.getReservationStatus(), reservation.getReservedDate(), reservation.getPrice());
            guestReservationResponses.add(guestReservationResponse);
        }
        return new ReservationSummaryCollectionResponse(guestReservationResponses,totalNumberOfReservations);
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
            reservation.setReservationStatus(ReservationStatus.CANCELED);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }
    @Override
    public MessageResponse resolveReservation(UUID reservationId, boolean isAccepted){
        boolean isValid;
        if(isAccepted){
            isValid = acceptReservation(reservationId);
        }
        else{
            isValid = rejectReservation(reservationId);
        }
        if(isValid){
            return  new MessageResponse(true,"Succesfuly resolved reservation request");
        }
        else{
            return new MessageResponse(false, "ERROR: reservation request could not be resolved");
        }
    }

    @Override
    public boolean acceptReservation(UUID reservationId){
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(ReservationStatus.ACCEPTED);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }
    @Override
    public boolean rejectReservation(UUID reservationId){
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            reservation.setReservationStatus(ReservationStatus.REJECTED);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    // Helper method to convert Reservation entity to Response DTO
    private ReservationResponse convertToDto(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getGuest().getId(),
                reservation.getHost().getId(),
                reservation.getAccommodation().getId(),
                reservation.getReservationStatus(),
                reservation.getReservedDate(),
                999
        );
    }
}
