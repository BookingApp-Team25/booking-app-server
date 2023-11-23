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
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") int reservationId, @RequestBody ReservationRequest reservationRequest) {
        boolean isDeleted = reservationService.deleteReservation(reservationId);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/viewReservation/cancelReservation/{reservationId}")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable("reservationId") int reservationId) {
        boolean isCancelled = reservationService.cancelReservation(reservationId);

        if (isCancelled) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
    public ResponseEntity<Void> addFavoriteAccommodation(@PathVariable("accommodationId") int accommodationId, @RequestBody AccommodationRequest accommodationRequest) {
        boolean isAdded = guestService.addFavoriteAccommodation(accommodationId);//, accommodationRequest);

        if (isAdded) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
