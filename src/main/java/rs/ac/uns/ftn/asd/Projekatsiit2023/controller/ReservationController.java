package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReservationServiceImplementation;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImplementation reservationService;
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
    @PutMapping("/{reservationId}/cancel")
    @PreAuthorize("hasAuthority('ROLE_Guest')")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable("reservationId") UUID reservationId) {
        Boolean isCancelled = reservationService.cancelReservation(reservationId);

        if (isCancelled != null) { //isCancelled ne znaci da li je cancelovan ili ne vec proverava da li akomodacija postoji ili ne(true false)
            return ResponseEntity.ok(isCancelled);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "{hostId}/results")
    public ResponseEntity<Collection<ReservationResponse>> getHostReservations(@PathVariable("hostId") UUID hostId){
        Collection<ReservationResponse> reservations = reservationService.getAllHostReservations(hostId);
        if(reservations == null){
            return new ResponseEntity<Collection<ReservationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);
    }
    @GetMapping(value = "{hostId}/filtered")
    public ResponseEntity<Collection<ReservationResponse>> getFilteredHostReservations(@PathVariable("hostId") int hostId,
                                                                                       @RequestParam(required = false) DatePeriod reservationPeriod,
                                                                                       @RequestParam(required = false) String reservationName,
                                                                                       @RequestParam(required = false) ReservationStatus reservationStatus){
        Collection<ReservationResponse> reservations = reservationService.getFilteredHostReservations(hostId,reservationPeriod,reservationName,reservationStatus);
        if(reservations == null){
            return new ResponseEntity<Collection<ReservationResponse>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservations);

    }
    @PutMapping(value= "/{reservationId}/accept")
    public ResponseEntity<Boolean> acceptReservation(@PathVariable("reservationId") UUID reservationId){
        boolean reservationResponse = reservationService.acceptReservation(reservationId);
        if(!reservationResponse){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservationResponse);
    }
}
