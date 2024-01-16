package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.repository.AccommodationRepository;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.time.Instant;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImplementation reservationService;
    @Autowired
    private DateManagementService dateManagementService;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private UserServiceImplementation userService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('ROLE_Guest')")
    public ResponseEntity<MessageResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        MessageResponse reservationResponse = reservationService.createReservation(reservationRequest);
        return ResponseEntity.ok(reservationResponse);
    }
    @PutMapping(value = "/{reservationId}/delete")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable("reservationId") UUID reservationId) {//, @RequestBody ReservationRequest reservationRequest) {
        Boolean isDeleted = reservationService.deleteReservation(reservationId);

        if (isDeleted != null) {
            return ResponseEntity.ok(isDeleted);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/cancel/{reservationId}")
    @PreAuthorize("hasAuthority('ROLE_Guest')")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable("reservationId") String reservationId) {
        Boolean isCancelled = reservationService.cancelReservation(UUID.fromString(reservationId));

        if (isCancelled != null) { //isCancelled ne znaci da li je cancelovan ili ne vec proverava da li akomodacija postoji ili ne(true false)
            return ResponseEntity.ok(isCancelled);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "{hostId}/unresolved")
    @PreAuthorize("hasAuthority('ROLE_Host')")
    public ResponseEntity<HostReservationCollectionResponse> getUnresolvedHostReservations(@PathVariable("hostId") UUID hostId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        HostReservationCollectionResponse reservations = reservationService.getAllUnresolvedHostReservations(hostId,page,numberOfElements);
        if(reservations == null){
            return new ResponseEntity<HostReservationCollectionResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);
    }
    @GetMapping(value = "{hostId}/results")
    @PreAuthorize("hasAuthority('ROLE_Host')")
    public ResponseEntity<HostReservationCollectionResponse> getHostReservations(@PathVariable("hostId") UUID hostId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        HostReservationCollectionResponse reservations = reservationService.getAllHostReservations(hostId,page,numberOfElements);
        if(reservations == null){
            return new ResponseEntity<HostReservationCollectionResponse>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);
    }
    @GetMapping(value = "{hostId}/filtered")
    public ResponseEntity<HostReservationCollectionResponse> getFilteredHostReservations(@PathVariable("hostId") UUID hostId,
                                                                                         @RequestParam(required = false) String startDateStr,
                                                                                         @RequestParam(required = false) String endDateStr,
                                                                                         @RequestParam(required = false) String reservationName,
                                                                                         @RequestParam ReservationStatus reservationStatus, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        if(reservationName == null){
            reservationName = "";
        }
        LocalDate startDate;
        LocalDate endDate;
        if(startDateStr == null || endDateStr == null){
            startDate = LocalDate.MIN;
            endDate = LocalDate.MAX;
        }else{
            ZonedDateTime startDateTime = ZonedDateTime.parse(startDateStr, DateTimeFormatter.ISO_DATE_TIME);
            ZonedDateTime endDateTime = ZonedDateTime.parse(endDateStr, DateTimeFormatter.ISO_DATE_TIME);

            startDate = startDateTime.toLocalDate().plusDays(1);
            endDate = endDateTime.toLocalDate().plusDays(1);
        }
        DatePeriod reservationPeriod= new DatePeriod(startDate,endDate);
        HostReservationCollectionResponse reservations = reservationService.getFilteredHostReservations(hostId,reservationPeriod,reservationName,reservationStatus,page,numberOfElements);
        return ResponseEntity.ok(reservations);

    }

    @GetMapping(value = "{guestId}/filtered-guest")
    public ResponseEntity<ReservationSummaryCollectionResponse> getFilteredGuestReservations(@PathVariable("guestId") UUID guestId,
                                                                                         @RequestParam(required = false) String startDateStr,
                                                                                         @RequestParam(required = false) String endDateStr,
                                                                                         @RequestParam(required = false) String reservationName,
                                                                                         @RequestParam ReservationStatus reservationStatus, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10")int numberOfElements) throws IOException {
        if(reservationName == null){
            reservationName = "";
        }
        LocalDate startDate;
        LocalDate endDate;
        if(startDateStr == null || endDateStr == null){
            startDate = LocalDate.MIN;
            endDate = LocalDate.MAX;
        }else{
            ZonedDateTime startDateTime = ZonedDateTime.parse(startDateStr, DateTimeFormatter.ISO_DATE_TIME);
            ZonedDateTime endDateTime = ZonedDateTime.parse(endDateStr, DateTimeFormatter.ISO_DATE_TIME);

            startDate = startDateTime.toLocalDate().plusDays(1);
            endDate = endDateTime.toLocalDate().plusDays(1);
        }
        DatePeriod reservationPeriod= new DatePeriod(startDate,endDate);
        ReservationSummaryCollectionResponse reservations = reservationService.getFilteredGuestReservations(guestId,reservationPeriod,reservationName,reservationStatus,page,numberOfElements);
        return ResponseEntity.ok(reservations);

    }

    @PostMapping(value = "/{reservationId}/resolve")
    public ResponseEntity<MessageResponse> resolveReservationRequest(@PathVariable("reservationId") UUID reservationId, @RequestParam boolean isAccepted){
        MessageResponse message = reservationService.resolveReservation(reservationId,isAccepted);
        return ResponseEntity.ok(message);
    }
    @PutMapping(value= "/{reservationId}/accept")
    public ResponseEntity<Boolean> acceptReservation(@PathVariable("reservationId") UUID reservationId){
        boolean reservationResponse = reservationService.acceptReservation(reservationId);
        if(!reservationResponse){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservationResponse);
    }

    @GetMapping(value = "/price")
    public ResponseEntity<Long> calculatePrice(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("accommodationId") String accommodationId) {

        try {
            Date startDateObj = Date.from(Instant.parse(startDate));
            Date endDateObj = Date.from(Instant.parse(endDate));

            LocalDate localDateStart = startDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateEnd = endDateObj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            UUID accommodationUUID = UUID.fromString(accommodationId);

            long price = dateManagementService.calculatePriceForPeriod(
                    new DatePeriod(localDateStart, localDateEnd),
                    accommodationRepository.findAccommodationById(accommodationUUID)
            );

            return ResponseEntity.ok(price);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(-1L);
        }
    }

    @GetMapping(value = "/guest-id/{guestUsername}") //morao sam u ovaj controller staviti jer nije drugde zbog corsa hteo nzm zasto cak i kad se doda cors da treba da radi on ne radi
    public ResponseEntity<GuestData> findGuestByUsername(@PathVariable("guestUsername") String guestUsername){
        GuestData guestData = userService.getGuestByUsername(guestUsername);
        return ResponseEntity.ok(guestData);
    }
}
