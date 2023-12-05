package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReservationResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewRequest;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.ReviewResponse;
import rs.ac.uns.ftn.asd.Projekatsiit2023.enums.ReservationStatus;
import rs.ac.uns.ftn.asd.Projekatsiit2023.model.DatePeriod;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReservationServiceImplementation;

import java.util.Collection;

@RestController
@RequestMapping("api/reservation")
public class ReservationController {
    @Autowired
    private ReservationServiceImplementation reservationService;
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<ReservationResponse>(reservationResponse, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{reservationId}/delete")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable("reservationId") int reservationId) {//, @RequestBody ReservationRequest reservationRequest) {
        Boolean isDeleted = reservationService.deleteReservation(reservationId);

        if (isDeleted != null) {
            return ResponseEntity.ok(isDeleted);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable("reservationId") int reservationId) {
        Boolean isCancelled = reservationService.cancelReservation(reservationId);

        if (isCancelled != null) { //isCancelled ne znaci da li je cancelovan ili ne vec proverava da li akomodacija postoji ili ne(true false)
            return ResponseEntity.ok(isCancelled);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "{hostId}/results")
    public ResponseEntity<Collection<ReservationResponse>> getHostReservations(@PathVariable("hostId") int hostId){
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
    public ResponseEntity<String> acceptReservation(@PathVariable("reservationId") int reservationId){
        String reservationResponse = reservationService.acceptReservation(reservationId);
        if(reservationResponse == null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(reservationResponse);
    }
}
