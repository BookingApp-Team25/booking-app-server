package rs.ac.uns.ftn.asd.Projekatsiit2023.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.dto.*;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.GuestServiceImplementation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReservationServiceImplementation;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewService;
import rs.ac.uns.ftn.asd.Projekatsiit2023.service.ReviewServiceImplementation;

@RestController
@RequestMapping("/api/guest")
public class GuestController {
    @Autowired
    private GuestServiceImplementation guestService;
    @Autowired
    private ReservationServiceImplementation reservationService;
    @Autowired
    private ReviewServiceImplementation reviewService;
    @PostMapping(value = "/createReservation")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        ReservationResponse reservationResponse = reservationService.createReservation(reservationRequest);
        return new ResponseEntity<ReservationResponse>(reservationResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/viewReservation/deleteReservation/{reservationId}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable("reservationId") int reservationId) {//, @RequestBody ReservationRequest reservationRequest) {
        Boolean isDeleted = reservationService.deleteReservation(reservationId);

        if (isDeleted != null) {
            return ResponseEntity.ok(isDeleted);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/viewReservation/cancelReservation/{reservationId}")
    public ResponseEntity<Boolean> cancelReservation(@PathVariable("reservationId") int reservationId) {
        Boolean isCancelled = reservationService.cancelReservation(reservationId);

        if (isCancelled != null) { //isCancelled ne znaci da li je cancelovan ili ne vec proverava da li akomodacija postoji ili ne(true false)
            return ResponseEntity.ok(isCancelled);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/createReview") //trebalo bi da pokriva i host review i accommodation review
    public ResponseEntity<ReviewResponse> createReview(@RequestBody ReviewRequest reviewRequest) {
        ReviewResponse reviewResponse = reviewService.createReview(reviewRequest);
        return new ResponseEntity<ReviewResponse>(reviewResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/viewFavoriteAccommodations/addFavoriteAccommodation/{accommodationId}")
    public ResponseEntity<Boolean> addFavoriteAccommodation(@PathVariable("accommodationId") int accommodationId) {//, @RequestBody AccommodationRequest accommodationRequest) {
        Boolean isAdded = guestService.addFavoriteAccommodation(accommodationId);//, accommodationRequest);

        if (isAdded != null) {
            return ResponseEntity.ok(isAdded);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
